/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/23/23, 8:07 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.add.weight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityAddWeightBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.ControlWeight
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil
import com.cbaelectronics.bitacoradefamilia.util.extension.addClose
import com.cbaelectronics.bitacoradefamilia.util.extension.enable
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import com.cbaelectronics.bitacoradefamilia.util.extension.hideSoftInput

class AddWeightActivity : AppCompatActivity() {

    // Properties

    private lateinit var binding: ActivityAddWeightBinding
    private lateinit var viewModel: AddWeightViewModel
    private var weekEditText: String? = null
    private var weightEditText: String? = null

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWeightBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[AddWeightViewModel::class.java]

        // Setup
        localize()
        setup()
        footer()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }

    // Private

    private fun localize(){
        binding.textViewAddWeightTitle.text = getString(viewModel.title)
        binding.textFieldAddWeightWeek.hint = getString(viewModel.editTextWeek)
        binding.textFieldAddWeight.hint = getString(viewModel.editTextWeight)
        binding.buttonSaveWeight.text = getString(viewModel.save)
        binding.buttonCancelWeight.text = getText(viewModel.back)
    }

    private fun setup(){
        addClose(this)

        // UI
        binding.textViewAddWeightTitle.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )

        // Week

        val arrayWeek = resources.getStringArray(R.array.week)
        val adapterWeek = ArrayAdapter(
            this,
            R.layout.dropdown_menu_popup_item,
            arrayWeek
        )
        binding.editTextAddWeightWeek.setAdapter(adapterWeek)

        binding.editTextAddWeightWeek.inputType = InputType.TYPE_NULL

        setupInfo()
    }

    private fun footer(){
        disableSave()
        binding.buttonCancelWeight.setOnClickListener {
            onBackPressed()
        }

        binding.buttonSaveWeight.setOnClickListener {
            validForm()
        }
    }

    private fun setupInfo() {

        // Week

        binding.editTextAddWeightWeek.setOnItemClickListener { adapterView, view, i, l ->
            hideSoftInput()
            weekEditText = binding.editTextAddWeightWeek.text.toString()
            checkEnable()
        }

        // Weight

        binding.editTextAddWeight.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                weightEditText = binding.editTextAddWeight.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })
    }

    private fun checkEnable(){
        if (!weekEditText.isNullOrEmpty() && !weightEditText.isNullOrEmpty()){
            enableSave()
        }
    }

    private fun validForm(){
        val week = binding.editTextAddWeightWeek.text
        val weight = binding.editTextAddWeight.text

        if (week.isNullOrBlank() || weight.isNullOrBlank()) {
            UIUtil.showAlert(this, getString(viewModel.errorIncomplete))
        } else {
            val controlWeight = ControlWeight(
                viewModel.children?.id,
                week.toString().toInt(),
                weight.toString(),
                viewModel.user
            )

            saveDatabase(controlWeight)
        }
    }

    private fun saveDatabase(controlWeight: ControlWeight) {
        viewModel.save(controlWeight)

        clearEditText()
        hideSoftInput()
        UIUtil.showAlert(this, getString(viewModel.ok))
        disableSave()
    }

    private fun clearEditText() {
        binding.editTextAddWeight.text = null
        binding.editTextAddWeightWeek.text = null
    }

    private fun disableSave() {
        binding.buttonSaveWeight.enable(false)
        binding.buttonCancelWeight.text = getString(viewModel.back)
    }

    private fun enableSave() {
        binding.buttonSaveWeight.enable(true)
        binding.buttonCancelWeight.text = getString(viewModel.cancel)
    }
}
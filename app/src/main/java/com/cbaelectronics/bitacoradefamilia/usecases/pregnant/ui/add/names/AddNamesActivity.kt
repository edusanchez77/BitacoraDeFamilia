/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/17/23, 12:55 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.add.names

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityAddNamesBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.PosibleNames
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil.showAlert
import com.cbaelectronics.bitacoradefamilia.util.extension.addClose
import com.cbaelectronics.bitacoradefamilia.util.extension.enable
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import com.cbaelectronics.bitacoradefamilia.util.extension.hideSoftInput

class AddNamesActivity : AppCompatActivity() {

    // Properties

    private lateinit var binding: ActivityAddNamesBinding
    private lateinit var viewModel: AddNamesViewModel
    private var nameEditText: String? = null
    private var genreEditText: String? = null

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNamesBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[AddNamesViewModel::class.java]

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
        binding.textViewAddNamesTitle.text = getString(viewModel.title)
        binding.editTextAddNameName.hint = getString(viewModel.editTextName)
        binding.editTextAddNameGenre.hint = getString(viewModel.editTextGenre)
        binding.buttonSaveNames.text = getString(viewModel.save)
        binding.buttonCancelNames.text = getString(viewModel.cancel)
    }

    private fun setup(){
        addClose(this)

        // UI
        binding.textViewAddNamesTitle.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )

        // Genre

        val arrayGenre = resources.getStringArray(R.array.genre)
        val adapterGenre = ArrayAdapter(
            this,
            R.layout.dropdown_menu_popup_item,
            arrayGenre
        )
        binding.editTextAddNameGenre.setAdapter(adapterGenre)
        binding.editTextAddNameGenre.inputType = InputType.TYPE_NULL

        setupInfo()
    }

    private fun footer(){
        disableSave()
        binding.buttonCancelNames.setOnClickListener {
            onBackPressed()
        }

        binding.buttonSaveNames.setOnClickListener {
            validForm()
        }
    }

    private fun setupInfo(){
        // Name

        binding.editTextAddNameName.addTextChangedListener ( object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nameEditText = binding.editTextAddNameName.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        } )

        // Genre

        binding.editTextAddNameGenre.setOnItemClickListener { adapterView, view, i, l ->
            hideSoftInput()
            genreEditText = binding.editTextAddNameGenre.text.toString()
            checkEnable()
        }
    }

    private fun checkEnable(){
        if (!nameEditText.isNullOrEmpty() && !genreEditText.isNullOrEmpty()){
            enableSave()
        }
    }

    private fun validForm() {
        val name = binding.editTextAddNameName.text
        val genre = binding.editTextAddNameGenre.text

        if (name.isNullOrBlank() || genre.isNullOrBlank()) {
            showAlert(this, getString(viewModel.errorIncomplete))
        } else {
            val name = PosibleNames(
                viewModel.children?.id,
                name.toString(),
                genre.toString(),
                viewModel.user
            )

            saveDatabase(name)
        }
    }

    private fun saveDatabase(name: PosibleNames) {
        viewModel.save(name)

        clearEditText()
        hideSoftInput()
        showAlert(this, getString(viewModel.ok))
        disableSave()
    }

    private fun clearEditText() {
        binding.editTextAddNameName.text = null
        binding.editTextAddNameGenre.text = null
    }

    private fun disableSave() {
        binding.buttonSaveNames.enable(false)
        binding.buttonCancelNames.text = getString(viewModel.back)
    }

    private fun enableSave() {
        binding.buttonSaveNames.enable(true)
        binding.buttonCancelNames.text = getString(viewModel.cancel)
    }
}
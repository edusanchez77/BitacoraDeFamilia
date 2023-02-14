/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/3/23, 4:29 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.growth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityAddGrowthBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Growth
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil
import com.cbaelectronics.bitacoradefamilia.util.extension.*
import java.text.SimpleDateFormat
import kotlin.text.toDouble

class AddGrowthActivity : AppCompatActivity() {

    // Properties

    private lateinit var binding: ActivityAddGrowthBinding
    private lateinit var viewModel: AddGrowthViewModel

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGrowthBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[AddGrowthViewModel::class.java]

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

    private fun localize() {
        binding.textViewAddGrowthTitle.text = getString(viewModel.title)
        binding.textFieldAddGrowthDate.hint = getString(viewModel.date)
        binding.textFieldAddGrowthWeight.hint = getString(viewModel.weight)
        binding.textFieldAddGrowthHeigth.hint = getString(viewModel.height)
        binding.textFieldAddGrowhtPC.hint = getString(viewModel.pc)
        binding.buttonSaveGrowth.text = getString(viewModel.save)
        binding.buttonCancelGrowth.text = getString(viewModel.cancel)
    }

    private fun setup() {
        addClose(this)

        // UI
        binding.textViewAddGrowthTitle.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )
    }

    private fun footer() {
        binding.buttonSaveGrowth.setOnClickListener {
            validForm()
        }

        binding.buttonCancelGrowth.setOnClickListener {
            onBackPressed()
        }
    }

    private fun validForm() {
        val date = binding.editTextAddGrowthDate.text.toString()
        val weight = binding.editTextAddGrowthWeight.text
        val height = binding.editTextAddGrowthHeight.text.toString()
        val pc = if(!binding.editTextAddGrowthPC.text.isNullOrBlank()) binding.editTextAddGrowthPC.text.toString().toInt() else null

        if (date.isNullOrBlank() || weight.isNullOrBlank() || height.isNullOrBlank()) {
            UIUtil.showAlert(this, getString(viewModel.errorIncomplete))
        } else {

            val sdf = SimpleDateFormat(Constants.DATE)
            val dateFormat = sdf.parse(date.toString())

            val growth = Growth(
                viewModel.children?.id,
                dateFormat,
                weight.toString(),
                height.toInt(),
                pc,
                viewModel.user
            )

            saveDatabase(growth)
        }
    }

    private fun saveDatabase(growth: Growth) {
        viewModel.save(growth)

        clearEditText()
        hideSoftInput()
        UIUtil.showAlert(this, getString(viewModel.ok))
        onBackPressed()
    }

    private fun clearEditText() {
        binding.editTextAddGrowthDate.text = null
        binding.editTextAddGrowthWeight.text = null
        binding.editTextAddGrowthHeight.text = null
        binding.editTextAddGrowthPC.text = null
    }
}
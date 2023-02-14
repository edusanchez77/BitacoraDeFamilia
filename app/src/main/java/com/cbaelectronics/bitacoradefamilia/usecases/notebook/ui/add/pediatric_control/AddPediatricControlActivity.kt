/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/6/23, 8:48 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.pediatric_control

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityAddPediatricControlBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.PediatricControl
import com.cbaelectronics.bitacoradefamilia.util.Constants.DATE
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil
import com.cbaelectronics.bitacoradefamilia.util.UIUtil.showAlert
import com.cbaelectronics.bitacoradefamilia.util.extension.addClose
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import com.cbaelectronics.bitacoradefamilia.util.extension.hideSoftInput
import com.cbaelectronics.bitacoradefamilia.util.extension.toDate
import java.text.SimpleDateFormat

class AddPediatricControlActivity : AppCompatActivity() {

    // Properties

    private lateinit var binding: ActivityAddPediatricControlBinding
    private lateinit var viewModel: AddPediatricControlViewModel

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPediatricControlBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[AddPediatricControlViewModel::class.java]

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
        binding.textViewAddPediatricControlTitle.text = getString(viewModel.title)
        binding.textFieldAddPediatricControlDate.hint = getString(viewModel.editTextDate)
        binding.textFieldAddPediatricControlDoctor.hint = getString(viewModel.editTextDoctor)
        binding.textFieldAddPediatricControlDoctorSpeciality.hint =
            getString(viewModel.editTextSpeciality)
        binding.textFieldAddPediatricControlWeight.hint = getString(viewModel.editTextWeight)
        binding.textFieldAddPediatricControlHeight.hint = getString(viewModel.editTextHeight)
        binding.textFieldAddPediatricControlObservation.hint =
            getString(viewModel.editTextObservation)
        binding.textFieldAddPediatricControlNextControl.hint = getString(viewModel.editTextNext)
        binding.textFieldAddPediatricControlNotes.hint = getString(viewModel.editTextNotes)
        binding.buttonSavePediatricControl.text = getString(viewModel.save)
        binding.buttonCancelPediatricControl.text = getString(viewModel.cancel)
    }

    private fun setup() {
        addClose(this)

        // UI
        binding.textViewAddPediatricControlTitle.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )
    }

    private fun footer() {
        binding.buttonSavePediatricControl.setOnClickListener {
            validForm()
        }

        binding.buttonCancelPediatricControl.setOnClickListener {
            onBackPressed()
        }
    }

    private fun validForm() {
        val date = binding.editTextAddPediatricControlDate.text
        val doctor = binding.editTextAddPediatricControlDoctor.text
        val specialty = binding.editTextAddPediatricControlSpeciality.text
        val weight = binding.editTextAddPediatricControlWeight.text
        val height = binding.editTextAddPediatricControlHeight.text
        val observation = binding.editTextAddPediatricControlObservation.text
        val next = binding.editTextAddPediatricControlNextControl.text
        val notes = binding.editTextAddPediatricControlNotes.text

        if (date.isNullOrEmpty() || doctor.isNullOrEmpty() || specialty.isNullOrEmpty() || weight.isNullOrEmpty() || height.isNullOrEmpty() || observation.isNullOrEmpty() || next.isNullOrEmpty()) {
            showAlert(this, getString(viewModel.errorIncomplete))
        } else {

            val sdf = SimpleDateFormat(DATE)
            val dateFormat = sdf.parse(date.toString())
            val nextFormat = sdf.parse(next.toString())

            val control = PediatricControl(
                viewModel.children?.id,
                dateFormat,
                doctor.toString(),
                specialty.toString(),
                weight.toString(),
                height.toString().toInt(),
                observation.toString(),
                nextFormat,
                notes.toString(),
                viewModel.user
            )

            saveDatabase(control)
        }
    }

    private fun saveDatabase(control: PediatricControl) {
        viewModel.save(control)

        clearEditText()
        hideSoftInput()
        UIUtil.showAlert(this, getString(viewModel.ok))
        onBackPressed()
    }

    private fun clearEditText() {
        binding.editTextAddPediatricControlDate.text = null
        binding.editTextAddPediatricControlDoctor.text = null
        binding.editTextAddPediatricControlSpeciality.text = null
        binding.editTextAddPediatricControlWeight.text = null
        binding.editTextAddPediatricControlHeight.text = null
        binding.editTextAddPediatricControlObservation.text = null
        binding.editTextAddPediatricControlNextControl.text = null
        binding.editTextAddPediatricControlNotes.text = null
    }
}
/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/6/23, 8:48 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.pediatric_control

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityAddPediatricControlBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.PediatricControl
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.cbaelectronics.bitacoradefamilia.util.Constants.DATE
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil
import com.cbaelectronics.bitacoradefamilia.util.UIUtil.showAlert
import com.cbaelectronics.bitacoradefamilia.util.extension.addClose
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import com.cbaelectronics.bitacoradefamilia.util.extension.hideSoftInput
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class AddPediatricControlActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    // Properties

    private lateinit var binding: ActivityAddPediatricControlBinding
    private lateinit var viewModel: AddPediatricControlViewModel

    private var day = 0
    private var month = 0
    private var year = 0
    private var vDay = 0
    private var vMonth = 0
    private var vYear = 0
    private var currentEditText: EditText? = null

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

        // Date and Time

        binding.editTextAddPediatricControlDate.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                hideSoftInput()
                getDateTimeCalendar()
                activeEditText(binding.editTextAddPediatricControlDate)
                DatePickerDialog(
                    this,
                    R.style.themeOnverlay_timePicker,
                    this,
                    year,
                    month,
                    day
                ).show()
            }
        }

        binding.editTextAddPediatricControlNextControl.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                hideSoftInput()
                getDateTimeCalendar()
                activeEditText(binding.editTextAddPediatricControlNextControl)
                DatePickerDialog(
                    this,
                    R.style.themeOnverlay_timePicker,
                    this,
                    year,
                    month,
                    day
                ).show()
            }
        }
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
        finish()
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

    private fun getDateTimeCalendar() {

        TimeZone.getDefault()
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)

    }

    private fun activeEditText(editText: TextInputEditText) {
        showAlert(this, "Next Control")
        currentEditText = editText
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        vYear = p1
        vMonth = p2 + 1
        vDay = p3

        val day = "$vDay/$vMonth/$vYear"
        val sdf = SimpleDateFormat(Constants.DATE)
        val date = sdf.parse(day)
        val newDate = sdf.format(date)

        currentEditText?.setText(newDate)
        //binding.editTextAddPediatricControlDate.setText(newDate)

    }
}
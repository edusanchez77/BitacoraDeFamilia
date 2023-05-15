/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/6/23, 8:48 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.pediatric_control

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.cbaelectronics.bitacoradefamilia.util.extension.enable
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import com.cbaelectronics.bitacoradefamilia.util.extension.hideSoftInput
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class AddPediatricControlActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    // Properties

    private lateinit var binding: ActivityAddPediatricControlBinding
    private lateinit var viewModel: AddPediatricControlViewModel
    private var dateEditText: String? = null
    private var doctorEditText: String? = null
    private var specialityEditText: String? = null
    private var weightEditText: String? = null
    private var heightEditText: String? = null
    private var observationsEditText: String? = null
    private var nextControlEditText: String? = null

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

        footerInfo()
    }

    private fun footer() {
        disableSave()
        binding.buttonSavePediatricControl.setOnClickListener {
            validForm()
        }

        binding.buttonCancelPediatricControl.setOnClickListener {
            onBackPressed()
        }
    }

    private fun footerInfo(){

        // Date

        binding.editTextAddPediatricControlDate.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                dateEditText = binding.editTextAddPediatricControlDate.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })

        // Doctor

        binding.editTextAddPediatricControlDoctor.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                doctorEditText = binding.editTextAddPediatricControlDoctor.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })

        // Speciality

        binding.editTextAddPediatricControlSpeciality.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                specialityEditText = binding.editTextAddPediatricControlSpeciality.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })

        // Weight

        binding.editTextAddPediatricControlWeight.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                weightEditText = binding.editTextAddPediatricControlWeight.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })

        // Height

        binding.editTextAddPediatricControlHeight.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                heightEditText = binding.editTextAddPediatricControlHeight.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })

        // Observations

        binding.editTextAddPediatricControlObservation.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                observationsEditText = binding.editTextAddPediatricControlObservation.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })

        // Next Control

        binding.editTextAddPediatricControlNextControl.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nextControlEditText = binding.editTextAddPediatricControlNextControl.text.toString()
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
        if (!dateEditText.isNullOrEmpty() && !doctorEditText.isNullOrEmpty() && !specialityEditText.isNullOrEmpty() && !weightEditText.isNullOrEmpty() && !heightEditText.isNullOrEmpty() && !observationsEditText.isNullOrEmpty() && !nextControlEditText.isNullOrEmpty()){
            enableSave()
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
        disableSave()
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

    private fun disableSave() {
        binding.buttonSavePediatricControl.enable(false)
        binding.buttonCancelPediatricControl.text = getString(viewModel.back)
    }

    private fun enableSave() {
        binding.buttonSavePediatricControl.enable(true)
        binding.buttonCancelPediatricControl.text = getString(viewModel.cancel)
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
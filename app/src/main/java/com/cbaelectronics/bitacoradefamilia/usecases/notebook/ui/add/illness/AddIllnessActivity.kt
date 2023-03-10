/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/3/23, 2:59 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.illness

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityAddIllnessBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Illness
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil
import com.cbaelectronics.bitacoradefamilia.util.extension.*
import java.text.SimpleDateFormat
import java.util.*

class AddIllnessActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    // Properties

    private lateinit var binding: ActivityAddIllnessBinding
    private lateinit var viewModel: AddIllnessViewModel
    private var dateEditText: String? = null
    private var illnessEditText: String? = null
    private var durationEditText: String? = null
    private var symptomEditText: String? = null
    private var medicationEditText: String? = null

    private var day = 0
    private var month = 0
    private var year = 0
    private var vDay = 0
    private var vMonth = 0
    private var vYear = 0

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddIllnessBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[AddIllnessViewModel::class.java]

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
        binding.textViewAddIllnesTitle.text = getString(viewModel.title)
        binding.textFieldAddIllnessDate.hint = getString(viewModel.editTextDate)
        binding.textFieldAddIllnessName.hint = getString(viewModel.editTextIllnessName)
        binding.textFieldAddIllnessDuration.hint = getString(viewModel.editTextDuration)
        binding.textFieldAddIllnessSymptom.hint = getString(viewModel.editTextSymptom)
        binding.textFieldAddIllnessMedication.hint = getString(viewModel.editTextMedication)
        binding.textFieldAddIllnessObservation.hint = getString(viewModel.editTextObservation)
        binding.buttonSaveIllness.text = getString(viewModel.save)
        binding.buttonCancelIllness.text = getString(viewModel.cancel)
    }

    private fun setup() {
        addClose(this)

        // UI
        binding.textViewAddIllnesTitle.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )

        // Date and Time

        binding.editTextAddIllnessDate.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                hideSoftInput()
                getDateTimeCalendar()
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
        binding.buttonSaveIllness.setOnClickListener {
            validForm()
        }

        binding.buttonCancelIllness.setOnClickListener {
            onBackPressed()
        }
    }

    private fun footerInfo() {

        // Date

        binding.editTextAddIllnessDate.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                dateEditText = binding.editTextAddIllnessDate.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })

        // Illness

        binding.editTextAddIllnessName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                illnessEditText = binding.editTextAddIllnessName.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })

        // Duration

        binding.editTextAddIllnessDuration.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                durationEditText = binding.editTextAddIllnessDuration.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })

        // Symptom

        binding.editTextAddIllnessSymptom.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                symptomEditText = binding.editTextAddIllnessSymptom.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })

        // Medication

        binding.editTextAddIllnessMedication.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                medicationEditText = binding.editTextAddIllnessMedication.text.toString()
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

    private fun checkEnable() {
        if (!dateEditText.isNullOrEmpty() && !illnessEditText.isNullOrEmpty() && !durationEditText.isNullOrEmpty() && !symptomEditText.isNullOrEmpty() && !medicationEditText.isNullOrEmpty()) {
            enableSave()
        }
    }

    private fun validForm() {
        val date = binding.editTextAddIllnessDate.text
        val illnessName = binding.editTextAddIllnessName.text
        val symptom = binding.editTextAddIllnessSymptom.text
        val duration = binding.editTextAddIllnessDuration.text
        val medication = binding.editTextAddIllnessMedication.text
        val observations = binding.editTextAddIllnessObservation.text

        if (date.isNullOrEmpty() || illnessName.isNullOrEmpty() || symptom.isNullOrEmpty() || duration.isNullOrEmpty() || medication.isNullOrEmpty()) {
            UIUtil.showAlert(binding.root.context, getString(viewModel.errorIncomplete))
        } else {

            val sdf = SimpleDateFormat(Constants.DATE)
            val dateFormat = sdf.parse(date.toString())

            val illness = Illness(
                viewModel.children?.id,
                dateFormat,
                illnessName.toString(),
                symptom.toString(),
                duration.toString().toInt(),
                medication.toString(),
                observations.toString(),
                viewModel.user
            )

            saveDatabase(illness)
        }
    }

    private fun disableSave() {
        binding.buttonSaveIllness.enable(false)
        binding.buttonCancelIllness.text = getString(viewModel.back)
    }

    private fun enableSave() {
        binding.buttonSaveIllness.enable(true)
        binding.buttonCancelIllness.text = getString(viewModel.cancel)
    }

    private fun saveDatabase(illness: Illness) {
        viewModel.save(illness)

        clearEditText()
        hideSoftInput()
        UIUtil.showAlert(this, getString(viewModel.ok))
        disableSave()
        finish()
    }

    private fun clearEditText() {
        binding.editTextAddIllnessDate.text = null
        binding.editTextAddIllnessName.text = null
        binding.editTextAddIllnessSymptom.text = null
        binding.editTextAddIllnessDuration.text = null
        binding.editTextAddIllnessMedication.text = null
        binding.editTextAddIllnessObservation.text = null
    }

    private fun getDateTimeCalendar() {

        TimeZone.getDefault()
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)

    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        vYear = p1
        vMonth = p2 + 1
        vDay = p3

        getDateTimeCalendar()

        val day = "$vDay/$vMonth/$vYear"
        val sdf = SimpleDateFormat(Constants.DATE)
        val date = sdf.parse(day)
        val newDate = sdf.format(date)

        binding.editTextAddIllnessDate.setText(newDate)

    }
}
/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/24/23, 9:19 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.add.medical_meeting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityAddMedicalMeetingBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.MedicalMeeting
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil
import com.cbaelectronics.bitacoradefamilia.util.extension.addClose
import com.cbaelectronics.bitacoradefamilia.util.extension.enable
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import com.cbaelectronics.bitacoradefamilia.util.extension.hideSoftInput
import java.text.SimpleDateFormat

class AddMedicalMeetingActivity : AppCompatActivity() {

    // Properties

    private lateinit var binding: ActivityAddMedicalMeetingBinding
    private lateinit var viewModel: AddMedicalMeetingViewModel
    private var dateEditText: String? = null
    private var doctorEditText: String? = null
    private var noteEditText: String? = null

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMedicalMeetingBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[AddMedicalMeetingViewModel::class.java]

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
        binding.textViewAddMedicalMeetingTitle.text = getString(viewModel.title)
        binding.buttonCancelMedicalMeeting.text = getString(viewModel.cancel)
        binding.buttonSaveMedicalMeeting.text = getString(viewModel.save)
        binding.textFieldAddMedicalMeetingDate.hint = getString(viewModel.editTextDate)
        binding.textFieldAddMedicalMeetingDoctor.hint = getString(viewModel.editTextDoctor)
        binding.textFieldAddMedicalMeetingNote.hint = getString(viewModel.editTextNote)
    }

    private fun setup() {
        addClose(this)

        // UI
        binding.textViewAddMedicalMeetingTitle.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )

        footerInfo()
    }

    private fun footerInfo() {

        // Date

        binding.editTextAddMedicalMeetingDate.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                dateEditText = binding.editTextAddMedicalMeetingDate.text.toString()
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

        binding.editTextAddMedicalMeetingDoctor.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                doctorEditText = binding.editTextAddMedicalMeetingDoctor.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })

        // Notes

        binding.editTextAddMedicalMeetingNote.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                noteEditText = binding.editTextAddMedicalMeetingNote.text.toString()
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
        if (!dateEditText.isNullOrEmpty() && !doctorEditText.isNullOrEmpty() && !noteEditText.isNullOrEmpty()){
            enableSave()
        }
    }

    private fun footer() {
        disableSave()
        binding.buttonCancelMedicalMeeting.setOnClickListener {
            onBackPressed()
        }

        binding.buttonSaveMedicalMeeting.setOnClickListener {
            validForm()
        }
    }

    private fun validForm() {
        val date = binding.editTextAddMedicalMeetingDate.text
        val doctor = binding.editTextAddMedicalMeetingDoctor.text
        val note = binding.editTextAddMedicalMeetingNote.text

        if (date.isNullOrBlank() || doctor.isNullOrBlank() || note.isNullOrBlank()) {
            UIUtil.showAlert(this, getString(viewModel.errorIncomplete))
        } else {
            val sdf = SimpleDateFormat(Constants.DATE)
            val dateFormat = sdf.parse(date.toString())

            val meeting = MedicalMeeting(
                viewModel.children?.id,
                dateFormat,
                doctor.toString(),
                note.toString(),
                viewModel.user
            )

            saveDatabase(meeting)
        }
    }

    private fun saveDatabase(meeting: MedicalMeeting) {
        viewModel.save(meeting)

        clearEditText()
        hideSoftInput()
        UIUtil.showAlert(this, getString(viewModel.ok))
        disableSave()
        finish()
    }

    private fun clearEditText() {
        binding.editTextAddMedicalMeetingDate.text = null
        binding.editTextAddMedicalMeetingDoctor.text = null
        binding.editTextAddMedicalMeetingNote.text = null
    }

    private fun disableSave() {
        binding.buttonSaveMedicalMeeting.enable(false)
        binding.buttonCancelMedicalMeeting.text = getString(viewModel.back)
    }

    private fun enableSave() {
        binding.buttonSaveMedicalMeeting.enable(true)
        binding.buttonCancelMedicalMeeting.text = getString(viewModel.cancel)
    }
}
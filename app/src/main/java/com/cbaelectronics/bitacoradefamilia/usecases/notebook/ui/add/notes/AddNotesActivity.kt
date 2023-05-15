/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/3/23, 10:44 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.notes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityAddNotesBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Notes
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.DatabaseField
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil
import com.cbaelectronics.bitacoradefamilia.util.UIUtil.showAlert
import com.cbaelectronics.bitacoradefamilia.util.extension.addClose
import com.cbaelectronics.bitacoradefamilia.util.extension.enable
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import com.cbaelectronics.bitacoradefamilia.util.extension.hideSoftInput
import java.text.SimpleDateFormat
import java.util.*

class AddNotesActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    // Properties

    private lateinit var binding: ActivityAddNotesBinding
    private lateinit var viewModel: AddNotesViewModel
    private lateinit var type: String
    private var dateEditText: String? = null
    private var noteEditText: String? = null

    private var day = 0
    private var month = 0
    private var year = 0
    private var vDay = 0
    private var vMonth = 0
    private var vYear = 0

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[AddNotesViewModel::class.java]

        // Setup
        localize()
        setup()
        data()
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
        binding.textViewAddNoteTitle.text = getString(viewModel.title)
        binding.buttonSaveNote.text = getString(viewModel.save)
        binding.buttonCancelNote.text = getString(viewModel.cancel)
        binding.textFieldAddNote.hint = getString(viewModel.editTextNote)
    }

    private fun setup() {
        addClose(this)

        // UI
        binding.textViewAddNoteTitle.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )

        // Date and Time

        binding.editTextAddNoteDate.setOnFocusChangeListener { _, hasFocus ->
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

    private fun data(){
        val bundle = intent.extras
        type = bundle?.getString(DatabaseField.NOTE_TYPE.key).toString()
    }

    private fun footer() {
        disableSave()
        binding.buttonSaveNote.setOnClickListener {
            validForm()
        }

        binding.buttonCancelNote.setOnClickListener {
            onBackPressed()
        }
    }

    private fun footerInfo(){

        // Date

        binding.editTextAddNoteDate.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                dateEditText = binding.editTextAddNoteDate.text.toString()
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

        binding.editTextAddNote.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                noteEditText = binding.editTextAddNote.text.toString()
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
        if (!dateEditText.isNullOrEmpty() && !noteEditText.isNullOrEmpty()){
            enableSave()
        }
    }

    private fun validForm() {
        val date = binding.editTextAddNoteDate.text
        val notes = binding.editTextAddNote.text

        if (date.isNullOrEmpty() || notes.isNullOrEmpty()) {
            showAlert(this, getString(viewModel.errorIncomplete))
        } else {

            val sdf = SimpleDateFormat(Constants.DATE)
            val dateFormat = sdf.parse(date.toString())

            val note = Notes(
                viewModel.children?.id,
                dateFormat,
                notes.toString(),
                type,
                viewModel.user
            )

            saveDatabase(note)
        }
    }

    private fun saveDatabase(note: Notes) {
        viewModel.save(note)

        clearEditText()
        hideSoftInput()
        UIUtil.showAlert(this, getString(viewModel.ok))
        disableSave()
        finish()
    }

    private fun clearEditText() {
        binding.editTextAddNoteDate.text = null
        binding.editTextAddNote.text = null

    }

    private fun disableSave() {
        binding.buttonSaveNote.enable(false)
        binding.buttonCancelNote.text = getString(viewModel.back)
    }

    private fun enableSave() {
        binding.buttonSaveNote.enable(true)
        binding.buttonCancelNote.text = getString(viewModel.cancel)
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

        binding.editTextAddNoteDate.setText(newDate)

    }
}
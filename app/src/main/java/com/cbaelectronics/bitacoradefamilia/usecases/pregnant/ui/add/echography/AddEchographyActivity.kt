/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/24/23, 4:17 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.add.echography

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityAddEchographyBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Echography
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil
import com.cbaelectronics.bitacoradefamilia.util.extension.addClose
import com.cbaelectronics.bitacoradefamilia.util.extension.enable
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import com.cbaelectronics.bitacoradefamilia.util.extension.hideSoftInput
import java.text.SimpleDateFormat
import java.util.*

class AddEchographyActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    // Properties

    private lateinit var binding: ActivityAddEchographyBinding
    private lateinit var viewModel: AddEchographyViewModel
    private var dateEditText: String? = null
    private var weekEditText: String? = null
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
        binding = ActivityAddEchographyBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[AddEchographyViewModel::class.java]

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
        binding.textViewAddEchographyTitle.text = getString(viewModel.title)
        binding.buttonCancelEchography.text = getString(viewModel.cancel)
        binding.buttonSaveEchography.text = getString(viewModel.save)
        binding.textFieldAddEchographyDate.hint = getString(viewModel.editTextDate)
        binding.textFieldAddEchographyWeek.hint = getString(viewModel.editTextWeek)
        binding.textFieldAddEchographyNote.hint = getString(viewModel.editTextNote)
    }

    private fun setup() {
        addClose(this)

        // UI
        binding.textViewAddEchographyTitle.font(
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

        binding.editTextAddEchographyWeek.setAdapter(adapterWeek)
        binding.editTextAddEchographyWeek.inputType = InputType.TYPE_NULL

        // Date and Time

        binding.editTextAddEchographyDate.setOnFocusChangeListener { _, hasFocus ->
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
        binding.buttonCancelEchography.setOnClickListener {
            onBackPressed()
        }

        binding.buttonSaveEchography.setOnClickListener {
            validForm()
        }
    }

    private fun footerInfo(){

        // Date

        binding.editTextAddEchographyDate.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                dateEditText = binding.editTextAddEchographyDate.text.toString()
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

        binding.editTextAddEchographyWeek.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                weekEditText = binding.editTextAddEchographyWeek.text.toString()
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

        binding.editTextAddEchographyNote.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                noteEditText = binding.editTextAddEchographyNote.text.toString()
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
        if (!dateEditText.isNullOrEmpty() && !weekEditText.isNullOrEmpty() && !noteEditText.isNullOrEmpty()){
            enableSave()
        }
    }

    private fun validForm() {
        val date = binding.editTextAddEchographyDate.text
        val week = binding.editTextAddEchographyWeek.text
        val note = binding.editTextAddEchographyNote.text

        if (date.isNullOrBlank() || week.isNullOrBlank() || note.isNullOrBlank()) {
            UIUtil.showAlert(this, getString(viewModel.errorIncomplete))
        } else {
            val sdf = SimpleDateFormat(Constants.DATE)
            val dateFormat = sdf.parse(date.toString())

            val echography = Echography(
                viewModel.children?.id,
                dateFormat,
                week.toString().toInt(),
                note.toString(),
                viewModel.user
            )

            saveDatabase(echography)
        }
    }

    private fun saveDatabase(echography: Echography) {
        viewModel.save(echography)

        clearEditText()
        hideSoftInput()
        UIUtil.showAlert(this, getString(viewModel.ok))
        disableSave()
        finish()
    }

    private fun clearEditText() {
        binding.editTextAddEchographyDate.text = null
        binding.editTextAddEchographyWeek.text = null
        binding.editTextAddEchographyNote.text = null
    }

    private fun disableSave() {
        binding.buttonSaveEchography.enable(false)
        binding.buttonCancelEchography.text = getString(viewModel.back)
    }

    private fun enableSave() {
        binding.buttonSaveEchography.enable(true)
        binding.buttonCancelEchography.text = getString(viewModel.cancel)
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

        binding.editTextAddEchographyDate.setText(newDate)

    }
}
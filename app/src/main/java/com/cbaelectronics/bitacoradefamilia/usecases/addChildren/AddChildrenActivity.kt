/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/7/23, 4:02 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.addChildren

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityAddChildrenBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Children
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.DatabaseField
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil.showAlert
import com.cbaelectronics.bitacoradefamilia.util.extension.*
import java.text.SimpleDateFormat
import java.util.*

class AddChildrenActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    // Properties

    private lateinit var binding: ActivityAddChildrenBinding
    private lateinit var viewModel: AddChildrenViewModel
    private var nameEditText: String? = null
    private var genreEditText: String? = null
    private lateinit var childrenJSON: String
    private var children: Children? = null

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var vDay = 0
    private var vMonth = 0
    private var vYear = 0
    private var vHour = 0
    private var vMinute = 0

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddChildrenBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[AddChildrenViewModel::class.java]

        // Setup
        data()
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

    private fun data(){
        val bundle = intent.extras
        childrenJSON = bundle?.getString(DatabaseField.CHILDREN.key).toString()
        children = Children.fromJson(childrenJSON)

    }

    private fun localize() {
        binding.textViewAddChildrenTitle.text = getString(viewModel.title)
        binding.textFieldAddChildrenName.hint = if(children?.name.isNullOrEmpty()) getString(viewModel.editTextName) else children?.name
        binding.textFieldAddChildrenGenre.hint = if(children?.genre.isNullOrEmpty()) getString(viewModel.editTextGenre) else children?.genre
        binding.textFieldAddChildrenDate.hint = if(children?.date?.customShortFormat() == Constants.DATE_DEFAULT || children?.date?.customShortFormat().isNullOrEmpty()) getString(viewModel.editTextDate) else children?.date?.customShortFormat()
        binding.textFieldAddChildrenWeight.hint = if(children?.weight.isNullOrEmpty()) getString(viewModel.editTextWeight) else children?.weight
        binding.textFieldAddChildrenHeight.hint = if(children?.height.isNullOrEmpty()) getString(viewModel.editTextHeight) else children?.height
        binding.buttonSaveChildren.text = if(children?.name.isNullOrEmpty()) getString(viewModel.save) else getString(viewModel.edit)
        binding.buttonCancelChildren.text = getString(viewModel.cancel)

        Log.d("ChildrenEdu", children?.date?.customShortFormat().toString())
    }

    private fun setup() {

        addClose(this)

        // UI
        binding.textViewAddChildrenTitle.font(
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
        binding.editTextAddChildrenGenre.setAdapter(adapterGenre)
        binding.editTextAddChildrenGenre.inputType = InputType.TYPE_NULL

        // Date and Time

        binding.editTextAddChildrenDate.setOnFocusChangeListener { _, hasFocus ->
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

        setupInfo()
    }

    private fun footer() {

        // Button Save

        disableSave()
        binding.buttonSaveChildren.setOnClickListener {
            validForm()
        }

        // Button Cancel

        binding.buttonCancelChildren.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupInfo(){

        // Name

        binding.editTextAddChildrenName.addTextChangedListener ( object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nameEditText = binding.editTextAddChildrenName.text.toString()
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

        binding.editTextAddChildrenGenre.setOnItemClickListener { adapterView, view, i, l ->
            hideSoftInput()
            genreEditText = binding.editTextAddChildrenGenre.text.toString()
            checkEnable()
        }

    }

    private fun checkEnable(){
        if (!nameEditText.isNullOrEmpty() && !genreEditText.isNullOrEmpty()){
            enableSave()
        }
    }

    private fun validForm() {
        val name = binding.editTextAddChildrenName.text
        val genre = binding.editTextAddChildrenGenre.text
        val date = binding.editTextAddChildrenDate.text
        val weight = binding.editTextAddChildrenWeight.text
        val height = binding.editTextAddChildrenHeight.text

        if (name.isNullOrBlank() || genre.isNullOrBlank()) {
            showAlert(this, getString(viewModel.errorIncomplete))
        } else {

            val sdf = SimpleDateFormat(Constants.DATE_COMPLETE)
            val date1 = if (date.isNullOrBlank()) sdf.parse("01/01/1900 00:00") else sdf.parse(date.toString())

            val children = Children(
                name = name.toString(),
                genre = genre.toString(),
                date = date1,
                weight = weight.toString(),
                height = height.toString(),
                registeredBy = viewModel.user
            )

            saveDatabase(children)
        }
    }

    private fun saveDatabase(children: Children) {
        viewModel.save(children)

        clearEditText()
        hideSoftInput()
        showAlert(this, getString(viewModel.ok))
        disableSave()
        finish()
    }

    private fun clearEditText() {
        binding.editTextAddChildrenName.text = null
        binding.editTextAddChildrenGenre.text = null
        binding.editTextAddChildrenDate.text = null
        binding.editTextAddChildrenHeight.text = null
        binding.editTextAddChildrenWeight.text = null
        binding.editTextAddChildrenHeight.text = null
    }

    private fun disableSave() {
        binding.buttonSaveChildren.enable(false)
        binding.buttonCancelChildren.text = getString(viewModel.back)
    }

    private fun enableSave() {
        binding.buttonSaveChildren.enable(true)
        binding.buttonCancelChildren.text = getString(viewModel.cancel)
    }

    private fun getDateTimeCalendar() {

        TimeZone.getDefault()
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)

    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        vYear = p1
        vMonth = p2 + 1
        vDay = p3

        getDateTimeCalendar()

        TimePickerDialog(this, R.style.themeOnverlay_timePicker, this, hour, minute, true).show()
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        vHour = p1
        vMinute = p2

        val hour = "$vDay/$vMonth/$vYear $vHour:$vMinute"
        val sdf = SimpleDateFormat(Constants.DATE_COMPLETE)
        val date = sdf.parse(hour)
        val newHour = sdf.format(date)

        binding.editTextAddChildrenDate.setText(newHour)
    }
}
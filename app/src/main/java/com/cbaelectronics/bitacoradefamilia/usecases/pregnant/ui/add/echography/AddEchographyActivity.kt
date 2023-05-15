/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/24/23, 4:17 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.add.echography

import android.Manifest
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityAddEchographyBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Echography
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.FirebaseDBService
import com.cbaelectronics.bitacoradefamilia.usecases.addChildren.AddChildrenActivity
import com.cbaelectronics.bitacoradefamilia.util.extension.addClose
import com.cbaelectronics.bitacoradefamilia.util.extension.enable
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import com.cbaelectronics.bitacoradefamilia.util.extension.hideSoftInput
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*
import com.cbaelectronics.bitacoradefamilia.usecases.addChildren.AddChildrenActivity.request.*
import com.cbaelectronics.bitacoradefamilia.util.*

class AddEchographyActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    // Properties

    private lateinit var binding: ActivityAddEchographyBinding
    private lateinit var viewModel: AddEchographyViewModel
    private var dateEditText: String? = null
    private var weekEditText: String? = null
    private var noteEditText: String? = null
    private var typeEditText: String? = null
    private var typeOther: String? = null
    private var mUri: Uri? = null
    private var echographyPath: String? = ""
    private lateinit var mProgress: ProgressDialog
    private lateinit var echographyId: String

    private var day = 0
    private var month = 0
    private var year = 0
    private var vDay = 0
    private var vMonth = 0
    private var vYear = 0

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){ uri ->
        if(uri != null){
            openDialog()
            mUri = uri
            //viewModel.saveAvatar(childrenId, mUri!!)
            saveAvatar(mUri!!)
            Glide.with(this).load(mUri).into(binding.imageViewEchography)
            binding.imageViewIconUpload.visibility = GONE
            binding.textViewUpload.visibility = GONE
        }else{
            UIUtil.showSnackBar(binding.constraintLayoutAddEchography, getString(viewModel.errorLoad))
        }
    }

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEchographyBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[AddEchographyViewModel::class.java]

        // Dialog
        mProgress = ProgressDialog(this)

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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            REQUEST_GALLERY.value -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openGallery()
                }else{
                    UIUtil.showSnackBar(
                        binding.constraintLayoutAddEchography,
                        getString(viewModel.errorImages)
                    )
                }
            }
        }
    }

    // Private

    private fun data(){
        echographyId = Util.getRandomString(Constants.LENGHT_ECHOGRAPHY_ID)
    }

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
        disableEditTextType()

        // UI
        binding.textViewAddEchographyTitle.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )

        // Week

        val arrayWeek = resources.getStringArray(R.array.week)
        val adapterWeek = ArrayAdapter(this, R.layout.dropdown_menu_popup_item, arrayWeek)

        binding.editTextAddEchographyWeek.setAdapter(adapterWeek)
        binding.editTextAddEchographyWeek.inputType = InputType.TYPE_NULL

        // Type

        val arrayType = resources.getStringArray(R.array.echographies)
        val adapterType = ArrayAdapter(this, R.layout.dropdown_menu_popup_item, arrayType)

        binding.editTextAddEchographyType.setAdapter(adapterType)
        binding.editTextAddEchographyType.inputType = InputType.TYPE_NULL

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

    private fun footerInfo() {

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

        // Week

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

        // Type

        binding.editTextAddEchographyType.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                typeEditText = binding.editTextAddEchographyType.text.toString()
                checkEchography()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })

        // Type Other

        binding.editTextAddEchographyOther.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                typeOther = binding.editTextAddEchographyOther.text.toString()
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

        // Images

        binding.imageViewEchography.setOnClickListener {
            if(checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            ){
                val mPermisoGaleria = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(mPermisoGaleria,
                    REQUEST_GALLERY.value
                )
            }else{
                openGallery()
            }
        }

    }

    private fun saveAvatar(uri: Uri) {
        FirebaseDBService.echographyStorageRef.child(echographyId).putFile(uri).addOnSuccessListener {
            FirebaseDBService.echographyStorageRef.child(echographyId).downloadUrl.addOnSuccessListener {
                echographyPath = it.toString()
                mProgress.cancel()
                Log.d("ChildrenEdu Eco", echographyPath!!)
                checkEnable()
            }
        }

    }

    private fun createAlertOptions(){
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(viewModel.optionTitle))
            .setItems(R.array.optionsCamera, DialogInterface.OnClickListener { _, i ->
                when(i){
                    0 -> {
                        if (checkPermission(Manifest.permission.CAMERA) || checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        {

                            val mPermisoCamara = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            requestPermissions(mPermisoCamara,
                                REQUEST_IMAGE_CAPTURE.value
                            )

                        }else{
                            openCamera()
                        }
                    }

                    1 -> {
                        if(checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        ){
                            val mPermisoGaleria = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                            requestPermissions(mPermisoGaleria,
                                REQUEST_GALLERY.value
                            )
                        }else{
                            openGallery()
                        }
                    }
                }
            })
            .show()
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED
    }

    private fun openCamera() {
        //pickCamera.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
    }

    private fun openGallery(){
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun openDialog(){
        mProgress.setMessage(getString(viewModel.load))
        mProgress.show()
    }

    private fun checkEnable() {
        if (!dateEditText.isNullOrEmpty() && !weekEditText.isNullOrEmpty() && !typeEditText.isNullOrEmpty() && !noteEditText.isNullOrEmpty() && (typeEditText != getString(viewModel.typeOther) || !typeOther.isNullOrEmpty())) {
            enableSave()
        }
    }

    private fun validForm() {
        val date = binding.editTextAddEchographyDate.text
        val week = binding.editTextAddEchographyWeek.text
        val type = if(typeEditText == getString(viewModel.typeOther)) binding.editTextAddEchographyOther.text else binding.editTextAddEchographyType.text
        val note = binding.editTextAddEchographyNote.text

        if (date.isNullOrBlank() || week.isNullOrBlank() || note.isNullOrBlank()) {
            UIUtil.showAlert(this, getString(viewModel.errorIncomplete))
        } else {
            val sdf = SimpleDateFormat(Constants.DATE)
            val dateFormat = sdf.parse(date.toString())

            val echography = Echography(
                echographyId,
                viewModel.children?.id,
                dateFormat,
                week.toString().toInt(),
                type.toString(),
                note.toString(),
                echographyPath,
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

    private fun checkEchography(){
        if (typeEditText == getString(viewModel.typeOther)){
            enableEditTextType()
        }else{
            disableEditTextType()
        }
    }

    private fun disableEditTextType(){
        binding.textFieldAddEchographyOther.visibility = View.GONE
    }

    private fun enableEditTextType(){
        binding.textFieldAddEchographyOther.visibility = View.VISIBLE
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
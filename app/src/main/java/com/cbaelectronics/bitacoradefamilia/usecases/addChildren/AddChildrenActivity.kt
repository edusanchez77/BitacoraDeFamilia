/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/7/23, 4:02 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.addChildren

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityAddChildrenBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Children
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.DatabaseField
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.FirebaseDBService
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil.showAlert
import com.cbaelectronics.bitacoradefamilia.util.UIUtil.showSnackBar
import com.cbaelectronics.bitacoradefamilia.util.Util
import com.cbaelectronics.bitacoradefamilia.util.extension.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class AddChildrenActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    // Properties

    private lateinit var binding: ActivityAddChildrenBinding
    private lateinit var viewModel: AddChildrenViewModel
    private var nameEditText: String? = null
    private var genreEditText: String? = null
    private var dateEditText: String? = null
    private var weightEditText: String? = null
    private var heightEditText: String? = null
    private lateinit var childrenJSON: String
    private var children: Children? = null
    private lateinit var childrenId: String
    private var mUri: Uri? = null
    private var avatarPath: String? = ""
    private lateinit var mProgress: ProgressDialog
    private val REQUEST_GALLERY = 1001
    private val REQUEST_IMAGE_CAPTURE = 1002

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

    private val pickMedia = registerForActivityResult(PickVisualMedia()){ uri ->
        if(uri != null){
            openDialog()
            mUri = uri
            //viewModel.saveAvatar(childrenId, mUri!!)
            saveAvatar(mUri!!)
            Glide.with(this).load(mUri).into(binding.imageViewAddChildrenAvatar)
        }else{
            showSnackBar(binding.constraintLayoutAddChildren, getString(viewModel.errorLoad))
        }
    }

    private val pickCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback() {
        if(it.resultCode == RESULT_OK){
            openDialog()
            val extras = it.data?.extras
            val bitmap = extras?.get("data") as Bitmap
            mUri = Util.getImageUriFromBitmap(this, bitmap) as Uri
            //viewModel.saveAvatar(childrenId, mUri!!)
            saveAvatar(mUri!!)
            Glide.with(this).load(mUri).into(binding.imageViewAddChildrenAvatar)
        }else{
            showSnackBar(binding.constraintLayoutAddChildren, getString(viewModel.errorLoad))
        }
    })

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddChildrenBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[AddChildrenViewModel::class.java]

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
            REQUEST_GALLERY -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openGallery()
                }else{
                    showSnackBar(binding.constraintLayoutAddChildren, getString(viewModel.errorImages))
                }
            }

            REQUEST_IMAGE_CAPTURE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openCamera()
                }else{
                    showSnackBar(binding.constraintLayoutAddChildren, getString(viewModel.errorCamera))
                }
            }

        }
    }

    // Private

    private fun saveAvatar(uri: Uri) {
        FirebaseDBService.avatarStorageRef.child(childrenId).putFile(uri).addOnSuccessListener {
            FirebaseDBService.avatarStorageRef.child(childrenId).downloadUrl.addOnSuccessListener {
                avatarPath = it.toString()
                mProgress.cancel()
                checkEnable()
            }
        }

    }

    private fun data(){
        val bundle = intent.extras
        childrenJSON = bundle?.getString(DatabaseField.CHILDREN.key).toString()
        children = Children.fromJson(childrenJSON)
        childrenId = if(children?.id.isNullOrEmpty()) Util.getRandomString(Constants.LENGHT_CHILDREN_ID) else children?.id.toString()
    }

    private fun localize() {

        binding.textViewAddChildrenTitle.text = getString(viewModel.title)
        binding.buttonSaveChildren.text = if(children?.name.isNullOrEmpty()) getString(viewModel.save) else getString(viewModel.edit)
        binding.buttonCancelChildren.text = getString(viewModel.cancel)

        if(checkEdit()){
            Glide.with(this).load(children?.avatar).into(binding.imageViewAddChildrenAvatar)
            binding.editTextAddChildrenName.setText(children?.name)
            binding.editTextAddChildrenGenre.setText(children?.genre)
            binding.editTextAddChildrenWeight.setText(children?.weight)
            binding.editTextAddChildrenHeight.setText(children?.height)
            if(children?.date?.customShortFormat() == Constants.DATE_DEFAULT || children?.date?.customShortFormat().isNullOrEmpty()){
                binding.textFieldAddChildrenDate.hint = getString(viewModel.editTextDate)
            }else{
                binding.editTextAddChildrenDate.setText(children?.date?.customShortFormat())
            }
        }else{
            Glide.with(this).load(Constants.AVATAR_DEFAULT).into(binding.imageViewAddChildrenAvatar)
            binding.textFieldAddChildrenName.hint = getString(viewModel.editTextName)
            binding.textFieldAddChildrenGenre.hint = getString(viewModel.editTextGenre)
            binding.textFieldAddChildrenDate.hint = getString(viewModel.editTextDate)
            binding.textFieldAddChildrenWeight.hint = getString(viewModel.editTextWeight)
            binding.textFieldAddChildrenHeight.hint = getString(viewModel.editTextHeight)
        }
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

        // Date

        binding.editTextAddChildrenDate.addTextChangedListener ( object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                dateEditText = binding.editTextAddChildrenDate.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        } )

        // Weight

        binding.editTextAddChildrenWeight.addTextChangedListener ( object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                weightEditText = binding.editTextAddChildrenWeight.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        } )

        // Height

        binding.editTextAddChildrenHeight.addTextChangedListener ( object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                heightEditText = binding.editTextAddChildrenHeight.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        } )

        // Avatar
        binding.imageViewAddChildrenAvatar.setOnClickListener {
            createAlertOptions()
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
                            requestPermissions(mPermisoCamara, REQUEST_IMAGE_CAPTURE)

                        }else{
                            openCamera()
                        }
                    }

                    1 -> {
                        if(checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        ){
                            val mPermisoGaleria = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                            requestPermissions(mPermisoGaleria, REQUEST_GALLERY)
                        }else{
                            openGallery()
                        }
                    }
                }
            })
            .show()
    }

    private fun openCamera() {
        pickCamera.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
    }

    private fun openGallery(){
        pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
    }

    private fun openDialog(){
        mProgress.setMessage(getString(viewModel.load))
        mProgress.show()
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED
    }


    private fun checkEnable(){
        if(checkEdit()){
            if (!nameEditText.isNullOrEmpty() || !genreEditText.isNullOrEmpty() || !dateEditText.isNullOrEmpty() || !weightEditText.isNullOrEmpty() || !heightEditText.isNullOrEmpty() || !avatarPath.isNullOrEmpty()){
                enableSave()
            }
        }else{
            if (!nameEditText.isNullOrEmpty() && !genreEditText.isNullOrEmpty()){
                enableSave()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun validForm() {

        val name = if (binding.editTextAddChildrenName.text.isNullOrEmpty()) children?.name else binding.editTextAddChildrenName.text.toString()
        val genre = if (binding.editTextAddChildrenGenre.text.isNullOrEmpty()) children?.genre else binding.editTextAddChildrenGenre.text.toString()
        val date = if (binding.editTextAddChildrenDate.text.isNullOrEmpty()) children?.date.toString() else binding.editTextAddChildrenDate.text
        val weight = if (binding.editTextAddChildrenWeight.text.isNullOrEmpty()) children?.weight else binding.editTextAddChildrenWeight.text.toString()
        val height = if (binding.editTextAddChildrenHeight.text.isNullOrEmpty()) children?.height else binding.editTextAddChildrenHeight.text.toString()
        val avatar = if (avatarPath.isNullOrEmpty()) children?.avatar else avatarPath
        val registeredDate = if(checkEdit()) children?.registeredDate else Timestamp(Date().time)

        val sdf = SimpleDateFormat(Constants.DATE_COMPLETE)
        val date1 = if (date == "null") sdf.parse(Constants.DATE_DEFAULT) else sdf.parse(date.toString())

        val childrenNew = Children(
            id = childrenId,
            name = name,
            avatar = avatar,
            genre = genre,
            date = date1,
            weight = weight,
            height = height,
            registeredBy = viewModel.user,
            registeredDate = registeredDate
        )

        saveDatabase(childrenNew)
    }

    private fun saveDatabase(children: Children) {
        if(checkEdit()){
            viewModel.update(children)
        }else{
            viewModel.save(children)

        }

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

    private fun checkEdit(): Boolean{
        return childrenJSON != "null"
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
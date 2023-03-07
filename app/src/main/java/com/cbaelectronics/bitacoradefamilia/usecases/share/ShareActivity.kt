/**
 *  Created by CbaElectronics by Eduardo Sanchez on 3/1/23, 10:28 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.share

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityShareBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.ControlWeight
import com.cbaelectronics.bitacoradefamilia.model.domain.Permission
import com.cbaelectronics.bitacoradefamilia.model.domain.SharedChildren
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil
import com.cbaelectronics.bitacoradefamilia.util.extension.addClose
import com.cbaelectronics.bitacoradefamilia.util.extension.enable
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import com.cbaelectronics.bitacoradefamilia.util.extension.hideSoftInput

class ShareActivity : AppCompatActivity() {

    // Properties

    private lateinit var binding: ActivityShareBinding
    private lateinit var viewModel: ShareViewModel
    private var emailEditText: String? = null
    private var permissionEditText: String? = null

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[ShareViewModel::class.java]

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
        binding.textViewShareTitle.text = getString(viewModel.title)
        binding.textViewName.text = viewModel.children?.name
        Glide.with(binding.root.context).load(viewModel.children?.avatar)
            .into(binding.imageViewAvatar)
        binding.buttonCancelShare.text = getString(viewModel.cancel)
        binding.buttonSaveShare.text = getString(viewModel.save)
    }

    private fun setup() {
        addClose(this)

        // UI
        binding.textViewShareTitle.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )

        binding.textViewName.font(
            FontSize.TITLE,
            FontType.GALADA,
            ContextCompat.getColor(this, R.color.text)
        )

        // Permission

        val arrayPermission = resources.getStringArray(R.array.permission)
        val adapterPermission = ArrayAdapter(
            this,
            R.layout.dropdown_menu_popup_item,
            arrayPermission
        )
        binding.editTextSharePermission.setAdapter(adapterPermission)
        binding.editTextSharePermission.inputType = InputType.TYPE_NULL

        footerInfo()
    }

    private fun footer() {
        disableSave()
        binding.buttonCancelShare.setOnClickListener {
            onBackPressed()
        }

        binding.buttonSaveShare.setOnClickListener {
            validForm()
        }
    }

    private fun footerInfo() {
        // Email

        binding.editTextShareUser.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                emailEditText = binding.editTextShareUser.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })

        // Permission

        binding.editTextSharePermission.setOnItemClickListener { adapterView, view, i, l ->
            hideSoftInput()
            permissionEditText = binding.editTextSharePermission.text.toString()
            checkEnable()
        }
    }

    private fun checkEnable() {
        if (!emailEditText.isNullOrEmpty() && !permissionEditText.isNullOrEmpty()) {
            enableSave()
        }
    }

    private fun validForm() {
        val email = binding.editTextShareUser.text
        val permission = binding.editTextSharePermission.text

        if (email.isNullOrBlank() || permission.isNullOrBlank()) {
            UIUtil.showAlert(this, getString(viewModel.errorIncomplete))
        } else {
            val permissionInt = when (permission.toString()) {
                Permission.READ.type -> Permission.READ.value
                Permission.WRITE.type -> Permission.WRITE.value
                else -> Permission.READ.value
            }

            val shared = SharedChildren(
                viewModel.children?.id,
                viewModel.children?.name,
                viewModel.children?.genre,
                viewModel.children?.avatar,
                viewModel.user,
                null,
                permissionInt
            )
            saveDatabase(shared)
        }
    }

    private fun saveDatabase(sharedChildren: SharedChildren) {
        viewModel.save(sharedChildren)

        clearEditText()
        hideSoftInput()
        UIUtil.showAlert(this, getString(viewModel.ok))
        disableSave()
        finish()
    }

    private fun clearEditText() {
        binding.editTextShareUser.text = null
        binding.editTextSharePermission.text = null
    }

    private fun disableSave() {
        binding.buttonSaveShare.enable(false)
        binding.buttonCancelShare.text = getString(viewModel.back)
    }

    private fun enableSave() {
        binding.buttonSaveShare.enable(true)
        binding.buttonCancelShare.text = getString(viewModel.cancel)
    }
}
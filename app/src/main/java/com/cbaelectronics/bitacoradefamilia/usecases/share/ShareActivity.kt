/**
 *  Created by CbaElectronics by Eduardo Sanchez on 3/1/23, 10:28 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.share

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityShareBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Permission
import com.cbaelectronics.bitacoradefamilia.model.domain.SharedChildren
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.DatabaseField
import com.cbaelectronics.bitacoradefamilia.usecases.common.rows.SharedWithRecyclerViewAdapter
import com.cbaelectronics.bitacoradefamilia.usecases.common.rows.UsersRecyclerViewAdapter
import com.cbaelectronics.bitacoradefamilia.usecases.searchUser.SearchUserActivity
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil
import com.cbaelectronics.bitacoradefamilia.util.extension.*

class ShareActivity : AppCompatActivity(), UsersRecyclerViewAdapter.onClickUserListener {

    // Properties

    private lateinit var binding: ActivityShareBinding
    private lateinit var viewModel: ShareViewModel
    private lateinit var adapter: SharedWithRecyclerViewAdapter
    private var emailEditText: String? = null
    private var permissionEditText: String? = null
    private var user: User? = null

    // Initialization

    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val userName =
                    activityResult.data?.getStringExtra(DatabaseField.DISPLAY_NAME.key).orEmpty()
                val userEmail =
                    activityResult.data?.getStringExtra(DatabaseField.EMAIL.key).orEmpty()
                val userPhoto =
                    activityResult.data?.getStringExtra(DatabaseField.PROFILE_IMAGE_URL.key)
                        .orEmpty()
                val token = activityResult.data?.getStringExtra(DatabaseField.TOKEN.key).orEmpty()
                val registeredDate =
                    activityResult.data?.getStringExtra(DatabaseField.REGISTERED_DATE.key).orEmpty()
                val userType = activityResult.data?.getStringExtra(DatabaseField.TYPE.key).orEmpty()

                user = User(
                    userName,
                    userEmail,
                    userPhoto,
                    token,
                    userType.toInt(),
                    registeredDate.parseFirebase()
                )
                binding.editTextShareUser.setText(userName)
                emailEditText = userName
                checkEnable()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[ShareViewModel::class.java]

        // Adapter
        adapter = SharedWithRecyclerViewAdapter(binding.root.context, this)

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

    override fun onItemClick(user: User) {

        val positive = getString(viewModel.positive)
        val negative = getString(viewModel.negative)

        UIUtil.showAlert(
            context = this,
            message = viewModel.messageQuestion(this, user.displayName.toString()),
            positive = positive,
            negative = negative,
            positiveAction = {
                viewModel.delete(user.id.toString())
                UIUtil.showSnackBar(
                    binding.linearLayoutSharedWith,
                    viewModel.messageDone(this, user.displayName.toString())
                )
            }
        )
    }

    // Private

    private fun localize() {
        binding.textViewShareTitle.text = getString(viewModel.title)
        binding.textViewSharedWithTitle.text = getString(viewModel.with)
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

        binding.textViewSharedWithTitle.font(
            FontSize.CAPTION,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )

        binding.textViewName.font(
            FontSize.TITLE,
            FontType.GALADA,
            ContextCompat.getColor(this, R.color.text)
        )

        binding.recyclerViewSharedWith.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewSharedWith.adapter = adapter

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
        observeData()
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

    private fun observeData() {
        viewModel.load().observe(this, Observer { listUsers ->

            adapter.setDataList(listUsers)
            adapter.notifyDataSetChanged()

            if (listUsers.size == 0) {
                binding.textViewSharedWithTitle.visibility = GONE
            } else {
                binding.textViewSharedWithTitle.visibility = VISIBLE
            }


        })
    }

    private fun footerInfo() {

        // User

        binding.editTextShareUser.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                hideSoftInput()
                val intent = Intent(this, SearchUserActivity::class.java)
                responseLauncher.launch(intent)
            }
        }

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
                id = viewModel.children?.id,
                name = viewModel.children?.name,
                genre = viewModel.children?.genre,
                avatar = viewModel.children?.avatar,
                registeredBy = viewModel.user,
                user = user!!,
                permission = permissionInt
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
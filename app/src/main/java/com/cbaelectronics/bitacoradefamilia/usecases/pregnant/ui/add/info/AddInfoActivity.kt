/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/23/23, 11:52 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.add.info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityAddInfoBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.PregnantInfo
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil
import com.cbaelectronics.bitacoradefamilia.util.extension.addClose
import com.cbaelectronics.bitacoradefamilia.util.extension.enable
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import com.cbaelectronics.bitacoradefamilia.util.extension.hideSoftInput

class AddInfoActivity : AppCompatActivity() {

    // Properties

    private lateinit var binding: ActivityAddInfoBinding
    private lateinit var viewModel: AddInfoViewModel
    private var whenEditText: String? = null
    private var howEditText: String? = null
    private var reactionsEditText: String? = null

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddInfoBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[AddInfoViewModel::class.java]

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

    private fun localize(){
        binding.textViewAddInfoTitle.text = getString(viewModel.title)
        binding.buttonCancelInfo.text = getString(viewModel.cancel)
        binding.buttonSaveInfo.text = getString(viewModel.save)
        binding.textFieldAddInfoWhen.hint = getString(viewModel.editTextWhen)
        binding.textFieldAddInfoHow.hint = getString(viewModel.editTextHow)
        binding.textFieldAddInfoReactions.hint = getString(viewModel.editTextReactions)
    }

    private fun setup(){
        addClose(this)

        // UI
        binding.textViewAddInfoTitle.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )

        footerInfo()
    }

    private fun footer(){
        disableSave()
        binding.buttonCancelInfo.setOnClickListener {
            onBackPressed()
        }

        binding.buttonSaveInfo.setOnClickListener {
            validForm()
        }
    }

    private fun footerInfo(){

        // When

        binding.editTextAddInfoWhen.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                whenEditText = binding.editTextAddInfoWhen.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })

        // How

        binding.editTextAddInfoHow.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                howEditText = binding.editTextAddInfoHow.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })

        // Reactions

        binding.editTextAddInfoReactions.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                reactionsEditText = binding.editTextAddInfoReactions.text.toString()
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
        if (!whenEditText.isNullOrEmpty() && !howEditText.isNullOrEmpty() && !reactionsEditText.isNullOrEmpty()){
            enableSave()
        }
    }

    private fun validForm() {
        val mWhen = binding.editTextAddInfoWhen.text
        val how = binding.editTextAddInfoHow.text
        val reaction = binding.editTextAddInfoReactions.text

        if (mWhen.isNullOrBlank() || how.isNullOrBlank()) {
            UIUtil.showAlert(this, getString(viewModel.errorIncomplete))
        } else {
            val info = PregnantInfo(
                viewModel.children?.id,
                mWhen.toString(),
                how.toString(),
                reaction.toString(),
                viewModel.user
            )

            saveDatabase(info)
        }
    }

    private fun saveDatabase(info: PregnantInfo) {
        viewModel.save(info)

        clearEditText()
        hideSoftInput()
        UIUtil.showAlert(this, getString(viewModel.ok))
        disableSave()
        finish()
    }

    private fun clearEditText() {
        binding.editTextAddInfoWhen.text = null
        binding.editTextAddInfoHow.text = null
        binding.editTextAddInfoReactions.text = null
    }

    private fun disableSave() {
        binding.buttonSaveInfo.enable(false)
        binding.buttonCancelInfo.text = getString(viewModel.back)
    }

    private fun enableSave() {
        binding.buttonSaveInfo.enable(true)
        binding.buttonCancelInfo.text = getString(viewModel.cancel)
    }
}
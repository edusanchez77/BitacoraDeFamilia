/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/6/23, 9:27 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.achievements

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityAddAchievementsBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Achievements
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

class AddAchievementsActivity : AppCompatActivity() {

    // Properties

    private lateinit var binding: ActivityAddAchievementsBinding
    private lateinit var viewModel: AddAchievementsViewModel
    private var dateEditText: String? = null
    private var achievementEditText: String? = null

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAchievementsBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[AddAchievementsViewModel::class.java]

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
        binding.textViewAddAchievementsTitle.text = getString(viewModel.title)
        binding.textFieldAddAchievementsDate.hint = getString(viewModel.editTextDate)
        binding.textFieldAddAchievementsName.hint = getString(viewModel.editTextAchievementsName)
        binding.textFieldAddAchievementsDetails.hint = getString(viewModel.editTextDetails)
        binding.buttonSaveAchievements.text = getString(viewModel.save)
        binding.buttonCancelAchievements.text = getString(viewModel.cancel)
    }

    private fun setup() {
        addClose(this)

        // UI
        binding.textViewAddAchievementsTitle.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )

        setupInfo()
    }

    private fun footer() {
        disableSave()

        binding.buttonSaveAchievements.setOnClickListener {
            validForm()
        }

        binding.buttonCancelAchievements.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupInfo(){

        // Date

        binding.editTextAddAchievementsDate.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                dateEditText = binding.editTextAddAchievementsDate.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })

        // Achievement

        binding.editTextAddAchievementsName.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                achievementEditText = binding.editTextAddAchievementsName.text.toString()
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
        if (!dateEditText.isNullOrEmpty() && !achievementEditText.isNullOrEmpty()){
            enableSave()
        }
    }

    private fun enableSave(){
        binding.buttonSaveAchievements.enable(true)
        binding.buttonCancelAchievements.text = getString(viewModel.cancel)
    }

    private fun disableSave() {
        binding.buttonSaveAchievements.enable(false)
        binding.buttonCancelAchievements.text = getString(viewModel.back)
    }

    private fun validForm() {
        val date = binding.editTextAddAchievementsDate.text
        val achievementName = binding.editTextAddAchievementsName.text
        val detail = binding.editTextAddAchievementsDetails.text

        if (date.isNullOrEmpty() || achievementName.isNullOrEmpty()) {
            showAlert(this, getString(viewModel.errorIncomplete))
        } else {
            val sdf = SimpleDateFormat(Constants.DATE)
            val dateFormat = sdf.parse(date.toString())

            val achievement = Achievements(
                viewModel.children?.id,
                dateFormat,
                achievementName.toString(),
                detail.toString(),
                viewModel.user
            )

            saveDatabase(achievement)
        }
    }

    private fun saveDatabase(achievement: Achievements) {
        viewModel.save(achievement)

        clearEditText()
        hideSoftInput()
        UIUtil.showAlert(this, getString(viewModel.ok))
        onBackPressed()
    }

    private fun clearEditText() {
        binding.editTextAddAchievementsDate.text = null
        binding.editTextAddAchievementsName.text = null
        binding.editTextAddAchievementsDetails.text = null
    }
}
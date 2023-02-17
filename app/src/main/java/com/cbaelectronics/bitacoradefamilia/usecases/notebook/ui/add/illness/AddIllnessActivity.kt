/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/3/23, 2:59 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.illness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityAddIllnessBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Illness
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil
import com.cbaelectronics.bitacoradefamilia.util.extension.*
import java.text.SimpleDateFormat

class AddIllnessActivity : AppCompatActivity() {

    // Properties

    private lateinit var binding: ActivityAddIllnessBinding
    private lateinit var viewModel: AddIllnessViewModel

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddIllnessBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[AddIllnessViewModel::class.java]

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
        binding.textViewAddIllnesTitle.text = getString(viewModel.title)
        binding.textFieldAddIllnessDate.hint = getString(viewModel.editTextDate)
        binding.textFieldAddIllnessName.hint = getString(viewModel.editTextIllnessName)
        binding.textFieldAddIllnessDuration.hint = getString(viewModel.editTextDuration)
        binding.textFieldAddIllnessSymptom.hint = getString(viewModel.editTextSymptom)
        binding.textFieldAddIllnessMedication.hint = getString(viewModel.editTextMedication)
        binding.textFieldAddIllnessObservation.hint = getString(viewModel.editTextObservation)
        binding.buttonSaveIllness.text = getString(viewModel.save)
        binding.buttonCancelIllness.text = getString(viewModel.cancel)
    }

    private fun setup() {
        addClose(this)

        // UI
        binding.textViewAddIllnesTitle.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )
    }

    private fun footer() {
        binding.buttonSaveIllness.setOnClickListener {
            validForm()
        }

        binding.buttonCancelIllness.setOnClickListener {
            onBackPressed()
        }
    }

    private fun validForm() {
        val date = binding.editTextAddIllnessDate.text
        val illnessName = binding.editTextAddIllnessName.text
        val symptom = binding.editTextAddIllnessSymptom.text
        val duration = binding.editTextAddIllnessDuration.text
        val medication = binding.editTextAddIllnessMedication.text
        val observations = binding.editTextAddIllnessObservation.text

        if (date.isNullOrEmpty() || illnessName.isNullOrEmpty() || symptom.isNullOrEmpty() || duration.isNullOrEmpty() || medication.isNullOrEmpty()) {
            UIUtil.showAlert(binding.root.context, getString(viewModel.errorIncomplete))
        } else {

            val sdf = SimpleDateFormat(Constants.DATE)
            val dateFormat = sdf.parse(date.toString())

            val illness = Illness(
                viewModel.children?.id,
                dateFormat,
                illnessName.toString(),
                symptom.toString(),
                duration.toString().toInt(),
                medication.toString(),
                observations.toString(),
                viewModel.user
            )

            saveDatabase(illness)
        }
    }

    private fun saveDatabase(illness: Illness) {
        viewModel.save(illness)

        clearEditText()
        hideSoftInput()
        UIUtil.showAlert(this, getString(viewModel.ok))
        onBackPressed()
    }

    private fun clearEditText() {
        binding.editTextAddIllnessDate.text = null
        binding.editTextAddIllnessName.text = null
        binding.editTextAddIllnessSymptom.text = null
        binding.editTextAddIllnessDuration.text = null
        binding.editTextAddIllnessMedication.text = null
        binding.editTextAddIllnessObservation.text = null
    }
}
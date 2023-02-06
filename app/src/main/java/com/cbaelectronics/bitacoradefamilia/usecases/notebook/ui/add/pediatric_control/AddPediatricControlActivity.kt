/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/6/23, 8:48 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.pediatric_control

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityAddPediatricControlBinding
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.addClose
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class AddPediatricControlActivity : AppCompatActivity() {

    // Properties

    private lateinit var binding: ActivityAddPediatricControlBinding
    private lateinit var viewModel: AddPediatricControlViewModel

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPediatricControlBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[AddPediatricControlViewModel::class.java]

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
        binding.textViewAddPediatricControlTitle.text = getString(viewModel.title)
        binding.textFieldAddPediatricControlDate.hint = getString(viewModel.editTextDate)
        binding.textFieldAddPediatricControlDoctor.hint = getString(viewModel.editTextDoctor)
        binding.textFieldAddPediatricControlDoctorSpeciality.hint = getString(viewModel.editTextSpeciality)
        binding.textFieldAddPediatricControlWeight.hint = getString(viewModel.editTextWeight)
        binding.textFieldAddPediatricControlHeight.hint = getString(viewModel.editTextHeight)
        binding.textFieldAddPediatricControlObservation.hint = getString(viewModel.editTextObservation)
        binding.textFieldAddPediatricControlNextControl.hint = getString(viewModel.editTextNext)
        binding.textFieldAddPediatricControlNotes.hint = getString(viewModel.editTextNotes)
        binding.buttonSavePediatricControl.text = getString(viewModel.save)
        binding.buttonCancelPediatricControl.text = getString(viewModel.cancel)
    }

    private fun setup(){
        addClose(this)

        // UI
        binding.textViewAddPediatricControlTitle.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )
    }

    private fun footer(){
        binding.buttonSavePediatricControl.setOnClickListener {
            Toast.makeText(this, "Add Pediatric Control", Toast.LENGTH_SHORT).show()
        }

        binding.buttonCancelPediatricControl.setOnClickListener {
            Toast.makeText(this, "Cancel Pediatric Control", Toast.LENGTH_SHORT).show()
        }
    }
}
/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/6/23, 9:27 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.achievements

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityAddAchievementsBinding
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.addClose
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class AddAchievementsActivity : AppCompatActivity() {

    // Properties

    private lateinit var binding:ActivityAddAchievementsBinding
    private lateinit var viewModel: AddAchievementsViewModel

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

    private fun localize(){
        binding.textViewAddAchievementsTitle.text = getString(viewModel.title)
        binding.textFieldAddAchievementsDate.hint = getString(viewModel.editTextDate)
        binding.textFieldAddAchievementsName.hint = getString(viewModel.editTextAchievementsName)
        binding.textFieldAddAchievementsDetails.hint = getString(viewModel.editTextDetails)
        binding.buttonSaveAchievements.text = getString(viewModel.save)
        binding.buttonCancelAchievements.text = getString(viewModel.cancel)
    }

    private fun setup(){
        addClose(this)

        // UI
        binding.textViewAddAchievementsTitle.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )
    }

    private fun footer(){
        binding.buttonSaveAchievements.setOnClickListener {
            Toast.makeText(this, "Add Achievements", Toast.LENGTH_SHORT).show()
        }

        binding.buttonCancelAchievements.setOnClickListener {
            Toast.makeText(this, "Cancel Achievements", Toast.LENGTH_SHORT).show()
        }
    }
}
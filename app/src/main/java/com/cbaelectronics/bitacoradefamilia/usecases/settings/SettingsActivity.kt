/**
 *  Created by CbaElectronics by Eduardo Sanchez on 3/1/23, 10:25 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivitySettingsBinding
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.addClose
import com.cbaelectronics.bitacoradefamilia.util.extension.enable
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class SettingsActivity : AppCompatActivity() {

    // Properties

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SettingsViewModel

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

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
        binding.textViewUsername.text = viewModel.user.displayName
        binding.textViewUserMail.text = viewModel.user.email
        binding.textViewAccount.text = getString(viewModel.title)
        binding.buttonCancelSettings.text = getString(viewModel.cancel)
        binding.buttonSaveSettings.text = getString(viewModel.save)
        Glide.with(this).load(viewModel.user.photoProfile).into(binding.imageViewAvatar)
    }

    private fun setup() {
        addClose(this)

        // UI
        binding.textViewAccount.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )

        binding.textViewUsername.font(
            FontSize.HEAD,
            FontType.BOLD,
            ContextCompat.getColor(binding.root.context, R.color.light)
        )

        binding.textViewUserMail.font(
            FontSize.BODY,
            FontType.LIGHT,
            ContextCompat.getColor(binding.root.context, R.color.light)
        )
    }

    private fun footer() {
        disableSave()
        binding.buttonCancelSettings.setOnClickListener {
            onBackPressed()
        }

        binding.buttonSaveSettings.setOnClickListener {

        }
    }

    private fun disableSave() {
        binding.buttonSaveSettings.enable(false)
        binding.buttonCancelSettings.text = getString(viewModel.back)
    }

    private fun enableSave() {
        binding.buttonSaveSettings.enable(true)
        binding.buttonCancelSettings.text = getString(viewModel.cancel)
    }
}
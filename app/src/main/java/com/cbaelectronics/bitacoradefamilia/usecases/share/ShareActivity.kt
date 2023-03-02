/**
 *  Created by CbaElectronics by Eduardo Sanchez on 3/1/23, 10:28 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.share

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityShareBinding
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.addClose
import com.cbaelectronics.bitacoradefamilia.util.extension.enable
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class ShareActivity : AppCompatActivity() {

    // Properties

    private lateinit var binding: ActivityShareBinding
    private lateinit var viewModel: ShareViewModel

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
    }

    private fun footer() {
        disableSave()
        binding.buttonCancelShare.setOnClickListener {
            onBackPressed()
        }

        binding.buttonSaveShare.setOnClickListener {

        }
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
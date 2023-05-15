/**
 *  Created by CbaElectronics by Eduardo Sanchez on 3/1/23, 10:22 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityAboutBinding
import com.cbaelectronics.bitacoradefamilia.usecases.onboarding.OnboardingRouter
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.addCloseWithoutTitle
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import com.cbaelectronics.bitacoradefamilia.util.extension.navigation

class AboutActivity : AppCompatActivity() {

    // Properties

    private lateinit var binding: ActivityAboutBinding
    private lateinit var viewModel: AboutViewModel

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[AboutViewModel::class.java]

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
        binding.textViewBy.text = getString(viewModel.byText)
        binding.textViewInfo.text = getString(viewModel.infoText)
        binding.textViewVersion.text = viewModel.versionText(this)
        binding.buttonOnboarding.text = getString(viewModel.onboardingText)
        binding.buttonSite.text = getString(viewModel.siteText)
    }

    private fun setup(){
        addCloseWithoutTitle(this)

        // UI
        binding.textViewBy.font(FontSize.BODY, FontType.LIGHT, getColor(R.color.light))
        binding.textViewInfo.font(FontSize.BODY, FontType.LIGHT, getColor(R.color.light))
        binding.buttonSite.font(FontSize.BODY, FontType.LIGHT, getColor(R.color.text))
        binding.buttonOnboarding.font(FontSize.BODY, FontType.LIGHT, getColor(R.color.text))
        binding.textViewVersion.font(FontSize.CAPTION, FontType.LIGHT, getColor(R.color.text))
    }

    private fun footer(){

        binding.imageButtonInstagram.setOnClickListener {
            viewModel.open(this, Network.INSTAGRAM)
        }

        binding.imageButtonFacebook.setOnClickListener {
            viewModel.open(this, Network.FACEBOOK)
        }

        binding.imageButtonTwitter.setOnClickListener {
            viewModel.open(this, Network.TWITTER)
        }

        binding.buttonOnboarding.setOnClickListener {
            OnboardingRouter().launch(this)
        }

        binding.buttonSite.navigation {
            viewModel.openSite(this)
        }
    }
}
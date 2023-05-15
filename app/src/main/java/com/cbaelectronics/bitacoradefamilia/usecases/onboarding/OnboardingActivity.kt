/**
 *  Created by CbaElectronics by Eduardo Sanchez on 3/1/23, 10:26 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityOnboardingBinding
import com.cbaelectronics.bitacoradefamilia.provider.preferences.PreferencesKey
import com.cbaelectronics.bitacoradefamilia.provider.preferences.PreferencesProvider
import com.cbaelectronics.bitacoradefamilia.usecases.login.LoginRouter
import com.cbaelectronics.bitacoradefamilia.usecases.onboarding.pages.OnboardingPageAdapter
import com.cbaelectronics.bitacoradefamilia.util.extension.primary
import com.cbaelectronics.bitacoradefamilia.util.extension.secondary

class OnboardingActivity : AppCompatActivity() {

    // Properties

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var viewModel: OnboardingViewModel
    private var selection = 0
    private var dots: Array<TextView?> = arrayOfNulls(0)

    private var onboardingKey: Boolean? = false

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[OnboardingViewModel::class.java]

        // Setup
        localize()
        setup()
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
        binding.buttonPrev.text = getText(viewModel.previousText)
        binding.buttonNext.text = getText(viewModel.nextText)
    }

    private fun setup() {
        // UI
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()

        onboardingKey = PreferencesProvider.bool(this, PreferencesKey.ONBOARDING)

        // Adapter
        slider()

        // Dots
        dots(0)

        buttons()

    }

    private fun buttons() {

        binding.buttonPrev.secondary {
            selection -= 1
            binding.viewPagerOnboarding.setCurrentItem(selection, true)
        }

        binding.buttonNext.primary {
            if (selection == viewModel.pages - 1) {
                if (onboardingKey == false){
                    PreferencesProvider.set(this, PreferencesKey.ONBOARDING, true)
                    LoginRouter().launch(this)
                }
                finish()
            } else {
                selection += 1
                binding.viewPagerOnboarding.setCurrentItem(selection, true)
            }
        }
    }

    private fun slider() {
        binding.viewPagerOnboarding.adapter = OnboardingPageAdapter(this, viewModel.data)

        binding.viewPagerOnboarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                dots(position)
                selection = position

                binding.buttonPrev.visibility = if (position == 0) View.INVISIBLE else View.VISIBLE
                binding.buttonNext.text = getText(if (position == viewModel.pages - 1) viewModel.understoodText else viewModel.nextText)
            }
        })
    }

    private fun dots(position: Int) {
        dots = arrayOfNulls(viewModel.pages)
        binding.layoutDots.removeAllViews()
        for (index in dots.indices) {
            dots[index] = TextView(this)
            dots[index]?.text = Html.fromHtml("•")
            dots[index]?.textSize = 35f
            dots[index]?.setTextColor(getColor(if (index == position) R.color.primary else R.color.primary_dark))
            binding.layoutDots.addView(dots[index])
        }
    }
}
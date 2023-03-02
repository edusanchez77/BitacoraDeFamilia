package com.cbaelectronics.bitacoradefamilia.usecases.launch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityLaunchBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Onboarding
import com.cbaelectronics.bitacoradefamilia.model.session.Session
import com.cbaelectronics.bitacoradefamilia.provider.preferences.PreferencesKey
import com.cbaelectronics.bitacoradefamilia.provider.preferences.PreferencesProvider
import com.cbaelectronics.bitacoradefamilia.usecases.home.HomeActivity
import com.cbaelectronics.bitacoradefamilia.usecases.login.LoginActivity
import com.cbaelectronics.bitacoradefamilia.usecases.onboarding.OnboardingActivity
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class LaunchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // Setup
        setup()
        data()
    }

    private fun setup() {
        // UI
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding.textViewTitleBitacora.font(FontSize.TITLE_APP, FontType.GALADA, ContextCompat.getColor(this, R.color.text))
        binding.textViewTitleFamilia.font(FontSize.TITLE_APP, FontType.GALADA, ContextCompat.getColor(this, R.color.text))
        binding.textViewTitleDe.font(FontSize.TITLE_APP, FontType.GALADA, ContextCompat.getColor(this, R.color.secondary))
    }

    private fun data() {

        // Session
        Session.instance.configure(this)
        validateSession()

    }

    private fun validateSession() {
        val session = PreferencesProvider.string(this, PreferencesKey.AUTH_USER)

        var nextActivity = if (session.isNullOrEmpty()) Intent(this, OnboardingActivity::class.java).also { it } else Intent(this, HomeActivity::class.java).also { it }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(nextActivity)
            finish()
        }, 5000)
    }
}
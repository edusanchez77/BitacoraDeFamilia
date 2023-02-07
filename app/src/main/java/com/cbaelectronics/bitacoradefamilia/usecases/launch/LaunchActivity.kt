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
import com.cbaelectronics.bitacoradefamilia.usecases.home.HomeActivity
import com.cbaelectronics.bitacoradefamilia.usecases.login.LoginActivity
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
        var nextActivity = Intent(this, LoginActivity::class.java).also { it }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(nextActivity)
            finish()
        }, 5000)
    }
}
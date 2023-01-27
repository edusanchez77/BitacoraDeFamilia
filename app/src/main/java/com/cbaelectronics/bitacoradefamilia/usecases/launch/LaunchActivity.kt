package com.cbaelectronics.bitacoradefamilia.usecases.launch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityLaunchBinding
import com.cbaelectronics.bitacoradefamilia.usecases.home.HomeActivity

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
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun data() {
        var nextActivity = Intent(this, HomeActivity::class.java).also { it }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(nextActivity)
            finish()
        }, 5000)
    }
}
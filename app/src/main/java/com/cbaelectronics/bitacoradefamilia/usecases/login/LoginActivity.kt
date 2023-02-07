/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/6/23, 3:41 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityLoginBinding
import com.cbaelectronics.bitacoradefamilia.usecases.home.HomeRouter
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    // Properties

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private var token: String? = null
    private lateinit var gso: GoogleSignInOptions
    private lateinit var auth: FirebaseAuth

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        // Setup
        localize()
        setup()
        footer()
    }

    private fun localize() {
        binding.textViewLoginTitle.text = getString(viewModel.title)
        binding.textViewLoginBody.text = getString(viewModel.body)
        binding.buttonLogin.text = getString(viewModel.button)
    }

    private fun setup() {
        // UI
        supportActionBar?.hide()
        binding.textViewLoginTitle.font(
            FontSize.SUBTITLE,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.light)
        )
        binding.textViewLoginBody.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.light)
        )
    }

    private fun footer() {
        binding.buttonLogin.setOnClickListener {
            HomeRouter().launch(this)
        }
    }
}
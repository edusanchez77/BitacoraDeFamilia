/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/6/23, 3:41 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.login

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityLoginBinding
import com.cbaelectronics.bitacoradefamilia.usecases.home.HomeRouter
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    // Properties

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private var token: String? = null
    private lateinit var gso: GoogleSignInOptions
    private lateinit var auth: FirebaseAuth

    private val responseActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            response(activityResult)
        }

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        // Setup
        getToken()
        instanceGoogle()
        instanceFirebaseAuth()
        localize()
        setup()
        footer()
    }

    // Private
    private fun getToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                val message = getString(viewModel.errorToken)
                Log.w("Message", message, task.exception)
                return@OnCompleteListener
            }
            token = task.result.toString()
        })
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
            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            googleSignInClient.signOut()
            signIn(googleSignInClient)
        }
    }

    private fun instanceFirebaseAuth() {
        auth = Firebase.auth
    }

    private fun instanceGoogle() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

    }

    private fun signIn(googleSignInClient: GoogleSignInClient) {
        val signInIntent = googleSignInClient.signInIntent
        responseActivity.launch(signInIntent)
    }

    private fun response(activityResult: ActivityResult) {

        val mProgress = Dialog(this)

        when (activityResult.resultCode) {

            RESULT_OK -> {

                mProgress.requestWindowFeature(Window.FEATURE_NO_TITLE)
                mProgress.setContentView(R.layout.custom_loading)
                mProgress.setCanceledOnTouchOutside(false)
                mProgress.show()

                val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)

                try {

                    val account = task.getResult(ApiException::class.java)

                    if (account != null) {

                        val mCredential = GoogleAuthProvider.getCredential(account.idToken, null)
                        auth.signInWithCredential(mCredential)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    mProgress.dismiss()
                                    loadUser()
                                }
                            }

                    } else {
                        mProgress.dismiss()
                        val mMessage = getString(viewModel.error)
                        Toast.makeText(this, mMessage, Toast.LENGTH_SHORT).show()
                    }

                } catch (e: ApiException) {
                    val error = getString(viewModel.error)
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                    println("ERRORRR: ${e.message}")
                }
            }
            else -> {
                Log.d("HOME: ", "Else")
            }

        }

    }

    private fun loadUser(){
        viewModel.save(auth.currentUser?.email!!, auth.currentUser?.displayName!!)
        showHome()
    }

    private fun showHome() {
        HomeRouter().launch(this)
    }


}
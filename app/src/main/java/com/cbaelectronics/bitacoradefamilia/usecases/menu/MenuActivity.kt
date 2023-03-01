/**
 *  Created by CbaElectronics by Eduardo Sanchez on 27/1/23 14:16.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.menu

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityMenuBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Children
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.DatabaseField
import com.cbaelectronics.bitacoradefamilia.usecases.about.AboutRouter
import com.cbaelectronics.bitacoradefamilia.usecases.notebook.NotebookRouter
import com.cbaelectronics.bitacoradefamilia.usecases.onboarding.OnboardingRouter
import com.cbaelectronics.bitacoradefamilia.usecases.pregnant.PregnantRouter
import com.cbaelectronics.bitacoradefamilia.usecases.settings.SettingsRouter
import com.cbaelectronics.bitacoradefamilia.usecases.share.ShareRouter
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.addClose
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class MenuActivity : AppCompatActivity() {

    // Properties

    private lateinit var binding: ActivityMenuBinding
    private lateinit var viewModel: MenuViewModel
    private lateinit var childrenJSON: String
    private lateinit var children: Children

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[MenuViewModel::class.java]

        // Setup
        data()
        localize()
        setup()
    }

    private fun data(){
        val bundle = intent.extras
        childrenJSON = bundle?.getString(DatabaseField.CHILDREN.key).toString()
        children = Children.fromJson(childrenJSON)!!
        viewModel.childrenInstance(children)
    }

    private fun localize() {
        binding.textViewName.text = children.name
        binding.textViewMenuDateOfBirth.text = children.date
        binding.textViewMenuHourOfBirth.text = children.hour
        binding.textViewMenuWeigthOfBirth.text = children.weight.toString()
        binding.textViewMenuHeigthOfBirth.text = children.height.toString()

        binding.textViewPregnancyDiary.text = getString(viewModel.pregnancyDiary)
        binding.textViewPediatricNotebook.text = getString(viewModel.pediatricNotebook)
    }

    private fun setup() {
        addClose(this)

        // UI
        binding.textViewName.font(FontSize.TITLE, FontType.GALADA, ContextCompat.getColor(this, R.color.text))
        binding.textViewMenuDateOfBirth.font(FontSize.CAPTION, FontType.REGULAR, ContextCompat.getColor(this, R.color.text))
        binding.textViewMenuHourOfBirth.font(FontSize.CAPTION, FontType.REGULAR, ContextCompat.getColor(this, R.color.text))
        binding.textViewMenuWeigthOfBirth.font(FontSize.CAPTION, FontType.REGULAR, ContextCompat.getColor(this, R.color.text))
        binding.textViewMenuHeigthOfBirth.font(FontSize.CAPTION, FontType.REGULAR, ContextCompat.getColor(this, R.color.text))
        binding.textViewPregnancyDiary.font(FontSize.BODY, FontType.REGULAR, ContextCompat.getColor(this, R.color.light))
        binding.textViewPediatricNotebook.font(FontSize.BODY, FontType.REGULAR, ContextCompat.getColor(this, R.color.light))

        buttons()
    }

    private fun buttons() {

        binding.cardViewPregnant.setOnClickListener {
            PregnantRouter().launch(this, children)
        }

        binding.cardViewNotebook.setOnClickListener {
            NotebookRouter().launch(this, children)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                ShareRouter().launch(this)
            }
            R.id.action_onboard -> {
                OnboardingRouter().launch(this)
            }
            R.id.action_about -> {
                AboutRouter().launch(this)
            }
            R.id.action_settings -> {
                SettingsRouter().launch(this)
            }
            R.id.action_close -> {
                loadAlertDialog()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_back_in_up, R.anim.slide_back_out_up)
    }

    // Private

    private fun loadAlertDialog() {
        val mDialog = Dialog(binding.root.context)
        val mWindows = mDialog.window!!

        mWindows.attributes.windowAnimations = R.style.DialogAnimation
        mDialog.setContentView(R.layout.custom_dialog_opciones)
        mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog.setCancelable(false)
        val mText = mDialog.findViewById<TextView>(R.id.txtDialog)
        val mBtnOK = mDialog.findViewById<Button>(R.id.btnDialogAcept)
        val mBtnCancel = mDialog.findViewById<Button>(R.id.btnDialogCancel)
        val lottieDialog = mDialog.findViewById<LottieAnimationView>(R.id.lottieDialog)

        mText.text = getString(viewModel.alertLogout)
        lottieDialog.setAnimation(R.raw.logout)


        lottieDialog.loop(true)
        lottieDialog.playAnimation()

        mBtnOK.text = getString(viewModel.alertButtonOk)
        mBtnCancel.text = getString(viewModel.alertButtonCancel)

        mDialog.show()

        mBtnOK.setOnClickListener {
            mDialog.cancel()
            viewModel.close(this)
        }
        mBtnCancel.setOnClickListener {
            mDialog.cancel()

        }
    }

}
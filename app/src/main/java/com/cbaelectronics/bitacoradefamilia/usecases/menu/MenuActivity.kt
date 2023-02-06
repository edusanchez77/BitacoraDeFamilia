/**
 *  Created by CbaElectronics by Eduardo Sanchez on 27/1/23 14:16.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityMenuBinding
import com.cbaelectronics.bitacoradefamilia.usecases.notebook.NotebookActivity
import com.cbaelectronics.bitacoradefamilia.usecases.pregnant.PregnantRouter
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.addClose
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var viewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[MenuViewModel::class.java]

        // Setup
        localize()
        setup()
    }

    private fun localize() {
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
            PregnantRouter().launch(this)
        }

        binding.cardViewNotebook.setOnClickListener {
            startActivity(Intent(this, NotebookActivity::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_back_in_up, R.anim.slide_back_out_up)
    }

}
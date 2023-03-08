/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/7/23, 4:02 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.addChildren

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityAddChildrenBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Children
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil.showAlert
import com.cbaelectronics.bitacoradefamilia.util.extension.addClose
import com.cbaelectronics.bitacoradefamilia.util.extension.enable
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import com.cbaelectronics.bitacoradefamilia.util.extension.hideSoftInput

class AddChildrenActivity : AppCompatActivity() {

    // Properties

    private lateinit var binding: ActivityAddChildrenBinding
    private lateinit var viewModel: AddChildrenViewModel
    private var nameEditText: String? = null
    private var genreEditText: String? = null

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddChildrenBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[AddChildrenViewModel::class.java]

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
        binding.textViewAddChildrenTitle.text = getString(viewModel.title)
        binding.textFieldAddChildrenName.hint = getString(viewModel.editTextName)
        binding.textFieldAddChildrenGenre.hint = getString(viewModel.editTextGenre)
        binding.textFieldAddChildrenDate.hint = getString(viewModel.editTextDate)
        binding.textFieldAddChildrenWeight.hint = getString(viewModel.editTextWeight)
        binding.buttonSaveChildren.text = getString(viewModel.save)
        binding.buttonCancelChildren.text = getString(viewModel.cancel)
    }

    private fun setup() {

        addClose(this)

        // UI
        binding.textViewAddChildrenTitle.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )

        binding.editTextAddChildrenGenre.inputType = InputType.TYPE_NULL

        // Genre

        val arrayGenre = resources.getStringArray(R.array.genre)
        val adapterGenre = ArrayAdapter(
            this,
            R.layout.dropdown_menu_popup_item,
            arrayGenre
        )
        binding.editTextAddChildrenGenre.setAdapter(adapterGenre)

        setupInfo()
    }

    private fun footer() {

        // Button Save

        disableSave()
        binding.buttonSaveChildren.setOnClickListener {
            validForm()
        }

        // Button Cancel

        binding.buttonCancelChildren.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupInfo(){

        // Name

        binding.editTextAddChildrenName.addTextChangedListener ( object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nameEditText = binding.editTextAddChildrenName.text.toString()
                checkEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        } )

        // Genre

        binding.editTextAddChildrenGenre.setOnItemClickListener { adapterView, view, i, l ->
            hideSoftInput()
            genreEditText = binding.editTextAddChildrenGenre.text.toString()
            checkEnable()
        }

    }

    private fun checkEnable(){
        if (!nameEditText.isNullOrEmpty() && !genreEditText.isNullOrEmpty()){
            enableSave()
        }
    }

    private fun validForm() {
        val name = binding.editTextAddChildrenName.text
        val genre = binding.editTextAddChildrenGenre.text

        if (name.isNullOrBlank() || genre.isNullOrBlank()) {
            showAlert(this, getString(viewModel.errorIncomplete))
        } else {
            val children = Children(
                name = name.toString(),
                genre = genre.toString(),
                registeredBy = viewModel.user
            )

            saveDatabase(children)
        }
    }

    private fun saveDatabase(children: Children) {
        viewModel.save(children)

        clearEditText()
        hideSoftInput()
        showAlert(this, getString(viewModel.ok))
    }

    private fun clearEditText() {
        binding.editTextAddChildrenName.text = null
        binding.editTextAddChildrenGenre.text = null
        binding.editTextAddChildrenDate.text = null
        binding.editTextAddChildrenHeight.text = null
        binding.editTextAddChildrenWeight.text = null
        binding.editTextAddChildrenHeight.text = null
    }

    private fun disableSave() {
        binding.buttonSaveChildren.enable(false)
        binding.buttonCancelChildren.text = getString(viewModel.back)
    }

    private fun enableSave() {
        binding.buttonSaveChildren.enable(true)
        binding.buttonCancelChildren.text = getString(viewModel.cancel)
    }
}
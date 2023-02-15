/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/3/23, 10:44 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityAddNotesBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Notes
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil
import com.cbaelectronics.bitacoradefamilia.util.UIUtil.showAlert
import com.cbaelectronics.bitacoradefamilia.util.extension.addClose
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import com.cbaelectronics.bitacoradefamilia.util.extension.hideSoftInput
import com.cbaelectronics.bitacoradefamilia.util.extension.toDate
import java.text.SimpleDateFormat

class AddNotesActivity : AppCompatActivity() {

    // Properties

    private lateinit var binding: ActivityAddNotesBinding
    private lateinit var viewModel: AddNotesViewModel

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[AddNotesViewModel::class.java]

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
        binding.textViewAddNoteTitle.text = getString(viewModel.title)
        binding.buttonSaveNote.text = getString(viewModel.save)
        binding.buttonCancelNote.text = getString(viewModel.cancel)
        binding.textFieldAddNote.hint = getString(viewModel.editTextNote)
    }

    private fun setup() {
        addClose(this)

        // UI
        binding.textViewAddNoteTitle.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )
    }

    private fun footer() {
        binding.buttonSaveNote.setOnClickListener {
            validForm()
        }

        binding.buttonCancelNote.setOnClickListener {
            onBackPressed()
        }
    }

    private fun validForm() {
        val date = binding.editTextAddNoteDate.text
        val notes = binding.editTextAddNote.text

        if (date.isNullOrEmpty() || notes.isNullOrEmpty()) {
            showAlert(this, getString(viewModel.errorIncomplete))
        } else {

            val sdf = SimpleDateFormat(Constants.DATE)
            val dateFormat = sdf.parse(date.toString())

            val note = Notes(viewModel.children?.id, dateFormat, notes.toString(), viewModel.user)
            
            saveDatabase(note)
        }
    }

    private fun saveDatabase(note: Notes) {
        viewModel.save(note)

        clearEditText()
        hideSoftInput()
        UIUtil.showAlert(this, getString(viewModel.ok))
        onBackPressed()
    }

    private fun clearEditText() {
        binding.editTextAddNoteDate.text = null
        binding.editTextAddNote.text = null
    }
}
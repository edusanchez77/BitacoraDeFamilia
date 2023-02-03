/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/1/23, 7:53 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.notes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentNotebookNotesBinding
import com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.notes.AddNotesRouter
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import com.google.android.material.snackbar.Snackbar

class NotebookNotesFragment : Fragment() {

    private lateinit var _binding: FragmentNotebookNotesBinding
    private val binding get() = _binding
    private lateinit var viewModel: NotebookNotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Content
        _binding = FragmentNotebookNotesBinding.inflate(inflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(this)[NotebookNotesViewModel::class.java]

        // Localize
        localize()

        // Setup
        setup()
        footer()

        return binding.root
    }

    private fun localize() {
        binding.textViewNotesTitle.text = getString(viewModel.title)
        binding.buttonNotebookNotesAdd.text = getString(viewModel.button)
    }

    private fun setup() {
        // UI
        binding.textViewNotesTitle.font(FontSize.BODY, FontType.REGULAR, ContextCompat.getColor(binding.root.context, R.color.text))
    }

    private fun footer() {
        binding.buttonNotebookNotesAdd.setOnClickListener {
            AddNotesRouter().launch(binding.root.context)
        }
    }


}
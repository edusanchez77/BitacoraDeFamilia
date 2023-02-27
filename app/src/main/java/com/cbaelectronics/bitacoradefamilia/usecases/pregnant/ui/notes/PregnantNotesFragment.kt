/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/1/23, 7:38 AM.
 *  www.cbaelectronics.com.ar
 */

/**
 *  Created by CbaElectronics by Eduardo Sanchez on 1/31/23, 3:59 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentPregnantNotesBinding
import com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.notes.AddNotesRouter
import com.cbaelectronics.bitacoradefamilia.util.Constants.TYPE_PREGNANT
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil.showAlert
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class PregnantNotesFragment : Fragment() {

    // Properties

    private lateinit var _binding: FragmentPregnantNotesBinding
    private val binding get() = _binding
    private lateinit var viewModel: PregnantNotesViewModel

    // Initialization

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Content
        _binding = FragmentPregnantNotesBinding.inflate(inflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(this).get(PregnantNotesViewModel::class.java)

        // Setup
        localize()
        setup()
        footer()

        return binding.root
    }

    // Private

    private fun localize() {
        binding.textViewControlTitle.text = getString(viewModel.title)
        binding.buttonPregnantNotesAdd.text = getString(viewModel.button)
    }

    private fun setup() {
        // UI
        binding.textViewControlTitle.font(FontSize.BODY, FontType.REGULAR, ContextCompat.getColor(binding.root.context, R.color.text))
    }

    private fun footer() {
        binding.buttonPregnantNotesAdd.setOnClickListener {
            AddNotesRouter().launch(binding.root.context, TYPE_PREGNANT)
        }
    }


}
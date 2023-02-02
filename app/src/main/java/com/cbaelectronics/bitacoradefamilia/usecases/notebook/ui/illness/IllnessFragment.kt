/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/1/23, 7:51 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.illness

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentIllnessBinding
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentMedicalMeetingBinding
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import com.google.android.material.snackbar.Snackbar

class IllnessFragment : Fragment() {

    private lateinit var _binding: FragmentIllnessBinding
    private val binding get() = _binding
    private lateinit var viewModel: IllnessViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Content
        _binding = FragmentIllnessBinding.inflate(inflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(this)[IllnessViewModel::class.java]

        // Localize
        localize()

        // Setup
        setup()
        footer()

        return binding.root
    }

    private fun localize() {
        binding.textViewIllnessTitle.text = getString(viewModel.title)
        binding.buttonIllnessAdd.text = getString(viewModel.button)
    }

    private fun setup() {
        // UI
        binding.textViewIllnessTitle.font(FontSize.BODY, FontType.REGULAR, ContextCompat.getColor(binding.root.context, R.color.text))
    }

    private fun footer() {
        binding.buttonIllnessAdd.setOnClickListener {
            Snackbar.make(it, "Agregar enfermedad", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.buttonIllnessAdd)
                .setAction("Action", null).show()
        }
    }


}
/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/1/23, 7:41 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.pediatric_control

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.get
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentIllnessBinding
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentPediatricControlBinding
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import com.google.android.material.snackbar.Snackbar

class PediatricControlFragment : Fragment() {

    private lateinit var _binding: FragmentPediatricControlBinding
    private val binding get() = _binding
    private lateinit var viewModel: PediatricControlViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Content
        _binding = FragmentPediatricControlBinding.inflate(inflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(this)[PediatricControlViewModel::class.java]

        // Localize
        localize()

        // Setup
        setup()
        footer()

        return binding.root
    }

    private fun localize() {
        binding.textViewControlTitle.text = getString(viewModel.title)
        binding.buttonControlAdd.text = getString(viewModel.button)
    }

    private fun setup() {
        // UI
        binding.textViewControlTitle.font(FontSize.BODY, FontType.REGULAR, ContextCompat.getColor(binding.root.context, R.color.text))
    }

    private fun footer() {
        binding.buttonControlAdd.setOnClickListener {
            Snackbar.make(it, "Agregar control", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.buttonControlAdd)
                .setAction("Action", null).show()
        }
    }

}
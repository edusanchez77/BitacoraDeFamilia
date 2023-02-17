/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/17/23, 9:23 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.info.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentNamesBinding
import com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.add.names.AddNamesRouter
import com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.info.InfoViewModel
import com.cbaelectronics.bitacoradefamilia.util.UIUtil.showAlert

class NamesFragment : Fragment() {

    // Properties

    private lateinit var _binding: FragmentNamesBinding
    private val binding get() = _binding
    private lateinit var viewModel: InfoViewModel

// Initialization

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Content
        _binding = FragmentNamesBinding.inflate(layoutInflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(this)[InfoViewModel::class.java]

        // Setup
        localize()
        setup()
        footer()

        return binding.root
    }

    private fun localize() {
        binding.buttonPregnantTabNamesAdd.text = getString(viewModel.buttonAddNames)
    }

    private fun setup() {
        //TODO("Not yet implemented")
    }

    private fun footer() {
        binding.buttonPregnantTabNamesAdd.setOnClickListener {
            AddNamesRouter().launch(binding.root.context)
        }
    }
}
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
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentInformationBinding
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentWeightBinding
import com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.info.InfoViewModel
import com.cbaelectronics.bitacoradefamilia.util.UIUtil.showAlert

class WeightFragment : Fragment() {

    // Properties

    private lateinit var _binding: FragmentWeightBinding
    private val binding get() = _binding
    private lateinit var viewModel: InfoViewModel

    // Initialization

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Content
        _binding = FragmentWeightBinding.inflate(inflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(this)[InfoViewModel::class.java]

        // Setup
        localize()
        setup()
        footer()


        return binding.root
    }

    private fun localize() {
        binding.buttonPregnantTabWeightAdd.text = getString(viewModel.buttonAddWeight)
    }

    private fun setup() {
        //TODO("Not yet implemented")
    }

    private fun footer() {
        binding.buttonPregnantTabWeightAdd.setOnClickListener {
            showAlert(binding.root.context, "Add Weight")
        }
    }


}
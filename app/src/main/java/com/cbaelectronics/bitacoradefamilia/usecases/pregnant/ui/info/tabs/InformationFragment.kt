/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/17/23, 9:21 AM.
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
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentInfoBinding
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentInformationBinding
import com.cbaelectronics.bitacoradefamilia.usecases.common.tabs.tabInfoAdapter
import com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.add.info.AddInfoRouter
import com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.info.InfoViewModel
import com.cbaelectronics.bitacoradefamilia.util.UIUtil.showAlert

class InformationFragment : Fragment() {

    // Properties

    private lateinit var _binding: FragmentInformationBinding
    private val binding get() = _binding
    private lateinit var viewModel: InfoViewModel

    // Initialization

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Content
        _binding = FragmentInformationBinding.inflate(inflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(this)[InfoViewModel::class.java]

        // Setup
        localize()
        setup()
        footer()

        return binding.root
    }

    private fun localize() {
        binding.buttonPregnantTabInformationAdd.text = getString(viewModel.buttonAddInfo)
    }

    private fun setup() {
        //TODO("Not yet implemented")
    }

    private fun footer() {
        binding.buttonPregnantTabInformationAdd.setOnClickListener {
            AddInfoRouter().launch(binding.root.context)
        }
    }


}
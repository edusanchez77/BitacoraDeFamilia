/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/1/23, 7:38 AM.
 *  www.cbaelectronics.com.ar
 */

/**
 *  Created by CbaElectronics by Eduardo Sanchez on 30/1/23 16:18.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.growth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentGrowthBinding
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class GrowthFragment : Fragment() {

    private lateinit var _binding: FragmentGrowthBinding
    private val binding get() = _binding!!
    private lateinit var viewModel: GrowthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Content
        _binding = FragmentGrowthBinding.inflate(inflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(this).get(GrowthViewModel::class.java)

        // Localize
        localize()

        // Setup
        setup()



        return binding.root
    }

    private fun localize() {
        binding.textViewGrowthTitle.text = getString(viewModel.title)
        binding.textViewGrowthTableHeaderDate.text = getString(viewModel.headerDate)
        binding.textViewGrowthTableHeaderWeight.text = getString(viewModel.headerWeight)
        binding.textViewGrowthTableHeaderHeight.text = getString(viewModel.headerHeight)
        binding.textViewGrowthTableHeaderPC.text = getString(viewModel.headerPC)
    }

    private fun setup() {
        // UI
        binding.textViewGrowthTitle.font(FontSize.BODY, FontType.REGULAR, ContextCompat.getColor(binding.root.context, R.color.text))
    }

}
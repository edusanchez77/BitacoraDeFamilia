/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/1/23, 7:52 AM.
 *  www.cbaelectronics.com.ar
 */

/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/1/23, 7:38 AM.
 *  www.cbaelectronics.com.ar
 */

/**
 *  Created by CbaElectronics by Eduardo Sanchez on 30/1/23 16:18.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentInfoBinding
import com.cbaelectronics.bitacoradefamilia.usecases.common.tabs.tabInfoAdapter
import com.cbaelectronics.bitacoradefamilia.util.extension.uppercaseFirst
import com.google.android.material.tabs.TabLayoutMediator

class InfoFragment : Fragment() {

    // Properties

    private lateinit var _binding: FragmentInfoBinding
    private val binding get() = _binding!!
    private lateinit var viewModel: InfoViewModel
    private lateinit var adapter: tabInfoAdapter

    // Initialization

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Content
        _binding = FragmentInfoBinding.inflate(inflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(this)[InfoViewModel::class.java]

        // Adapter
        adapter = tabInfoAdapter(this)

        // Setup
        localize()
        setup()

        return binding.root
    }

    private fun localize() {
        //TODO("Not yet implemented")
    }

    private fun setup() {
        binding.viewPagerInfo.adapter = adapter

        binding.tabLayoutInfoOptions.setTabTextColors(ContextCompat.getColor(binding.root.context, R.color.background), ContextCompat.getColor(binding.root.context, R.color.light))
        binding.tabLayoutInfoOptions.setSelectedTabIndicatorColor(ContextCompat.getColor(binding.root.context, R.color.secondary))

        val tabLayoutMediator = TabLayoutMediator(binding.tabLayoutInfoOptions, binding.viewPagerInfo,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when(position){
                    0 -> {
                        tab.text = getString(viewModel.tabInfo).uppercase()
                    }
                    1 -> {
                        tab.text = getString(viewModel.tabWeight).uppercase()
                    }
                    2 -> tab.text = getString(viewModel.tabNames).uppercase()
                }
            }
        )
        tabLayoutMediator.attach()

    }


}
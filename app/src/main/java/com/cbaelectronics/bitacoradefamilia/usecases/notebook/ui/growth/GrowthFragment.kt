/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/1/23, 7:38 AM.
 *  www.cbaelectronics.com.ar
 */

/**
 *  Created by CbaElectronics by Eduardo Sanchez on 30/1/23 16:18.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.growth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentGrowthBinding
import com.cbaelectronics.bitacoradefamilia.usecases.common.rows.GrowthRecyclerViewAdapter
import com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.growth.AddGrowthRouter
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class GrowthFragment : Fragment() {

    // Properties

    private lateinit var _binding: FragmentGrowthBinding
    private val binding get() = _binding
    private lateinit var viewModel: GrowthViewModel
    private lateinit var adapter: GrowthRecyclerViewAdapter

    // Initialization

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Content
        _binding = FragmentGrowthBinding.inflate(inflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(this).get(GrowthViewModel::class.java)

        // Adapter
        adapter = GrowthRecyclerViewAdapter(binding.root.context)

        // Setup
        localize()
        setup()
        footer()



        return binding.root
    }

    // Private

    private fun localize() {
        binding.textViewGrowthTitle.text = getString(viewModel.title)
        binding.textViewGrowthTableHeaderDate.text = getString(viewModel.headerDate)
        binding.textViewGrowthTableHeaderWeight.text = getString(viewModel.headerWeight)
        binding.textViewGrowthTableHeaderHeight.text = getString(viewModel.headerHeight)
        binding.textViewGrowthTableHeaderPC.text = getString(viewModel.headerPC)
        binding.buttonGrowthAdd.text = getString(viewModel.button)
    }

    private fun setup() {
        // UI
        binding.textViewGrowthTitle.font(FontSize.BODY, FontType.REGULAR, ContextCompat.getColor(binding.root.context, R.color.text))
        binding.textViewGrowthTableHeaderDate.font(FontSize.BUTTON, FontType.REGULAR, ContextCompat.getColor(binding.root.context, R.color.text))
        binding.textViewGrowthTableHeaderWeight.font(FontSize.BUTTON, FontType.REGULAR, ContextCompat.getColor(binding.root.context, R.color.text))
        binding.textViewGrowthTableHeaderHeight.font(FontSize.BUTTON, FontType.REGULAR, ContextCompat.getColor(binding.root.context, R.color.text))
        binding.textViewGrowthTableHeaderPC.font(FontSize.BUTTON, FontType.REGULAR, ContextCompat.getColor(binding.root.context, R.color.text))

        // Adapter
        binding.recyclerViewGrowth.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerViewGrowth.adapter = adapter
        binding.recyclerViewGrowth.addItemDecoration(
            DividerItemDecoration(
                binding.root.context,
                LinearLayoutManager.VERTICAL
            )
        )

        observeData()
    }

    private fun footer() {
        binding.buttonGrowthAdd.setOnClickListener {
            AddGrowthRouter().launch(binding.root.context)
        }
    }

    private fun observeData() {
        viewModel.load().observe(viewLifecycleOwner, Observer {
            adapter.setDataList(it)
            adapter.notifyDataSetChanged()
        })
    }

}
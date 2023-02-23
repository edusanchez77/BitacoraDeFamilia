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
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentInformationBinding
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentWeightBinding
import com.cbaelectronics.bitacoradefamilia.usecases.common.rows.WeightRecyclerViewAdapter
import com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.add.weight.AddWeightRouter
import com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.info.InfoViewModel
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil.showAlert
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class WeightFragment : Fragment() {

    // Properties

    private lateinit var _binding: FragmentWeightBinding
    private val binding get() = _binding
    private lateinit var viewModel: InfoViewModel
    private lateinit var adapter: WeightRecyclerViewAdapter

    // Initialization

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Content
        _binding = FragmentWeightBinding.inflate(inflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(this)[InfoViewModel::class.java]

        // Adapter
        adapter = WeightRecyclerViewAdapter(binding.root.context)

        // Setup
        localize()
        setup()
        footer()


        return binding.root
    }

    private fun localize() {
        binding.buttonPregnantTabWeightAdd.text = getString(viewModel.buttonAddWeight)
        binding.textViewWeightTableHeaderWeek.text = getString(viewModel.headerWeek)
        binding.textViewWeightTableHeaderWeight.text = getString(viewModel.headerWeight)
    }

    private fun setup() {
        // UI
        binding.textViewWeightTableHeaderWeek.font(FontSize.BUTTON, FontType.REGULAR, ContextCompat.getColor(binding.root.context, R.color.text))
        binding.textViewWeightTableHeaderWeight.font(FontSize.BUTTON, FontType.REGULAR, ContextCompat.getColor(binding.root.context, R.color.text))

        binding.recyclerViewPregnantTabWeight.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerViewPregnantTabWeight.adapter = adapter
        binding.recyclerViewPregnantTabWeight.addItemDecoration(
            DividerItemDecoration(
                binding.root.context,
                LinearLayoutManager.VERTICAL
            )
        )

        observeData()
    }

    private fun footer() {
        binding.buttonPregnantTabWeightAdd.setOnClickListener {
            AddWeightRouter().launch(binding.root.context)
        }
    }

    private fun observeData(){
        viewModel.load().observe(viewLifecycleOwner, Observer {
            adapter.setDataList(it)
            adapter.notifyDataSetChanged()
        })
    }


}
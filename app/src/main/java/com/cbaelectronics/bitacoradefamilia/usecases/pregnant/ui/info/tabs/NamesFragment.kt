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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentNamesBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Genre
import com.cbaelectronics.bitacoradefamilia.usecases.common.rows.NamesRecyclerViewAdapter
import com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.add.names.AddNamesRouter
import com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.info.InfoViewModel
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil.showAlert
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class NamesFragment : Fragment() {

    // Properties

    private lateinit var _binding: FragmentNamesBinding
    private val binding get() = _binding
    private lateinit var viewModel: InfoViewModel
    private lateinit var adapterBoy: NamesRecyclerViewAdapter
    private lateinit var adapterWoman: NamesRecyclerViewAdapter

// Initialization

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Content
        _binding = FragmentNamesBinding.inflate(layoutInflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(this)[InfoViewModel::class.java]

        // Adapter
        adapterBoy = NamesRecyclerViewAdapter(binding.root.context)
        adapterWoman = NamesRecyclerViewAdapter(binding.root.context)

        // Setup
        localize()
        setup()
        footer()

        return binding.root
    }

    private fun localize() {
        binding.textViewNamesTitleBoy.text = getString(viewModel.boy)
        binding.textViewNamesTitleWoman.text = getString(viewModel.woman)
        binding.buttonPregnantTabNamesAdd.text = getString(viewModel.buttonAddNames)
    }

    private fun setup() {
        // UI
        binding.textViewNamesTitleBoy.font(FontSize.HEAD, FontType.BOLD, binding.root.context.getColor(R.color.light))
        binding.textViewNamesTitleWoman.font(FontSize.HEAD, FontType.BOLD, binding.root.context.getColor(R.color.light))

        binding.recyclerViewPregnantTabNamesBoy.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerViewPregnantTabNamesBoy.adapter = adapterBoy

        binding.recyclerViewPregnantTabNamesWoman.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerViewPregnantTabNamesWoman.adapter = adapterWoman

        observeData()
    }

    private fun footer() {
        binding.buttonPregnantTabNamesAdd.setOnClickListener {
            AddNamesRouter().launch(binding.root.context)
        }
    }

    private fun observeData(){
        viewModel.load(Genre.BOY.type).observe(viewLifecycleOwner, Observer {
            adapterBoy.setDataList(it)
            adapterBoy.notifyDataSetChanged()
        })

        viewModel.load(Genre.WOMAN.type).observe(viewLifecycleOwner, Observer {
            adapterWoman.setDataList(it)
            adapterWoman.notifyDataSetChanged()
        })
    }
}
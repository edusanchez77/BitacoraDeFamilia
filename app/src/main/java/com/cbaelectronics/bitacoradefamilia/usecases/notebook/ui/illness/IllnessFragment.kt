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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentIllnessBinding
import com.cbaelectronics.bitacoradefamilia.usecases.common.rows.IllnessRecyclerViewAdapter
import com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.illness.AddIllnessRouter
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class IllnessFragment : Fragment() {

    // Properties

    private lateinit var _binding: FragmentIllnessBinding
    private val binding get() = _binding
    private lateinit var viewModel: IllnessViewModel
    private lateinit var adapter: IllnessRecyclerViewAdapter

    // Initialization
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Content
        _binding = FragmentIllnessBinding.inflate(inflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(this)[IllnessViewModel::class.java]

        // Adapter
        adapter = IllnessRecyclerViewAdapter(binding.root.context)

        // Setup
        localize()
        setup()
        footer()

        return binding.root
    }

    // Private

    private fun localize() {
        binding.textViewIllnessTitle.text = getString(viewModel.title)
        binding.buttonIllnessAdd.text = getString(viewModel.button)
    }

    private fun setup() {
        // UI
        binding.textViewIllnessTitle.font(FontSize.BODY, FontType.REGULAR, ContextCompat.getColor(binding.root.context, R.color.text))

        binding.recyclerViewIllness.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerViewIllness.adapter = adapter

        observeData()
    }

    private fun footer() {
        binding.buttonIllnessAdd.setOnClickListener {
            AddIllnessRouter().launch(binding.root.context)
        }
    }

    private fun observeData(){
        viewModel.load().observe(viewLifecycleOwner, Observer {
            adapter.setDataList(it)
            adapter.notifyDataSetChanged()
        })
    }


}
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
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentPediatricControlBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Permission
import com.cbaelectronics.bitacoradefamilia.usecases.common.rows.PediatricControlRecyclerViewAdapter
import com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.pediatric_control.AddPediatricControlRouter
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class PediatricControlFragment : Fragment() {

    // Properties

    private lateinit var _binding: FragmentPediatricControlBinding
    private val binding get() = _binding
    private lateinit var viewModel: PediatricControlViewModel
    private lateinit var adapter: PediatricControlRecyclerViewAdapter

    // Initialization

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Content
        _binding = FragmentPediatricControlBinding.inflate(inflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(this)[PediatricControlViewModel::class.java]

        // Adapter
        adapter = PediatricControlRecyclerViewAdapter(binding.root.context)

        // Setup
        localize()
        setup()
        footer()

        return binding.root
    }

    // Public

    private fun localize() {
        //binding.textViewControlTitle.text = getString(viewModel.title)
        binding.buttonControlAdd.text = getString(viewModel.button)
    }

    private fun setup() {
        // UI
        //binding.textViewControlTitle.font(FontSize.BODY, FontType.REGULAR, ContextCompat.getColor(binding.root.context, R.color.text))

        binding.recyclerViewPediatricControl.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerViewPediatricControl.adapter = adapter

        observeData()

        // Footer

        if (viewModel.permission == Permission.READ.value){
            binding.layoutButtons.visibility = GONE
        }else{
            binding.layoutButtons.visibility = VISIBLE
        }
    }

    private fun footer() {
        binding.buttonControlAdd.setOnClickListener {
            AddPediatricControlRouter().launch(binding.root.context)
        }
    }

    private fun observeData(){
        viewModel.load().observe(viewLifecycleOwner, Observer {
            adapter.setDataList(it)
            adapter.notifyDataSetChanged()
        })
    }

}
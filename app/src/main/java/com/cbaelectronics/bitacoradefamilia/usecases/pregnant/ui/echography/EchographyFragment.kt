/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/1/23, 7:38 AM.
 *  www.cbaelectronics.com.ar
 */

/**
 *  Created by CbaElectronics by Eduardo Sanchez on 1/31/23, 3:59 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.echography

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentEchographyBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Permission
import com.cbaelectronics.bitacoradefamilia.usecases.common.rows.EchographyRecyclerViewAdapter
import com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.add.echography.AddEchographyRouter
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class EchographyFragment : Fragment() {

    private lateinit var _binding: FragmentEchographyBinding
    private val binding get() = _binding
    private lateinit var viewModel: EchographyViewModel
    private lateinit var adapter: EchographyRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Content
        _binding = FragmentEchographyBinding.inflate(inflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(this).get(EchographyViewModel::class.java)

        // Adapter
        adapter = EchographyRecyclerViewAdapter(binding.root.context)

        // Setup
        localize()
        setup()
        footer()

        return binding.root
    }

    // Private
    private fun localize() {
        binding.textViewEchographyTitle.text = getString(viewModel.info)
        binding.buttonEchographyAdd.text = getString(viewModel.button)
    }

    private fun setup() {
        // UI
        binding.textViewEchographyTitle.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )

        binding.recyclerViewEchography.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerViewEchography.adapter = adapter

        observeData()

        // Footer

        if (viewModel.permission == Permission.READ.value){
            binding.layoutButtons.visibility = GONE
        }else{
            binding.layoutButtons.visibility = VISIBLE
        }
    }

    private fun footer() {
        binding.buttonEchographyAdd.setOnClickListener {
            AddEchographyRouter().launch(binding.root.context)
        }
    }

    private fun observeData(){
        viewModel.load().observe(viewLifecycleOwner, Observer {
            adapter.setDataList(it)
            adapter.notifyDataSetChanged()
            if (it.size == 0){
                binding.textViewEchographyTitle.visibility = VISIBLE
            }else{
                binding.textViewEchographyTitle.visibility = GONE
            }
        })
    }

}
/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/1/23, 7:38 AM.
 *  www.cbaelectronics.com.ar
 */

/**
 *  Created by CbaElectronics by Eduardo Sanchez on 1/31/23, 3:59 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentPregnantNotesBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Permission
import com.cbaelectronics.bitacoradefamilia.usecases.common.rows.NotesRecyclerViewAdapter
import com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.notes.AddNotesRouter
import com.cbaelectronics.bitacoradefamilia.util.Constants.TYPE_PREGNANT
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil.showAlert
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class PregnantNotesFragment : Fragment() {

    // Properties

    private lateinit var _binding: FragmentPregnantNotesBinding
    private val binding get() = _binding
    private lateinit var viewModel: PregnantNotesViewModel
    private lateinit var adapter: NotesRecyclerViewAdapter

    // Initialization

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Content
        _binding = FragmentPregnantNotesBinding.inflate(inflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(this).get(PregnantNotesViewModel::class.java)

        // Adapter
        adapter = NotesRecyclerViewAdapter(binding.root.context)

        // Setup
        localize()
        setup()
        footer()

        return binding.root
    }

    // Private

    private fun localize() {
        binding.textViewNotesTitle.text = getString(viewModel.info)
        binding.buttonPregnantNotesAdd.text = getString(viewModel.button)
    }

    private fun setup() {
        // UI
        binding.textViewNotesTitle.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )

        binding.recyclerViewPregnantNotes.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerViewPregnantNotes.adapter = adapter

        observeData()

        // Footer

        if (viewModel.permission == Permission.READ.value){
            binding.layoutButtons.visibility = GONE
        }else{
            binding.layoutButtons.visibility = VISIBLE
        }
    }

    private fun footer() {
        binding.buttonPregnantNotesAdd.setOnClickListener {
            AddNotesRouter().launch(binding.root.context, TYPE_PREGNANT)
        }
    }

    private fun observeData(){
        viewModel.load().observe(viewLifecycleOwner, Observer {
            adapter.setDataList(it)
            adapter.notifyDataSetChanged()
            if (it.size == 0){
                binding.textViewNotesTitle.visibility = VISIBLE
            }else{
                binding.textViewNotesTitle.visibility = GONE
            }
        })
    }

}
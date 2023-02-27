/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/1/23, 7:53 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.notes

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
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentNotebookNotesBinding
import com.cbaelectronics.bitacoradefamilia.usecases.common.rows.NotesRecyclerViewAdapter
import com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.notes.AddNotesRouter
import com.cbaelectronics.bitacoradefamilia.util.Constants.TYPE_NOTEBOOK
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class NotebookNotesFragment : Fragment() {

    // Properties

    private lateinit var _binding: FragmentNotebookNotesBinding
    private val binding get() = _binding
    private lateinit var viewModel: NotebookNotesViewModel
    private lateinit var adapter: NotesRecyclerViewAdapter

    // Initialization

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Content
        _binding = FragmentNotebookNotesBinding.inflate(inflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(this)[NotebookNotesViewModel::class.java]

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
        binding.textViewNotesTitle.text = getString(viewModel.title)
        binding.buttonNotebookNotesAdd.text = getString(viewModel.button)
    }

    private fun setup() {
        // UI
        binding.textViewNotesTitle.font(FontSize.BODY, FontType.REGULAR, ContextCompat.getColor(binding.root.context, R.color.text))

        binding.recyclerViewNotebookNotes.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerViewNotebookNotes.adapter = adapter

        observeData()
    }

    private fun footer() {
        binding.buttonNotebookNotesAdd.setOnClickListener {
            AddNotesRouter().launch(binding.root.context, TYPE_NOTEBOOK)
        }
    }

    private fun observeData(){
        viewModel.load().observe(viewLifecycleOwner, Observer {
            adapter.setDataList(it)
            adapter.notifyDataSetChanged()
        })
    }

}
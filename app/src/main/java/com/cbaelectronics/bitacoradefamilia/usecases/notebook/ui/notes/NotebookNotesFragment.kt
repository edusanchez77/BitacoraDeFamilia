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
import com.cbaelectronics.bitacoradefamilia.R

class NotebookNotesFragment : Fragment() {

    companion object {
        fun newInstance() = NotebookNotesFragment()
    }

    private lateinit var viewModel: NotebookNotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notebook_notes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NotebookNotesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
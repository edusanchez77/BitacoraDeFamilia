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
import android.view.ViewGroup
import com.cbaelectronics.bitacoradefamilia.R

class PediatricControlFragment : Fragment() {

    companion object {
        fun newInstance() = PediatricControlFragment()
    }

    private lateinit var viewModel: PediatricControlViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pediatric_control, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PediatricControlViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
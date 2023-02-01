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
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentIllnessBinding
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentMedicalMeetingBinding

class IllnessFragment : Fragment() {

    private var _binding: FragmentIllnessBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: IllnessViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(IllnessViewModel::class.java)

        _binding = FragmentIllnessBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
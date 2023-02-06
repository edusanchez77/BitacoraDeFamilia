/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/1/23, 7:38 AM.
 *  www.cbaelectronics.com.ar
 */

/**
 *  Created by CbaElectronics by Eduardo Sanchez on 30/1/23 16:18.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.achievements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentAchievementsBinding
import com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.achievements.AddAchievementsRouter
import com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.illness.AddIllnessRouter
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class AchievementsFragment : Fragment() {

    // Properties

    private lateinit var _binding: FragmentAchievementsBinding
    private val binding get() = _binding
    private lateinit var viewModel: AchievementsViewModel

    // Initialization

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Content
        _binding = FragmentAchievementsBinding.inflate(inflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(this)[AchievementsViewModel::class.java]

        // Setup
        localize()
        setup()
        footer()

        return binding.root
    }

    // Private
    private fun localize(){
        binding.textViewAchievementsTitle.text = getString(viewModel.title)
        binding.buttonAchievementsAdd.text = getString(viewModel.button)
    }

    private fun setup(){
        // UI
        binding.textViewAchievementsTitle.font(FontSize.BODY, FontType.REGULAR, ContextCompat.getColor(binding.root.context, R.color.text))
    }

    private fun footer(){
        binding.buttonAchievementsAdd.setOnClickListener {
            AddAchievementsRouter().launch(binding.root.context)
        }
    }
}
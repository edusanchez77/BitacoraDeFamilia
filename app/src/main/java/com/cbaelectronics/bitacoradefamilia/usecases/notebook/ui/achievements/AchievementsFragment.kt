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
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentAchievementsBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Permission
import com.cbaelectronics.bitacoradefamilia.usecases.common.rows.AchievementRecyclerViewAdapter
import com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.achievements.AddAchievementsRouter
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class AchievementsFragment : Fragment() {

    // Properties

    private lateinit var _binding: FragmentAchievementsBinding
    private val binding get() = _binding
    private lateinit var viewModel: AchievementsViewModel
    private lateinit var adapter: AchievementRecyclerViewAdapter

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

        // Adapter
        adapter = AchievementRecyclerViewAdapter(binding.root.context)

        // Setup
        localize()
        setup()
        footer()

        return binding.root
    }

    // Private
    private fun localize(){
        binding.textViewAchievementTitle.text = getString(viewModel.info)
        binding.buttonAchievementsAdd.text = getString(viewModel.button)
    }

    private fun setup(){
        // UI
        binding.textViewAchievementTitle.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )

        binding.recyclerViewAchievements.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerViewAchievements.adapter = adapter

        observeData()

        // Footer

        if (viewModel.permission == Permission.READ.value){
            binding.layoutButtons.visibility = View.GONE
        }else{
            binding.layoutButtons.visibility = View.VISIBLE
        }
    }

    private fun footer(){
        binding.buttonAchievementsAdd.setOnClickListener {
            AddAchievementsRouter().launch(binding.root.context)
        }
    }

    private fun observeData(){
        viewModel.load().observe(viewLifecycleOwner, Observer {
            adapter.setDataList(it)
            adapter.notifyDataSetChanged()
            if (it.size == 0){
                binding.textViewAchievementTitle.visibility = VISIBLE
            }else{
                binding.textViewAchievementTitle.visibility = GONE
            }
        })
    }
}
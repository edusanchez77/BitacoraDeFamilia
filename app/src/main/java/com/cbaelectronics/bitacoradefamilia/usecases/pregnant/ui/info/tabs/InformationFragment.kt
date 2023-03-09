/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/17/23, 9:21 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.info.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentInfoBinding
import com.cbaelectronics.bitacoradefamilia.databinding.FragmentInformationBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.Permission
import com.cbaelectronics.bitacoradefamilia.usecases.common.tabs.tabInfoAdapter
import com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.add.info.AddInfoRouter
import com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.info.InfoViewModel
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil.showAlert
import com.cbaelectronics.bitacoradefamilia.util.extension.font
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class InformationFragment : Fragment() {

    // Properties

    private lateinit var _binding: FragmentInformationBinding
    private val binding get() = _binding
    private lateinit var viewModel: InfoViewModel

    // Initialization

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Content
        _binding = FragmentInformationBinding.inflate(inflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(this)[InfoViewModel::class.java]

        // Setup
        localize()
        setup()
        observeData()
        footer()

        return binding.root
    }

    private fun localize() {
        binding.textViewInfoWhenTitle.text = getString(viewModel.mWhen)
        binding.textViewInfoHowTitle.text = getString(viewModel.how)
        binding.textViewInfoReactionsTitle.text = getString(viewModel.reactions)
        binding.buttonPregnantTabInformationAdd.text = getString(viewModel.buttonAddInfo)
    }

    private fun setup() {

        // UI
        binding.textViewInfoWhenTitle.font(FontSize.HEAD, FontType.REGULAR, ContextCompat.getColor(binding.root.context, R.color.light))
        binding.textViewInfoHowTitle.font(FontSize.HEAD, FontType.REGULAR, ContextCompat.getColor(binding.root.context, R.color.light))
        binding.textViewInfoReactionsTitle.font(FontSize.HEAD, FontType.REGULAR, ContextCompat.getColor(binding.root.context, R.color.light))

        binding.textViewInfoWhenBody.font(FontSize.BODY, FontType.LIGHT, ContextCompat.getColor(binding.root.context, R.color.text))
        binding.textViewInfoHowBody.font(FontSize.BODY, FontType.LIGHT, ContextCompat.getColor(binding.root.context, R.color.text))
        binding.textViewInfoReactionsBody.font(FontSize.BODY, FontType.LIGHT, ContextCompat.getColor(binding.root.context, R.color.text))

        // Footer

        if (viewModel.permission == Permission.READ.value){
            binding.layoutButtons.visibility = GONE
        }else{
            binding.layoutButtons.visibility = VISIBLE
        }
    }

    private fun footer() {
        binding.buttonPregnantTabInformationAdd.setOnClickListener {
            AddInfoRouter().launch(binding.root.context)
        }
    }

    private fun observeData() = runBlocking{
        /*viewModel.loadInfo().observe(viewLifecycleOwner, Observer {
            binding.textViewInfoWhenBody.text = it[0].mWhen
            binding.textViewInfoHowBody.text = it[0].how
            binding.textViewInfoReactionsBody.text = it[0].reactions
        })*/
        withContext(Dispatchers.Default){
            viewModel.loadInformation()

            binding.textViewInfoWhenBody.text = viewModel.information?.mWhen
            binding.textViewInfoHowBody.text = viewModel.information?.how
            binding.textViewInfoReactionsBody.text = viewModel.information?.reactions
        }
    }

}

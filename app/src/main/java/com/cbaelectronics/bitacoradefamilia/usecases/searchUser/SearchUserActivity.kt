/**
 *  Created by CbaElectronics by Eduardo Sanchez on 3/16/23, 10:54 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.searchUser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivitySearchUserBinding
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.DatabaseField
import com.cbaelectronics.bitacoradefamilia.usecases.common.rows.UsersRecyclerViewAdapter
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil
import com.cbaelectronics.bitacoradefamilia.util.extension.addClose
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class SearchUserActivity : AppCompatActivity(), UsersRecyclerViewAdapter.onClickUserListener {

    // Properties

    private lateinit var binding: ActivitySearchUserBinding
    private lateinit var viewModel: SearchUserViewModel
    private lateinit var adapter: UsersRecyclerViewAdapter

    // Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchUserBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[SearchUserViewModel::class.java]

        // Adapter
        adapter = UsersRecyclerViewAdapter(this, this)

        // Setup
        localize()
        setup()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }

    override fun onItemClick(user: User) {
        val message = viewModel.messageQuestion(this, user.displayName!!)
        val positive = getString(viewModel.positive)
        val negative = getString(viewModel.negative)

        UIUtil.showAlert(
            context = this,
            message = message,
            positive = positive,
            positiveAction = {
                val intent = Intent()
                intent.putExtra(DatabaseField.EMAIL.key, user.email)
                intent.putExtra(DatabaseField.DISPLAY_NAME.key, user.displayName)
                intent.putExtra(DatabaseField.PROFILE_IMAGE_URL.key, user.photoProfile)
                intent.putExtra(DatabaseField.TOKEN.key, user.token)
                intent.putExtra(DatabaseField.REGISTERED_DATE.key, user.registerDate.toString())
                intent.putExtra(DatabaseField.TYPE.key, user.type.toString())
                setResult(RESULT_OK, intent)
                finish()
            },
            negative = negative
        )
    }

    // Private

    private fun localize() {
        binding.textViewSearchUsernInfo.text = getString(viewModel.title)
        binding.searchViewSearchUser.queryHint = getString(viewModel.search)
    }

    private fun setup() {
        addClose(this)

        // UI
        binding.textViewSearchUsernInfo.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )

        binding.recyclerViewSearchUser.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSearchUser.adapter = adapter

        search()
        observeData()
    }

    private fun search() {
        binding.searchViewSearchUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                adapter.filter.filter(query)
                adapter.notifyDataSetChanged()
                return true
            }
        })
    }

    private fun observeData() {
        viewModel.load().observe(this, Observer {
            adapter.setDataList(it)
            adapter.notifyDataSetChanged()
        })
    }
}
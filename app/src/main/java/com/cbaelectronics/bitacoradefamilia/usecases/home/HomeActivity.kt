package com.cbaelectronics.bitacoradefamilia.usecases.home

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityHomeBinding
import com.cbaelectronics.bitacoradefamilia.usecases.addChildren.AddChildrenRouter
import com.cbaelectronics.bitacoradefamilia.usecases.common.ChildrenRecyclerViewAdapter
import com.cbaelectronics.bitacoradefamilia.usecases.menu.MenuRouter
import com.cbaelectronics.bitacoradefamilia.usecases.pregnant.PregnantRouter
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIUtil
import com.cbaelectronics.bitacoradefamilia.util.extension.addClose
import com.cbaelectronics.bitacoradefamilia.util.extension.addCloseWithoutArrow
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: ChildrenRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        // Adapter
        adapter = ChildrenRecyclerViewAdapter(this)

        // Setup
        setup()

    }

    private fun setup() {

        addCloseWithoutArrow(this)

        // UI

        binding.textViewTitleApp.font(FontSize.TITLE, FontType.GALADA, getColor(R.color.light))

        binding.recyclerViewHome.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerViewHome.adapter = adapter

        observeDate()
        buttons()

    }

    private fun observeDate() {
        viewModel.load().observe(this, Observer {
            adapter.setDataList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun buttons() {

        binding.fab.setOnClickListener { view ->
            AddChildrenRouter().launch(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
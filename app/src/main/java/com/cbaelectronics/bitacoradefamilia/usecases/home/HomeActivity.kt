package com.cbaelectronics.bitacoradefamilia.usecases.home

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.databinding.ActivityHomeBinding
import com.cbaelectronics.bitacoradefamilia.usecases.about.AboutRouter
import com.cbaelectronics.bitacoradefamilia.usecases.addChildren.AddChildrenRouter
import com.cbaelectronics.bitacoradefamilia.usecases.common.rows.ChildrenRecyclerViewAdapter
import com.cbaelectronics.bitacoradefamilia.usecases.common.rows.SharedChildrenRecyclerViewAdapter
import com.cbaelectronics.bitacoradefamilia.usecases.onboarding.OnboardingRouter
import com.cbaelectronics.bitacoradefamilia.usecases.settings.SettingsRouter
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.extension.addCloseWithoutArrow
import com.cbaelectronics.bitacoradefamilia.util.extension.addCloseWithoutArrowAndTitle
import com.cbaelectronics.bitacoradefamilia.util.extension.addCloseWithoutTitle
import com.cbaelectronics.bitacoradefamilia.util.extension.font

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: ChildrenRecyclerViewAdapter
    private lateinit var adapterShared: SharedChildrenRecyclerViewAdapter
    private var countChildren: Int = 0
    private var countChildrenShared: Int = 0

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
        adapterShared = SharedChildrenRecyclerViewAdapter(this)

        // Setup
        localize()
        setup()

    }

    private fun localize() {
        binding.textViewTitleApp.text = ""//getString(viewModel.title)
        binding.textViewHomeTitle.text = getString(viewModel.info)
    }

    private fun setup() {
        addCloseWithoutArrow(this)
        // UI
        //binding.textViewTitleApp.font(FontSize.TITLE_APP, FontType.GALADA, getColor(R.color.light))

        binding.textViewHomeTitle.font(
            FontSize.BODY,
            FontType.REGULAR,
            ContextCompat.getColor(binding.root.context, R.color.text)
        )

        binding.recyclerViewHome.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerViewHome.adapter = adapter

        binding.recyclerViewHomeShare.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerViewHomeShare.adapter = adapterShared

        observeData()
        buttons()

    }

    private fun observeData() {
        viewModel.load().observe(this, Observer {
            adapter.setDataList(it)
            adapter.notifyDataSetChanged()
            countChildren = it.size
            checkTitle()
            if(it.size == 0){
                binding.recyclerViewHome.visibility = GONE
            }else{
                binding.recyclerViewHome.visibility = VISIBLE
            }
        })

        viewModel.loadShared().observe(this, Observer {
            adapterShared.setDataList(it)
            adapterShared.notifyDataSetChanged()
            countChildrenShared = it.size
            checkTitle()
            if(it.size == 0){
                binding.recyclerViewHomeShare.visibility = GONE
            }else{
                binding.recyclerViewHomeShare.visibility = VISIBLE
            }
        })
    }

    private fun checkTitle(){
        if(countChildren == 0 && countChildrenShared == 0){
            binding.textViewHomeTitle.visibility = VISIBLE
        }else{
            binding.textViewHomeTitle.visibility = GONE
        }
    }

    private fun buttons() {

        binding.fab.setOnClickListener { view ->
            AddChildrenRouter().launch(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_onboard -> {
                OnboardingRouter().launch(this)
            }
            R.id.action_about -> {
                AboutRouter().launch(this)
            }
            R.id.action_settings -> {
                SettingsRouter().launch(this)
            }
            R.id.action_close -> {
                loadAlertDialog()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    // Private

    private fun loadAlertDialog() {
        val mDialog = Dialog(binding.root.context)
        val mWindows = mDialog.window!!

        mWindows.attributes.windowAnimations = R.style.DialogAnimation
        mDialog.setContentView(R.layout.custom_dialog_opciones)
        mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog.setCancelable(false)
        val mText = mDialog.findViewById<TextView>(R.id.txtDialog)
        val mBtnOK = mDialog.findViewById<Button>(R.id.btnDialogAcept)
        val mBtnCancel = mDialog.findViewById<Button>(R.id.btnDialogCancel)
        val lottieDialog = mDialog.findViewById<LottieAnimationView>(R.id.lottieDialog)

        mText.text = getString(viewModel.alertLogout)
        lottieDialog.setAnimation(R.raw.logout)


        lottieDialog.loop(true)
        lottieDialog.playAnimation()

        mBtnOK.text = getString(viewModel.alertButtonOk)
        mBtnCancel.text = getString(viewModel.alertButtonCancel)

        mDialog.show()

        mBtnOK.setOnClickListener {
            mDialog.cancel()
            viewModel.close(this)
        }
        mBtnCancel.setOnClickListener {
            mDialog.cancel()

        }
    }
}
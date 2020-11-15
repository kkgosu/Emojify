package com.kvlg.emojify

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.kvlg.emojify.databinding.ActivityMainBinding
import com.kvlg.emojify.ui.main.SectionsPagerAdapter
import com.kvlg.emojify.ui.main.SharedViewModel
import com.kvlg.fluidlayout.FluidContentResizer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FluidContentResizer.listen(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                APPEARANCE_LIGHT_STATUS_BARS,
                APPEARANCE_LIGHT_STATUS_BARS
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = window.decorView.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.decorView.systemUiVisibility = flags
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        with(binding) {
            tabs.setupWithViewPager(viewPager)
            viewPager.adapter = sectionsPagerAdapter
            tabs.getTabAt(0)?.setIcon(R.drawable.ic_round_create_24)
            tabs.getTabAt(1)?.setIcon(R.drawable.ic_round_history_24)
            tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {}
                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    sharedViewModel.setScrollToTop(true)
                }
            })
        }
    }
}
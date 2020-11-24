package com.kvlg.emojify

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.play.core.review.ReviewManagerFactory
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

        sharedViewModel.showInAppReview.observe(this) {
            if (it) showInAppReview()
        }
    }

    private fun showInAppReview() {
        val reviewManager = ReviewManagerFactory.create(this)
        val requestReviewFlow = reviewManager.requestReviewFlow()
        requestReviewFlow.addOnCompleteListener { request ->
            if (request.isSuccessful) {
                val reviewInfo = request.result
                val flow = reviewManager.launchReviewFlow(this, reviewInfo)
                flow.addOnCompleteListener {
                    // Обрабатываем завершение сценария оценки
                }
            } else {
                Log.e(TAG, request.exception?.message ?: "Error in showInAppReview()")
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
package com.kvlg.emojify

import android.content.Intent
import android.content.res.Resources
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.animation.AccelerateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.graphics.createBitmap
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayout
import com.google.android.play.core.review.ReviewManagerFactory
import com.kvlg.emojify.databinding.ActivityMainBinding
import com.kvlg.emojify.domain.AppSettings
import com.kvlg.emojify.ui.main.SectionsPagerAdapter
import com.kvlg.emojify.ui.main.SharedViewModel
import com.kvlg.fluidlayout.FluidContentResizer
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.hypot
import kotlin.math.max

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appSettings: AppSettings

    private val sharedViewModel: SharedViewModel by viewModels()

    private var revealX: Int = 0
    private var revealY: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        appSettings = AppSettings(this)
        setTheme(if (appSettings.isLightTheme()) R.style.Theme_Emojify else R.style.Theme_Emojify_Night)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FluidContentResizer.listen(this)

        with(intent) {
            if (hasExtra(REVEAL_X) && hasExtra(REVEAL_Y)) {
                binding.coordinator.isInvisible = true

                revealX = getIntExtra(REVEAL_X, 0)
                revealY = getIntExtra(REVEAL_Y, 0)

                val viewTreeObserver = binding.coordinator.viewTreeObserver
                if (viewTreeObserver.isAlive) {
                    viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            revealActivity()
                            binding.coordinator.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        }
                    })
                }
            } else {
                binding.coordinator.isVisible = true
            }
        }

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
            changeThemeButton.setOnClickListener(this@MainActivity::presentActivity)
        }

        sharedViewModel.showInAppReview.observe(this) {
            if (it) showInAppReview()
        }
    }

    private fun revealActivity() {
        val finalRadius = max(binding.coordinator.width, binding.coordinator.height) * 1.1f
        ViewAnimationUtils.createCircularReveal(binding.coordinator, revealX, revealY, 0f, finalRadius).run {
            duration = 400
            interpolator = AccelerateInterpolator()
            binding.coordinator.isVisible = true
            start()
        }
    }

    private fun presentActivity(view: View) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "transition")
        val revealX: Int = (view.x + view.width / 2).toInt()
        val revealY: Int = (view.y + view.height / 2).toInt()

        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(REVEAL_X, revealX)
            putExtra(REVEAL_Y, revealY)
        }
        appSettings.setTheme(!appSettings.isLightTheme())
        ActivityCompat.startActivity(this, intent, options.toBundle())
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

    private fun setNewTheme(theme: Resources.Theme) {
        if (binding.hiddenImageView.isVisible) return

        val w = binding.coordinator.measuredWidth
        val h = binding.coordinator.measuredHeight

        val bitmap = createBitmap(w, h)
        val canvas = Canvas(bitmap)
        binding.coordinator.draw(canvas)

        binding.hiddenImageView.setImageBitmap(bitmap)
        binding.hiddenImageView.isVisible = true

        val finalRadius = hypot(w.toFloat(), h.toFloat())

        //TODO("Change theme over all views")

        val coordinates = IntArray(2)
        binding.changeThemeButton.getLocationOnScreen(coordinates)
        ViewAnimationUtils.createCircularReveal(binding.coordinator, coordinates[0], coordinates[1], 0f, finalRadius).run {
            duration = 400
            doOnEnd {
                binding.hiddenImageView.setImageDrawable(null)
                binding.hiddenImageView.isVisible = false
            }
            start()
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val REVEAL_X = "REVEAL_X"
        private const val REVEAL_Y = "REVEAL_Y"
    }
}
package com.kvlg.emojify

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.graphics.createBitmap
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayout
import com.google.android.play.core.review.ReviewManagerFactory
import com.kvlg.emojify.databinding.ActivityMainBinding
import com.kvlg.emojify.domain.AppSettings
import com.kvlg.emojify.ui.main.SectionsPagerAdapter
import com.kvlg.emojify.ui.main.SharedViewModel
import com.kvlg.fluidlayout.FluidContentResizer
import dagger.hilt.android.AndroidEntryPoint
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

        binding.hiddenImageView.apply {
            setImageBitmap(globalBitmap)
            scaleType = ImageView.ScaleType.MATRIX
        }

        with(intent) {
            revealX = getIntExtra(REVEAL_X, 0)
            revealY = getIntExtra(REVEAL_Y, 0)
            if (revealX != 0 && revealY != 0) {
                binding.hiddenImageView.isVisible = true

                val viewTreeObserver = binding.hiddenImageView.viewTreeObserver
                if (viewTreeObserver.isAlive) {
                    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            revealActivity()
                            binding.hiddenImageView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        }
                    })
                }
            } else {
                binding.hiddenImageView.isVisible = false
            }
        }

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
            changeThemeButton.setOnClickListener(this@MainActivity::presentActivity)
        }

        sharedViewModel.showInAppReview.observe(this) {
            if (it) showInAppReview()
        }
    }

    private fun presentActivity(view: View) {
        val w = binding.coordinator.measuredWidth
        val h = binding.coordinator.measuredHeight
        val bitmap = createBitmap(w, h)
        val canvas = Canvas(bitmap)
        binding.coordinator.draw(canvas)
        globalBitmap = bitmap

        val revealX: Int = (view.x + view.width / 2).toInt()
        val revealY: Int = (view.y + view.height / 2).toInt()

        appSettings.setTheme(!appSettings.isLightTheme())
        revealActivity()
        startActivity(newIntent(this, revealX, revealY))
    }

    private fun revealActivity() {
        with(binding.hiddenImageView) {
            val finalRadius = max(width, height) * 1.1f
            ViewAnimationUtils.createCircularReveal(this, revealX, revealY, 0f, finalRadius).run {
                doOnEnd {
                    setImageDrawable(null)
                    globalBitmap = null
                    isVisible = false
                }
                duration = 4000
                interpolator = AccelerateInterpolator()
                start()
            }
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

    private fun setNewTheme(theme: Resources.Theme, clickView: View) {
        if (binding.hiddenImageView.isVisible) return

        val w = binding.coordinator.measuredWidth
        val h = binding.coordinator.measuredHeight

        val bitmap = createBitmap(w, h)
        val canvas = Canvas(bitmap)
        binding.coordinator.draw(canvas)

        binding.hiddenImageView.setImageBitmap(bitmap)
        binding.hiddenImageView.isVisible = true

        val finalRadius = max(binding.coordinator.width, binding.coordinator.height) * 1.1f

        val revealX: Int = (clickView.x + clickView.width / 2).toInt()
        val revealY: Int = (clickView.y + clickView.height / 2).toInt()
        ViewAnimationUtils.createCircularReveal(binding.coordinator, revealX, revealY, 0f, finalRadius).run {
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

        fun newIntent(context: Context, x: Int? = null, y: Int? = null): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(REVEAL_X, x)
                putExtra(REVEAL_Y, y)
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        }
    }
}
package com.kvlg.emojify

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.graphics.createBitmap
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import com.google.android.material.tabs.TabLayout
import com.google.android.play.core.review.ReviewManagerFactory
import com.kvlg.emojify.databinding.ActivityMainBinding
import com.kvlg.emojify.domain.AppSettings
import com.kvlg.emojify.ui.main.SectionsPagerAdapter
import com.kvlg.emojify.ui.main.SharedViewModel
import com.kvlg.emojify.utils.updateForTheme
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
        updateForTheme(appSettings.currentTheme())

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

        binding.container.updatePadding(0, getStatusBarHeight(), 0, 0)

/*        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }*/

/*        if (appSettings.isLightTheme()) {
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
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.insetsController?.setSystemBarsAppearance(
                    APPEARANCE_LIGHT_STATUS_BARS.inv(),
                    APPEARANCE_LIGHT_STATUS_BARS
                )
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                var flags = window.decorView.systemUiVisibility
                flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                window.decorView.systemUiVisibility = flags

            }
        }*/


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
        val w = binding.container.measuredWidth
        val h = binding.container.measuredHeight
        val bitmap = createBitmap(w, h)
        val canvas = Canvas(bitmap)
        binding.container.draw(canvas)
        globalBitmap = bitmap

        val revealX: Int = (view.x + view.width / 2).toInt()
        val revealY: Int = (view.y + view.height / 2).toInt()

        appSettings.swapThemes()
        startActivity(newIntent(this, revealX, revealY))
    }

    private fun revealActivity() {
        with(binding.hiddenImageView) {
            val finalRadius = max(width, height) * 1.1f
            ViewAnimationUtils.createCircularReveal(binding.container, revealX, revealY, 0f, finalRadius).run {
                doOnEnd {
                    setImageDrawable(null)
                    isVisible = false
                    globalBitmap = null
                }
                duration = 1000
                interpolator = AccelerateInterpolator()
                start()
            }
        }
    }

    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
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
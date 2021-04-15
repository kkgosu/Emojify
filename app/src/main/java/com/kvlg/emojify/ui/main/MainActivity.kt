package com.kvlg.emojify.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
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
import com.kvlg.emojify.R
import com.kvlg.emojify.currentPage
import com.kvlg.emojify.databinding.ActivityMainBinding
import com.kvlg.emojify.globalBitmap
import com.kvlg.emojify.ui.SectionsPagerAdapter
import com.kvlg.emojify.utils.updateForTheme
import com.kvlg.fluidlayout.FluidContentResizer
import com.yandex.metrica.YandexMetrica
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.max

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val sharedViewModel: SharedViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    private var revealX: Int = 0
    private var revealY: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        YandexMetrica.resumeSession(this)
        updateForTheme(mainViewModel.getCurrentTheme())

        FluidContentResizer.listen(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.container.updatePadding(0, getStatusBarHeight(), 0, 0)
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


        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        with(binding) {
            tabs.setupWithViewPager(viewPager)
            viewPager.adapter = sectionsPagerAdapter
            tabs.getTabAt(0)?.setIcon(R.drawable.ic_round_create_24)
            tabs.getTabAt(1)?.setIcon(R.drawable.ic_round_history_24)
            tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    currentPage = tab?.position ?: 0
                    when (currentPage) {
                        0 -> sharedViewModel.onCreateTabOpen()
                        1 -> sharedViewModel.onHistoryTabOpen()
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    sharedViewModel.setScrollToTop(true)
                }
            })
            changeThemeButton.setOnClickListener {
                sharedViewModel.onThemeChange()
                presentActivity(it)
            }
        }
        binding.tabs.getTabAt(currentPage)?.select()
    }

    override fun onDestroy() {
        YandexMetrica.pauseSession(this)
        super.onDestroy()
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

        mainViewModel.swapThemes()
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
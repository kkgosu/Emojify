package com.kvlg.emojify

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.kvlg.emojify.databinding.ActivityScreenshotBinding
import kotlin.math.max

/**
 * @author Konstantin Koval
 * @since 02.04.2021
 */
class ScreenshotActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScreenshotBinding

    private var revealX: Int = 0
    private var revealY: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenshotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.screenshotImageView.apply {
            setImageBitmap(globalBitmap)
            scaleType = ImageView.ScaleType.MATRIX
        }

        with(intent) {
            if (hasExtra(REVEAL_X) && hasExtra(REVEAL_Y)) {
                revealX = getIntExtra(REVEAL_X, 0)
                revealY = getIntExtra(REVEAL_Y, 0)

                val viewTreeObserver = binding.screenshotImageView.viewTreeObserver
                if (viewTreeObserver.isAlive) {
                    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            revealActivity()
                            binding.screenshotImageView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        }
                    })
                }
            }
        }
    }


    private fun revealActivity() {
        with(binding.screenshotImageView) {
            val finalRadius = max(width, height) * 1.1f
            ViewAnimationUtils.createCircularReveal(this, revealX, revealY, 0f, finalRadius).run {
                doOnEnd {
                    setImageDrawable(null)
                    globalBitmap = null
                    finish()
                    startActivity(MainActivity.newIntent(this@ScreenshotActivity))
                }
                duration = 4000
                interpolator = AccelerateInterpolator()
                start()
            }
        }
    }

    companion object {
        private const val REVEAL_X = "REVEAL_X"
        private const val REVEAL_Y = "REVEAL_Y"

        fun newIntent(context: Context, x: Int, y: Int): Intent {
            return Intent(context, ScreenshotActivity::class.java).apply {
                putExtra(REVEAL_X, x)
                putExtra(REVEAL_Y, y)
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            }
        }
    }
}
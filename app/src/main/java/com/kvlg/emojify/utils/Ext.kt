package com.kvlg.emojify.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Build
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.kvlg.emojify.R

/**
 * @author Konstantin Koval
 * @since 12.11.2020
 */

@Composable
fun Toast(text: String) = Toast.makeText(LocalContext.current, text, Toast.LENGTH_SHORT).show()

fun Fragment.toast(text: String) = Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()

fun EditText.text() = text?.toString()

fun Fragment.showKeyboard() {
    val imm: InputMethodManager = requireContext().getSystemService()!!
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Fragment.hideKeyboard() {
    val imm: InputMethodManager = requireContext().getSystemService()!!
    imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
}

fun Fragment.copyToClipboard(text: String?) {
    val clipboardManager: ClipboardManager = requireContext().getSystemService()!!
    val clip = ClipData.newPlainText(CLIP_LABEL, text)
    clipboardManager.setPrimaryClip(clip)
    toast(getString(R.string.copy_text))
}

fun LottieAnimationView.hideAnimation() {
    visibility = View.GONE
    cancelAnimation()
}

fun LottieAnimationView.showAnimation() {
    visibility = View.VISIBLE
    playAnimation()
}

fun AppCompatActivity.updateForTheme(theme: Theme) {
    window.run {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }
    when (theme) {
        Theme.DARK -> {
            setTheme(R.style.Theme_Emojify_Night)
            setupWindowDecorView(0, window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv())
        }
        Theme.LIGHT -> {
            setTheme(R.style.Theme_Emojify)
            setupWindowDecorView(APPEARANCE_LIGHT_STATUS_BARS, window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
    }
}

private fun AppCompatActivity.setupWindowDecorView(r: Int, m: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.setSystemBarsAppearance(r, APPEARANCE_LIGHT_STATUS_BARS)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.decorView.systemUiVisibility = m
    }
}

enum class Theme(val storageKey: String) {
    LIGHT("light"),
    DARK("dark"),
}

private const val CLIP_LABEL = "CLIP_LABEL"
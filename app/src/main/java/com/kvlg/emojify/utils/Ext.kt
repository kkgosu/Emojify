package com.kvlg.emojify.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Build
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.kvlg.emojify.R

/**
 * @author Konstantin Koval
 * @since 12.11.2020
 */

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

fun AppCompatActivity.updateForTheme(theme: Theme) = when (theme) {
    Theme.DARK -> {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                0,
                APPEARANCE_LIGHT_STATUS_BARS
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = window.decorView.systemUiVisibility
            flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            window.decorView.systemUiVisibility = flags
            window.statusBarColor = ContextCompat.getColor(this, R.color.gray_900)
        }
        setTheme(R.style.Theme_Emojify_Night)
    }
    Theme.LIGHT -> {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                APPEARANCE_LIGHT_STATUS_BARS,
                APPEARANCE_LIGHT_STATUS_BARS
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = window.decorView.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.decorView.systemUiVisibility = flags
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        }
        setTheme(R.style.Theme_Emojify)
    }
}

enum class Theme(val storageKey: String) {
    LIGHT("light"),
    DARK("dark"),
}

private const val CLIP_LABEL = "CLIP_LABEL"
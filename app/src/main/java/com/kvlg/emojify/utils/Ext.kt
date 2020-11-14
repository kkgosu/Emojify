package com.kvlg.emojify.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
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

private const val CLIP_LABEL = "CLIP_LABEL"
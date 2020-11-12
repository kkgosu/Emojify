package com.kvlg.emojify.utils

import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment

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
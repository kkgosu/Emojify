package com.kvlg.emojify.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.getSystemService
import com.kvlg.emojify.R

/**
 * @author Konstantin Koval
 * @since 12.11.2020
 */

@Composable
fun Toast(text: String) = Toast.makeText(LocalContext.current, text, Toast.LENGTH_SHORT).show()

fun toast(id: Int, context: Context) = Toast.makeText(context, id, Toast.LENGTH_SHORT).show()

fun copyText(context: Context, text: String) {
    val clipboardManager: ClipboardManager = context.getSystemService()!!
    val clip = ClipData.newPlainText(CLIP_LABEL, text)
    clipboardManager.setPrimaryClip(clip)
    toast(R.string.copy_text, context)
}

const val CLIP_LABEL = "CLIP_LABEL"
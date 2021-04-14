package com.kvlg.emojify.ui

import androidx.recyclerview.widget.DiffUtil
import com.kvlg.emojify.model.EmojifyedText

/**
 * @author Konstantin Koval
 * @since 13.11.2020
 */
class HistoryDiffCallback : DiffUtil.ItemCallback<EmojifyedText>() {
    override fun areItemsTheSame(oldItem: EmojifyedText, newItem: EmojifyedText): Boolean {
        return oldItem.text == newItem.text
    }

    override fun areContentsTheSame(oldItem: EmojifyedText, newItem: EmojifyedText): Boolean {
        return oldItem == newItem
    }
}
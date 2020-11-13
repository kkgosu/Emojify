package com.kvlg.emojify.ui.main

import android.util.Log
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.kvlg.emojify.databinding.HistoryItemBinding
import com.kvlg.emojify.model.EmojifyedText

/**
 * @author Konstantin Koval
 * @since 13.11.2020
 */

class HistoryAdapter(
    private val clickListener: HistoryTextInteraction
) : AsyncListDifferDelegationAdapter<EmojifyedText>(HistoryDiffCallback()) {

    init {
        delegatesManager
            .addDelegate(emojifyedTextAdapterDelegate { emojifyedText ->
                clickListener.onItemClick(emojifyedText)
            })
    }

    interface HistoryTextInteraction {
        fun onItemClick(text: EmojifyedText)
    }
}

fun emojifyedTextAdapterDelegate(clickListener: (EmojifyedText) -> Unit) =
    adapterDelegateViewBinding<EmojifyedText, EmojifyedText, HistoryItemBinding>({ layoutInflater, parent ->
        HistoryItemBinding.inflate(layoutInflater, parent, false)
    }) {
        binding.root.setOnClickListener {
            clickListener(item)
        }
        bind {
            Log.d("DELEGATE", "emojifyedTextAdapterDelegate: ${it.joinToString()}")
            binding.apply {
                text.text = item.text
            }
        }
    }
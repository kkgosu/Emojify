package com.kvlg.emojify.ui.main

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

/**
 * @author Konstantin Koval
 * @since 12.04.2021
 */
open class BaseFragment : Fragment() {
    protected operator fun <T> LiveData<T>.invoke(consumer: (T) -> Unit) {
        observe(viewLifecycleOwner) { consumer(it) }
    }

    protected operator fun <T> LiveData<T>.invoke(consumer: () -> Unit) {
        observe(viewLifecycleOwner) { consumer() }
    }
}
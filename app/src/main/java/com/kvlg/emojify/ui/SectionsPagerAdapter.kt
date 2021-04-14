package com.kvlg.emojify.ui

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kvlg.emojify.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(
    private val context: Context,
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> CreateFragment.newInstance()
        1 -> HistoryFragment.newInstance()
        else -> throw IllegalStateException("There is no fragment with ID $position")
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return context.getString(TAB_TITLES[position])
    }
}
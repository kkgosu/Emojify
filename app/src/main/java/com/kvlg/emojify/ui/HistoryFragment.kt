package com.kvlg.emojify.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.kvlg.emojify.databinding.FragmentHistoryBinding
import com.kvlg.emojify.domain.Result
import com.kvlg.emojify.model.EmojifyedText
import com.kvlg.emojify.ui.main.BaseFragment
import com.kvlg.emojify.ui.main.SharedViewModel
import com.kvlg.emojify.utils.copyToClipboard
import com.kvlg.emojify.utils.hideAnimation
import com.kvlg.emojify.utils.showAnimation
import com.kvlg.emojify.utils.toast
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Konstantin Koval
 * @since 13.11.2020
 */
@AndroidEntryPoint
class HistoryFragment : BaseFragment(), HistoryAdapter.HistoryTextInteraction {

    private lateinit var adapter: HistoryAdapter

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter()
        subscribeToObservers()
    }

    private fun setupAdapter() {
        adapter = HistoryAdapter(this)
        binding.recyclerView.adapter = adapter
    }

    private fun subscribeToObservers() {
        with(viewModel) {
            scrollToTop { isScrollNeeded ->
                if (isScrollNeeded) {
                    binding.recyclerView.smoothScrollToPosition(0)
                }
            }
            history { result ->
                when (result) {
                    is Result.Success -> {
                        adapter.items = result.data
                        hideOrShowEmptyState(result.data.isEmpty())
                        viewModel.setScrollToTop(true)
                    }
                    else -> toast("Error on getting history :c")
                }
            }
        }
    }

    override fun onItemClick(text: EmojifyedText) {
        copyToClipboard(text.text)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun hideOrShowEmptyState(show: Boolean) {
        with(binding) {
            if (show) {
                emptyListAnimation.showAnimation()
                emptyTitle.visibility = View.VISIBLE
                emptySubtitle.visibility = View.VISIBLE
            } else {
                emptyListAnimation.hideAnimation()
                emptyTitle.visibility = View.GONE
                emptySubtitle.visibility = View.GONE
            }
        }
    }

    companion object {
        private const val TAG = "HistoryFragment"

        fun newInstance() = HistoryFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}
package com.kvlg.emojify.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kvlg.emojify.databinding.FragmentHistoryBinding
import com.kvlg.emojify.domain.Result
import com.kvlg.emojify.model.EmojifyedText
import com.kvlg.emojify.utils.copyToClipboard
import com.kvlg.emojify.utils.toast
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Konstantin Koval
 * @since 13.11.2020
 */
@AndroidEntryPoint
class HistoryFragment : Fragment(), HistoryAdapter.HistoryTextInteraction {

    private lateinit var adapter: HistoryAdapter

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = HistoryAdapter(this)
        binding.recyclerView.adapter = adapter
        viewModel.scrollToTop.observe(viewLifecycleOwner) {
            if (it) {
                binding.recyclerView.smoothScrollToPosition(0)
            }
        }
        viewModel.history.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    adapter.items = result.data
                    viewModel.setScrollToTop(true)
                }
                else -> toast("Error on getting history :c")
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

    companion object {
        private const val TAG = "HistoryFragment"

        fun newInstance() = HistoryFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}
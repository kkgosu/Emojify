package com.kvlg.emojify.ui.main

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kvlg.emojify.R
import com.kvlg.emojify.databinding.FragmentHistoryBinding
import com.kvlg.emojify.domain.Result
import com.kvlg.emojify.model.EmojifyedText
import com.kvlg.emojify.utils.toast
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Konstantin Koval
 * @since 13.11.2020
 */
@AndroidEntryPoint
class HistoryFragment : Fragment(), HistoryAdapter.HistoryTextInteraction {

    private lateinit var clipboardManager: ClipboardManager
    private lateinit var adapter: HistoryAdapter

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clipboardManager = requireContext().getSystemService()!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = HistoryAdapter(this)
        binding.recyclerView.adapter = adapter
        viewModel.history.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    adapter.items = result.data
                    binding.recyclerView.smoothScrollToPosition(0)
                }
                else -> toast("Error on getting history :c")
            }
        }
    }

    override fun onItemClick(text: EmojifyedText) {
        val clip = ClipData.newPlainText(CLIP_LABEL, text.text)
        clipboardManager.setPrimaryClip(clip)
        toast(getString(R.string.copy_text))
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        private const val TAG = "HistoryFragment"
        private const val CLIP_LABEL = "CLIP_LABEL"

        fun newInstance() = HistoryFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}
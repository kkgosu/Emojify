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
import com.kvlg.emojify.databinding.FragmentCreateBinding
import com.kvlg.emojify.utils.hideKeyboard
import com.kvlg.emojify.utils.text
import com.kvlg.emojify.utils.toast
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Konstantin Koval
 * @since 11.11.2020
 */
@AndroidEntryPoint
class CreateFragment : Fragment() {

    private lateinit var clipboardManager: ClipboardManager

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clipboardManager = requireContext().getSystemService()!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.createButton.setOnClickListener {
            hideKeyboard()
            binding.inputText.text()?.let(viewModel::emojifyText)
        }
        binding.copyButton.setOnClickListener {
            val clip = ClipData.newPlainText(CLIP_LABEL, binding.inputText.text())
            clipboardManager.setPrimaryClip(clip)
            toast(getString(R.string.copy_text))
        }
        binding.clearButton.setOnClickListener {
            binding.inputText.text?.clear()
        }
        viewModel.emojiText.observe(viewLifecycleOwner, binding.inputText::setText)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        private const val CLIP_LABEL = "CLIP_LABEL"

        fun newInstance() = CreateFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

}
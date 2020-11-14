package com.kvlg.emojify.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kvlg.emojify.databinding.FragmentCreateBinding
import com.kvlg.emojify.utils.copyToClipboard
import com.kvlg.emojify.utils.hideKeyboard
import com.kvlg.emojify.utils.text
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Konstantin Koval
 * @since 11.11.2020
 */
@AndroidEntryPoint
class CreateFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by activityViewModels()

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
            copyToClipboard(binding.inputText.text())
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
        fun newInstance() = CreateFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

}
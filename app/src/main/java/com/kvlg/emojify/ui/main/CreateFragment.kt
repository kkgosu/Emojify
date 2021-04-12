package com.kvlg.emojify.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.play.core.review.ReviewManagerFactory
import com.kvlg.emojify.currentText
import com.kvlg.emojify.databinding.FragmentCreateBinding
import com.kvlg.emojify.utils.copyToClipboard
import com.kvlg.emojify.utils.hideAnimation
import com.kvlg.emojify.utils.hideKeyboard
import com.kvlg.emojify.utils.showAnimation
import com.kvlg.emojify.utils.text
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Konstantin Koval
 * @since 11.11.2020
 */
@AndroidEntryPoint
class CreateFragment : BaseFragment() {

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribeToObservers()
        initViews()
    }

    private fun subscribeToObservers() {
        with(viewModel) {
            emojiText(binding.inputText::setText)
            loading { isLoading ->
                if (isLoading) {
                    binding.loadingAnimation.showAnimation()
                    binding.createButton.isEnabled = false
                } else {
                    binding.loadingAnimation.hideAnimation()
                    binding.createButton.isEnabled = true
                }
            }

            showInAppReview { isNeedToShow ->
                if (isNeedToShow) showInAppReview()
            }
        }
    }

    private fun initViews() {
        with(binding) {
            createButton.setOnClickListener {
                hideKeyboard()
                inputText.text()?.let(viewModel::emojifyText)
            }
            copyButton.setOnClickListener {
                copyToClipboard(inputText.text())
            }
            clearButton.setOnClickListener {
                inputText.text?.clear()
            }
            shareButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, binding.inputText.text())
                startActivity(Intent.createChooser(intent, "Share via"))
            }
            binding.inputText.setText(currentText)
        }
    }

    private fun showInAppReview() {
        val reviewManager = ReviewManagerFactory.create(requireContext())
        val requestReviewFlow = reviewManager.requestReviewFlow()
        requestReviewFlow.addOnCompleteListener { request ->
            if (request.isSuccessful) {
                val reviewInfo = request.result
                val flow = reviewManager.launchReviewFlow(requireActivity(), reviewInfo)
                flow.addOnCompleteListener {
                    // Обрабатываем завершение сценария оценки
                }
            } else {
                Log.e(TAG, request.exception?.message ?: "Error in showInAppReview()")
            }
        }
    }

    override fun onPause() {
        currentText = binding.inputText.text()
        super.onPause()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        private val TAG = "CreateFragment"
        fun newInstance() = CreateFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

}
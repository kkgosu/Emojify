package com.kvlg.emojify.ui.components

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.EmojiEmotions
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.android.play.core.review.ReviewManagerFactory
import com.kvlg.emojify.R
import com.kvlg.emojify.ui.main.SharedViewModel
import com.kvlg.emojify.ui.theme.EmojifyerTheme
import com.kvlg.emojify.utils.copyText
import com.kvlg.emojify.utils.findActivity

/**
 * @author Konstantin Koval
 * @since 29.11.2021
 */
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun CreateFragment(viewModel: SharedViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val emojiText by remember(viewModel::emojiText)
    val loading by remember(viewModel::loading)
    val keyboardController = LocalSoftwareKeyboardController.current
    val lottieComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.preloader_51))
    val progress by animateLottieCompositionAsState(composition = lottieComposition, iterations = LottieConstants.IterateForever)
    val showInAppReview by remember(viewModel::showInAppReview)
    if (showInAppReview) {
        showInAppReview(context, viewModel)
    }
    Box {
        Column {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(color = EmojifyerTheme.colors.editTextBackground),
                value = emojiText,
                onValueChange = viewModel::onTextChanged,
                keyboardActions = KeyboardActions(onDone = {
                    viewModel.emojifyText()
                    keyboardController?.hide()
                }),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                placeholder = { Text(text = stringResource(id = R.string.enter_text_here), color = EmojifyerTheme.colors.hintText) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = EmojifyerTheme.colors.text,
                    cursorColor = EmojifyerTheme.colors.pointer,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(EmojifyerTheme.colors.toolbarBackground)
                    .padding(12.dp), verticalAlignment = Alignment.Bottom
            ) {
                TextButtonVertical(onClick = {
                    viewModel.onShareClick()
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, emojiText)
                    context.startActivity(Intent.createChooser(intent, "Share via"))
                }, modifier = Modifier.weight(1f)) {
                    Icon(
                        imageVector = Icons.Rounded.Share,
                        contentDescription = stringResource(id = R.string.share),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        tint = EmojifyerTheme.colors.secondaryButtonText
                    )
                    Text(
                        text = stringResource(id = R.string.share),
                        color = EmojifyerTheme.colors.secondaryButtonText,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                TextButtonVertical(onClick = {
                    viewModel.clearText()
                    viewModel.onClearClick()
                }, modifier = Modifier.weight(1f)) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = stringResource(id = R.string.clear),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        tint = EmojifyerTheme.colors.secondaryButtonText
                    )
                    Text(
                        text = stringResource(id = R.string.clear),
                        color = EmojifyerTheme.colors.secondaryButtonText,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                TextButtonVertical(onClick = {
                    viewModel.onCopyClick()
                    copyText(context, emojiText)
                }, modifier = Modifier.weight(1f)) {
                    Icon(
                        imageVector = Icons.Rounded.ContentCopy,
                        contentDescription = stringResource(id = R.string.copy_button),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        tint = EmojifyerTheme.colors.secondaryButtonText
                    )
                    Text(
                        text = stringResource(id = R.string.copy_button),
                        color = EmojifyerTheme.colors.secondaryButtonText,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                ButtonVertical(onClick = {
                    viewModel.emojifyText()
                    keyboardController?.hide()
                }, modifier = Modifier.weight(1f)) {
                    Icon(
                        imageVector = Icons.Rounded.EmojiEmotions,
                        contentDescription = stringResource(id = R.string.emojify),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        tint = EmojifyerTheme.colors.mainButtonText
                    )
                    Text(
                        text = stringResource(id = R.string.emojify),
                        color = EmojifyerTheme.colors.mainButtonText,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
            NavigationBarInset(color = EmojifyerTheme.colors.background1)
        }
        AnimatedVisibility(visible = loading, enter = fadeIn(), exit = fadeOut()) {
            LottieAnimation(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.5f))
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = {}),
                composition = lottieComposition,
                progress = progress,
            )
        }
    }
}

private fun showInAppReview(context: Context, viewModel: SharedViewModel) {
    val reviewManager = ReviewManagerFactory.create(context)
    val requestReviewFlow = reviewManager.requestReviewFlow()
    requestReviewFlow.addOnCompleteListener { request ->
        if (request.isSuccessful) {
            val reviewInfo = request.result
            val flow = reviewManager.launchReviewFlow(context.findActivity(), reviewInfo)
            flow.addOnCompleteListener {
                viewModel.showInAppReview.value = false
            }
        } else {
            Log.e("CreateFragment", request.exception?.message ?: "Error in showInAppReview()")
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
@Preview
fun CreateFragmentPreview() {
    EmojifyerTheme(darkTheme = true) {
        CreateFragment()
    }
}
package com.kvlg.emojify.ui.components

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kvlg.emojify.R
import com.kvlg.emojify.domain.Result
import com.kvlg.emojify.domain.data
import com.kvlg.emojify.model.EmojifyedText
import com.kvlg.emojify.ui.main.SharedViewModel
import com.kvlg.emojify.ui.theme.EmojifyerTheme
import com.kvlg.emojify.ui.theme.Gray_700
import com.kvlg.emojify.utils.Toast
import com.kvlg.emojify.utils.copyText

/**
 * @author Konstantin Koval
 * @since 29.11.2021
 */

@ExperimentalFoundationApi
@Composable
fun HistoryFragment(viewModel: SharedViewModel = hiltViewModel()) {
    val historyList by viewModel.history.observeAsState()
    Box(modifier = Modifier.fillMaxSize().background(color = EmojifyerTheme.colors.background1)) {
        historyList?.let { result ->
            when (result) {
                is Result.Success -> {
                    if (historyList?.data.isNullOrEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            val lottieComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.lf30_editor_hkjhxsel))
                            val progress by animateLottieCompositionAsState(lottieComposition)
                            LottieAnimation(
                                modifier = Modifier
                                    .size(300.dp, 300.dp)
                                    .align(Alignment.CenterHorizontally),
                                composition = lottieComposition,
                                progress = progress,
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = R.string.there_is_no_content_yet),
                                style = MaterialTheme.typography.h4,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = R.string.all_the_emojifyed_texts_will_be_saved_there),
                                style = MaterialTheme.typography.h6,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        val scrollState = rememberLazyListState()
                        val context = LocalContext.current
                        Column {
                            LazyColumn(modifier = Modifier.weight(1f), state = scrollState, contentPadding = PaddingValues(top = 16.dp)) {
                                items(items = result.data) { item ->
                                    HistoryListItem(context = context, item = item)
                                }
                            }
                            NavigationBarInset(color = Color.Transparent)
                        }
                    }
                }
                else -> {
                    Toast(text = "Error on getting history :c")
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun HistoryListItem(context: Context, item: EmojifyedText) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(elevation = 8.dp, shape = MaterialTheme.shapes.large)
            .background(color = EmojifyerTheme.colors.background2)
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, color = Gray_700),
                onLongClick = {
                    copyText(context, item.text)
                },
                onClick = {},
            )
            .padding(8.dp),
        style = MaterialTheme.typography.body1,
        text = item.text,
        color = EmojifyerTheme.colors.text
    )
}

@ExperimentalFoundationApi
@Preview
@Composable
fun HistoryFragmentPreview() {
    EmojifyerTheme(darkTheme = false) {
        HistoryFragment()
    }
}
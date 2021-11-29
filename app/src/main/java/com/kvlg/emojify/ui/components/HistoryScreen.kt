package com.kvlg.emojify.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kvlg.emojify.R
import com.kvlg.emojify.model.EmojifyedText
import com.kvlg.emojify.ui.theme.EmojifyerTheme

/**
 * @author Konstantin Koval
 * @since 29.11.2021
 */

@Composable
fun HistoryFragment() {
    val isHistoryEmpty by remember { mutableStateOf(false) }
    Box {
        if (isHistoryEmpty) {
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
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            val scrollState = rememberLazyListState()
            LazyColumn(state = scrollState) {
                items(10) {
                    HistoryListItem(EmojifyedText("12332189u3jhfioajiojsdfioj28934uhj89234jh98jdaoijmioajd8912jmeidfia98jma89dma8i9dma89m3829h49832423"))
                }
            }
        }
    }
}

@Composable
fun HistoryListItem(item: EmojifyedText) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(elevation = 12.dp, shape = MaterialTheme.shapes.large)
            .background(color = MaterialTheme.colors.surface)
            .padding(8.dp),
        style = MaterialTheme.typography.body1,
        text = item.text
    )
}

@Preview
@Composable
fun HistoryFragmentPreview() {
    EmojifyerTheme {
        HistoryFragment()
    }
}
package com.kvlg.emojify.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.EmojiEmotions
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import com.kvlg.emojify.ui.components.ButtonVertical
import com.kvlg.emojify.ui.components.TextButtonVertical
import com.kvlg.emojify.ui.theme.EmojifyerTheme
import com.kvlg.emojify.ui.theme.Purple_500

/**
 * @author Konstantin Koval
 * @since 27.11.2021
 */
class ComposeMainAcitivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmojifyerTheme {

            }
        }
    }
}

@Composable
fun EmojifyerMainScreen() {
    Scaffold {

    }
}

@Composable
fun EmojifyerMainScreenPreview() {
    EmojifyerTheme {
        EmojifyerMainScreen()
    }
}

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

@ExperimentalMaterialApi
@Composable
fun CreateFragment() {
    val hint = stringResource(id = R.string.enter_text_here)
    val text by remember { mutableStateOf("") }
    Column(modifier = Modifier.animateContentSize()) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            value = text,
            onValueChange = {},
            placeholder = { Text(text = hint) },
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Purple_500,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp), verticalAlignment = Alignment.Bottom
        ) {
            TextButtonVertical(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = Icons.Rounded.Share,
                    contentDescription = stringResource(id = R.string.share),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(text = stringResource(id = R.string.share), modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            TextButtonVertical(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = Icons.Rounded.Clear,
                    contentDescription = stringResource(id = R.string.clear),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(text = stringResource(id = R.string.clear), modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            TextButtonVertical(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = Icons.Rounded.ContentCopy,
                    contentDescription = stringResource(id = R.string.copy_button),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(text = stringResource(id = R.string.copy_button), modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            ButtonVertical(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = Icons.Rounded.EmojiEmotions,
                    contentDescription = stringResource(id = R.string.emojify),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(text = stringResource(id = R.string.emojify), modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
//@Preview
fun CreateFragmentPreview() {
    EmojifyerTheme {
        CreateFragment()
    }
}
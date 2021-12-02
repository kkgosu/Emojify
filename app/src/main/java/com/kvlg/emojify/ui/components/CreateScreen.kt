package com.kvlg.emojify.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.EmojiEmotions
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import com.kvlg.emojify.R
import com.kvlg.emojify.ui.main.SharedViewModel
import com.kvlg.emojify.ui.theme.EmojifyerTheme
import com.kvlg.emojify.ui.theme.Purple_500
import com.kvlg.emojify.utils.CLIP_LABEL
import com.kvlg.emojify.utils.toast

/**
 * @author Konstantin Koval
 * @since 29.11.2021
 */

@ExperimentalMaterialApi
@Composable
fun CreateFragment(viewModel: SharedViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val emojiText by viewModel.emojiText.observeAsState("")
    Column(modifier = Modifier.animateContentSize()) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            value = emojiText,
            onValueChange = { viewModel.currentText = it },
            keyboardActions = KeyboardActions(onDone = {
                viewModel.emojifyText()
            }),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
            placeholder = { Text(text = stringResource(id = R.string.enter_text_here)) },
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
            TextButtonVertical(onClick = {
                viewModel.onShareClick()
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, viewModel.currentText)
                context.startActivity(Intent.createChooser(intent, "Share via"))
            }, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = Icons.Rounded.Share,
                    contentDescription = stringResource(id = R.string.share),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(text = stringResource(id = R.string.share), modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            TextButtonVertical(onClick = {
                viewModel.clearText()
                viewModel.onClearClick()
            }, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = Icons.Rounded.Clear,
                    contentDescription = stringResource(id = R.string.clear),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(text = stringResource(id = R.string.clear), modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            TextButtonVertical(onClick = {
                val clipboardManager: ClipboardManager = context.getSystemService()!!
                val clip = ClipData.newPlainText(CLIP_LABEL, viewModel.currentText)
                clipboardManager.setPrimaryClip(clip)
                toast(R.string.copy_text, context)
            }, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = Icons.Rounded.ContentCopy,
                    contentDescription = stringResource(id = R.string.copy_button),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(text = stringResource(id = R.string.copy_button), modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            ButtonVertical(onClick = {
                viewModel.emojifyText()
            }, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = Icons.Rounded.EmojiEmotions,
                    contentDescription = stringResource(id = R.string.emojify),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(text = stringResource(id = R.string.emojify), modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
        NavigationBarInset(color = MaterialTheme.colors.background)
    }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun CreateFragmentPreview() {
    EmojifyerTheme {
        CreateFragment()
    }
}
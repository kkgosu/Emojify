package com.kvlg.emojify.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kvlg.emojify.R
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

@ExperimentalMaterialApi
@Composable
fun CreateFragment() {
    val hint = stringResource(id = R.string.enter_text_here)
    val text by remember { mutableStateOf("") }
    Column {
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
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.Bottom) {
            TextButtonVertical(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Icon(imageVector = Icons.Rounded.Share, contentDescription = stringResource(id = R.string.share), modifier = Modifier.align(Alignment.CenterHorizontally))
                Text(text = stringResource(id = R.string.share), modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            TextButtonVertical(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Icon(imageVector = Icons.Rounded.Clear, contentDescription = stringResource(id = R.string.clear), modifier = Modifier.align(Alignment.CenterHorizontally))
                Text(text = stringResource(id = R.string.clear), modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            TextButtonVertical(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Icon(imageVector = Icons.Rounded.ContentCopy, contentDescription = stringResource(id = R.string.copy_button), modifier = Modifier.align(Alignment.CenterHorizontally))
                Text(text = stringResource(id = R.string.copy_button), modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            ButtonVertical(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Icon(imageVector = Icons.Rounded.EmojiEmotions, contentDescription = stringResource(id = R.string.emojify), modifier = Modifier.align(Alignment.CenterHorizontally))
                Text(text = stringResource(id = R.string.emojify), modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
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
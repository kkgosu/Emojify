package com.kvlg.emojify.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kvlg.emojify.R
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
fun CreateFragment() {
    val hint = stringResource(id = R.string.enter_text_here)
    val text by remember { mutableStateOf("") }
    TextField(
        modifier = Modifier.fillMaxSize(),
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
}

@Composable
@Preview
fun CreateFragmentPreview() {
    EmojifyerTheme {
        CreateFragment()
    }
}
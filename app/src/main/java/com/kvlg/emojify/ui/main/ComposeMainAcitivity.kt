package com.kvlg.emojify.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.kvlg.emojify.ui.theme.EmojifyerTheme

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

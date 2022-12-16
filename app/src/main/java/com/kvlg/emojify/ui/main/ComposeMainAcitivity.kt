package com.kvlg.emojify.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NightsStay
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.kvlg.emojify.R
import com.kvlg.emojify.ui.components.EmojifyScaffold
import com.kvlg.emojify.ui.components.TabItem
import com.kvlg.emojify.ui.components.Tabs
import com.kvlg.emojify.ui.components.TabsContent
import com.kvlg.emojify.ui.theme.EmojifyerTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Konstantin Koval
 * @since 27.11.2021
 */
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class ComposeMainAcitivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            EmojifyerTheme(darkTheme = mainViewModel.isLightTheme.value) {
                ProvideWindowInsets {
                    EmojifyerMainScreen(mainViewModel)
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun EmojifyerMainScreen(mainViewModel: MainViewModel, sharedViewModel: SharedViewModel = hiltViewModel()) {
    val tabs = listOf(
        TabItem.Create,
        TabItem.History
    )
    val pagerState = rememberPagerState(initialPage = 0)
    EmojifyScaffold(topBar = {
        TopAppBar(
            title = { Text(text = stringResource(R.string.app_name)) },
            backgroundColor = EmojifyerTheme.colors.toolbarBackground,
            contentColor = EmojifyerTheme.colors.toolbarOnBackground,
            elevation = 0.dp,
            actions = {
                IconButton(onClick = {
                    mainViewModel.switchThemes()
                    sharedViewModel.onThemeChange()
                }) {
                    Icon(
                        imageVector = Icons.Rounded.NightsStay,
                        contentDescription = "Switch theme",
                        tint = EmojifyerTheme.colors.toolbarOnBackground
                    )
                }
            }
        )
    }) {
        Column {
            Tabs(tabs = tabs, pagerState = pagerState)
            TabsContent(tabs = tabs, pagerState = pagerState)
        }
    }
}

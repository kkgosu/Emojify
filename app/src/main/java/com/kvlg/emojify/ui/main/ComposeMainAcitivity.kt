package com.kvlg.emojify.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NightsStay
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.kvlg.emojify.R
import com.kvlg.emojify.ui.components.EmojifyScaffold
import com.kvlg.emojify.ui.components.TabItem
import com.kvlg.emojify.ui.components.Tabs
import com.kvlg.emojify.ui.components.TabsContent
import com.kvlg.emojify.ui.theme.EmojifyerTheme
import com.yandex.metrica.YandexMetrica
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Konstantin Koval
 * @since 27.11.2021
 */
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
            val isMetricsEnabled = remember { mainViewModel.isMetricsEnabled }
            EmojifyerTheme(darkTheme = !mainViewModel.isLightTheme.value) {
                EmojifyerMainScreen(mainViewModel)
            }
            activateYandexMetrics(isMetricsEnabled.value)
        }
    }

    override fun onResume() {
        super.onResume()
        YandexMetrica.resumeSession(this)
    }

    override fun onPause() {
        super.onPause()
        YandexMetrica.pauseSession(this)
    }

    private fun activateYandexMetrics(isEnabled: Boolean) {
        YandexMetrica.setStatisticsSending(this, isEnabled)
    }
}

@OptIn(ExperimentalPagerApi::class)
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
private fun EmojifyerMainScreen(mainViewModel: MainViewModel, sharedViewModel: SharedViewModel = hiltViewModel()) {
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
        if (mainViewModel.isFirstLaunch.value) {
            MetricsAlert(mainViewModel = mainViewModel)
        }
    }
}

@Composable
private fun MetricsAlert(mainViewModel: MainViewModel) {
    AlertDialog(
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        onDismissRequest = {},
        dismissButton = {
            TextButton(onClick = {
                mainViewModel.setMetricsEnabled(false)
                mainViewModel.setNotFirstLaunch()
            }) {
                Text(text = "Maybe later")
            }
        },
        confirmButton = {
            Button(onClick = {
                mainViewModel.setMetricsEnabled(true)
                mainViewModel.setNotFirstLaunch()
            }) {
                Text(text = "Confirm")
            }
        },
        title = {
            Text(text = "Privacy info")
        },
        text = {
            Text(
                text = "This app uses AppMetrica. AppMetrica analyzes app usage data, including the device it is running on, the installation source, calculates conversion, collects statistics of your activity for product analytics and optimization, as well as for troubleshooting. Information collected in this way cannot identify you. Depersonalized information about your use of this app collected by AppMetrica tools will be transferred to Yandex and stored on Yandex’s server in the EU and the Russian Federation. Do you agree?"
            )
        }, shape = RoundedCornerShape(16.dp),
        backgroundColor = EmojifyerTheme.colors.background1,
        contentColor = EmojifyerTheme.colors.text
    )
}

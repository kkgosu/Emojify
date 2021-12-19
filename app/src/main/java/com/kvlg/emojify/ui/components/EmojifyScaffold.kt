package com.kvlg.emojify.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.statusBarsHeight
import com.kvlg.emojify.ui.theme.EmojifyerTheme

/**
 * @author Konstantin Koval
 * @since 03.12.2021
 */
@ExperimentalMaterialApi
@Composable
fun EmojifyScaffold(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    isFloatingActionButtonDocked: Boolean = false,
    statusBarColor: Color = EmojifyerTheme.colors.background1,
    navigationBarColor: Color = EmojifyerTheme.colors.background1,
    backgroundColor: Color = EmojifyerTheme.colors.background0,
    contentColor: Color = contentColorFor(backgroundColor),
    content: @Composable (PaddingValues) -> Unit
) {

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            Column {
                StatusBarInset(statusBarColor)
                topBar()
            }
        },
        bottomBar = {
            bottomBar()
            NavigationBarInset(navigationBarColor)
        },
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        isFloatingActionButtonDocked = isFloatingActionButtonDocked,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        content = content
    )
}

@Composable
fun StatusBarInset(color: Color) {
    Spacer(
        modifier = Modifier
            .background(color)
            .statusBarsHeight()
            .fillMaxWidth()
    )
}

@Composable
fun NavigationBarInset(color: Color) {
    Spacer(
        modifier = Modifier
            .background(color)
            .navigationBarsHeight()
            .fillMaxWidth()
    )
}
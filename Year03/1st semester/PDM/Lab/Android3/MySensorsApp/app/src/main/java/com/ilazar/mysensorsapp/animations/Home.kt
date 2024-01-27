package com.ilazar.mysensorsapp.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private enum class TabPage {
    Home, Work
}

@Composable
fun Home() {
    var tabPage by remember { mutableStateOf(TabPage.Home) }
    var isEditing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    suspend fun showEditMessage() {
        if (!isEditing) {
            isEditing = true
            delay(3000L)
            isEditing = false
        }
    }
    Scaffold(
        topBar = {
            HomeTabBar(
                tabPage = tabPage,
                onTabSelected = { tabPage = it }
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(
                isEditing = isEditing,
                onClick = {
                    coroutineScope.launch {
                        showEditMessage()
                    }
                })
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
            modifier = Modifier.padding(padding)
        ) {
            item { Text("$tabPage") }
            item { MyAsyncItem() }
            item { MyExpandableItem() }
        }
        EditMessage(shown = isEditing)
    }
}

@Composable
private fun HomeTabBar(
    tabPage: TabPage,
    onTabSelected: (tabPage: TabPage) -> Unit
) {
    TabRow(
        selectedTabIndex = tabPage.ordinal,
    ) {
        MyTab(
            icon = Icons.Default.Home,
            title = "Home",
            onClick = { onTabSelected(TabPage.Home) }
        )
        MyTab(
            icon = Icons.Default.AccountBox,
            title = "Work",
            onClick = { onTabSelected(TabPage.Work) }
        )
    }
}

@Composable
private fun EditMessage(shown: Boolean) {
    AnimatedVisibility(
        visible = shown,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.secondary,
            elevation = 4.dp
        ) {
            Text(
                text = "Edit is not available",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

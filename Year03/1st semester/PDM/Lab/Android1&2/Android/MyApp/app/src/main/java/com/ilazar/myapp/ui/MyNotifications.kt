package com.ilazar.myservices.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ilazar.myservices.util.createNotificationChannel
import com.ilazar.myservices.util.showSimpleNotification
import com.ilazar.myservices.util.showSimpleNotificationWithTapAction

@Composable
fun MyNotifications() {
    val context = LocalContext.current
    val channelId = "MyTestChannel"
    val notificationId = 0

    LaunchedEffect(Unit) {
        createNotificationChannel(channelId, context)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            "Notifications in Jetpack Compose",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(bottom = 100.dp)
        )

        // simple notification button
        Button(onClick = {
            showSimpleNotification(
                context,
                channelId,
                notificationId,
                "Simple notification",
                "This is a simple notification with default priority."
            )
        }, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Simple Notification")
        }

        // simple notification button with tap action
        Button(onClick = {
            showSimpleNotificationWithTapAction(
                context,
                channelId,
                notificationId,
                "Simple notification + Tap action",
                "This simple notification will open an activity on tap."
            )
        }, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Simple Notification + Tap Action")
        }
    }
}

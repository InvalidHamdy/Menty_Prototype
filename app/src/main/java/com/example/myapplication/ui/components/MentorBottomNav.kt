package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.myapplication.presentation.navigation.Screen

@Composable
fun MentorBottomNav(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 12.dp, horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val selected = MaterialTheme.colorScheme.primary
        val unselected = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)

        IconButton(onClick = { onNavigate(Screen.Schedule.route) }) {
            Icon(
                Icons.Default.CalendarMonth,
                contentDescription = "Schedule",
                tint = if (currentRoute == Screen.Schedule.route) selected else unselected,
                modifier = Modifier.size(28.dp)
            )
        }
        IconButton(
            onClick = { onNavigate(Screen.Home.route) },
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(
                    if (currentRoute == Screen.Home.route) selected
                    else MaterialTheme.colorScheme.surfaceVariant
                )
        ) {
            Icon(
                Icons.Default.Home,
                contentDescription = "Home",
                tint = if (currentRoute == Screen.Home.route) MaterialTheme.colorScheme.onPrimary
                else selected,
                modifier = Modifier.size(28.dp)
            )
        }
        IconButton(onClick = { onNavigate(Screen.Analytics.route) }) {
            Icon(
                Icons.Default.Analytics,
                contentDescription = "Analytics",
                tint = if (currentRoute == Screen.Analytics.route) selected else unselected,
                modifier = Modifier.size(28.dp)
            )
        }
        IconButton(onClick = { onNavigate(Screen.Settings.route) }) {
            Icon(
                Icons.Default.Settings,
                contentDescription = "Settings",
                tint = if (currentRoute == Screen.Settings.route) selected else unselected,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

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
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomNav(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(Color.White)
            .padding(vertical = 12.dp, horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val selectedColor = MaterialTheme.colorScheme.primary
        val unselectedColor = Color(0xFFC4C8C6)

        IconButton(onClick = { onNavigate("builder") }) {
            Icon(
                imageVector = Icons.Default.FormatListBulleted,
                contentDescription = "Habits",
                tint = if (currentRoute == "builder" || currentRoute == "breaker") selectedColor else unselectedColor,
                modifier = Modifier.size(28.dp)
            )
        }
        IconButton(onClick = { onNavigate("schedule") }) {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "Calendar",
                tint = if (currentRoute == "schedule") selectedColor else unselectedColor,
                modifier = Modifier.size(28.dp)
            )
        }
        IconButton(
            onClick = { onNavigate("home") },
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(if (currentRoute == "home") selectedColor else unselectedColor.copy(alpha = 0.2f))
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home",
                tint = if (currentRoute == "home") Color.White else selectedColor,
                modifier = Modifier.size(28.dp)
            )
        }
        IconButton(onClick = { onNavigate("analytics") }) {
            Icon(
                imageVector = Icons.Default.Analytics,
                contentDescription = "Analytics",
                tint = if (currentRoute == "analytics") selectedColor else unselectedColor,
                modifier = Modifier.size(28.dp)
            )
        }
        IconButton(onClick = { onNavigate("settings") }) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = if (currentRoute == "settings") selectedColor else unselectedColor,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

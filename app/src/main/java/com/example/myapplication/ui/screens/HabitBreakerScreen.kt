package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.BottomNav

@Composable
fun HabitBreakerScreen(onNavigate: (String) -> Unit) {
    Scaffold(
        bottomBar = { BottomNav(currentRoute = "breaker", onNavigate = onNavigate) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigate("add_bad_habit") },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MENTOR_OS",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.weight(1f)
                )
                Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(Color.White).padding(8.dp)) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Profile", tint = MaterialTheme.colorScheme.onSurface)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Builder / Breaker Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Transparent)
                        .padding(vertical = 10.dp)
                        .clickable { onNavigate("builder") },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Habit Builder", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant))
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Habit Breaker", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color.White))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text("Habit Breaker", style = MaterialTheme.typography.displayLarge.copy(color = MaterialTheme.colorScheme.onBackground))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Track and eliminate bad habits", style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
            Spacer(modifier = Modifier.height(24.dp))

            // Stats
            Row(modifier = Modifier.fillMaxWidth()) {
                StatBox(modifier = Modifier.weight(1f), label = "Active", value = "04")
                Spacer(modifier = Modifier.width(16.dp))
                StatBox(modifier = Modifier.weight(1f), label = "Violations", value = "12")
                Spacer(modifier = Modifier.width(16.dp))
                StatBox(modifier = Modifier.weight(1f), label = "Level", value = "02")
            }
            Spacer(modifier = Modifier.height(32.dp))

            BadHabitItem("Doom Scrolling", "Digital Overload", "08", "Violation", MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(16.dp))
            BadHabitItem("Late Night Snacks", "Dietary Disruption", "03", "Warning", MaterialTheme.colorScheme.tertiary)
            Spacer(modifier = Modifier.height(16.dp))
            BadHabitItem("Nail Biting", "Physical Trigger", "01", "Controlled", MaterialTheme.colorScheme.primary)

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun StatBox(modifier: Modifier = Modifier, label: String, value: String) {
    Column(modifier = modifier) {
        Text(text = label, style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
        Text(text = value, style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurface))
    }
}

@Composable
private fun BadHabitItem(title: String, category: String, violations: String, status: String, statusColor: Color) {
    Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White).padding(16.dp)) {
        Column {
            Row(verticalAlignment = Alignment.Top) {
                Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.background), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Close, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(title, style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(category, style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(status, style = MaterialTheme.typography.labelSmall.copy(color = statusColor))
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(violations, style = MaterialTheme.typography.displayLarge.copy(color = MaterialTheme.colorScheme.onSurface, fontSize = 32.sp))
                    Row {
                        IconButton(onClick = {}, modifier = Modifier.size(32.dp)) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                        }
                        IconButton(onClick = {}, modifier = Modifier.size(32.dp)) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}

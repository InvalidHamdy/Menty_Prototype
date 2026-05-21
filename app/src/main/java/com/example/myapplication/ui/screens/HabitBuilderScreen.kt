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
import com.example.myapplication.ui.components.BottomNav

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.myapplication.data.HabitType
import com.example.myapplication.viewmodel.MentorViewModel

@Composable
fun HabitBuilderScreen(viewModel: MentorViewModel, onNavigate: (String) -> Unit) {
    val allHabits by viewModel.habits.collectAsState()
    val habits = allHabits.filter { it.type == HabitType.GOOD }

    Scaffold(
        bottomBar = { BottomNav(currentRoute = "builder", onNavigate = onNavigate) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigate("add_good_habit") },
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
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(8.dp)
                ) {
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
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Habit Builder", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color.White))
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Transparent)
                        .padding(vertical = 10.dp)
                        .clickable { onNavigate("breaker") },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Habit Breaker", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Title
            Text("Habit Builder", style = MaterialTheme.typography.displayLarge.copy(color = MaterialTheme.colorScheme.onBackground))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Track and reinforce positive habits", style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
            Spacer(modifier = Modifier.height(24.dp))

            // Stats Row
            Row(modifier = Modifier.fillMaxWidth()) {
                StatBox(modifier = Modifier.weight(1f), label = "Active", value = String.format("%02d", habits.size))
                Spacer(modifier = Modifier.width(16.dp))
                StatBox(modifier = Modifier.weight(1f), label = "Done", value = "02")
                Spacer(modifier = Modifier.width(16.dp))
                StatBox(modifier = Modifier.weight(1f), label = "On Streak", value = "03")
                Spacer(modifier = Modifier.width(16.dp))
                StatBox(modifier = Modifier.weight(1f), label = "Best", value = "12")
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Habit Items
            habits.forEach { habit ->
                GoodHabitItem(
                    title = habit.name,
                    category = habit.category,
                    streak = "00", // Hardcoded for now as it's not in model
                    onRemove = { viewModel.removeHabit(habit.id) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            
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
private fun GoodHabitItem(title: String, category: String, streak: String, onRemove: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White).padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                Spacer(modifier = Modifier.height(4.dp))
                Text(category, style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary))
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("Current Streak", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(streak, style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.primary))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("DAYS", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant), modifier = Modifier.padding(bottom = 4.dp))
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.myapplication.data.Importance
import com.example.myapplication.ui.components.BottomNav
import com.example.myapplication.viewmodel.MentorViewModel

@Composable
fun HomeDashboardScreen(viewModel: MentorViewModel, onNavigate: (String) -> Unit) {
    val habits by viewModel.habits.collectAsState()
    val violations by viewModel.violations.collectAsState()
    val events by viewModel.events.collectAsState()
    val activeTimerSeconds by viewModel.activeTimerSeconds.collectAsState()
    val currentCycles by viewModel.currentCycles.collectAsState()

    val bestCycles by viewModel.bestCycles.collectAsState()
    val sessionStatus by viewModel.sessionStatus.collectAsState()
    
    val activeViolationsCount = violations.count { it.isActive }
    
    val minutes = activeTimerSeconds / 60
    val seconds = activeTimerSeconds % 60
    val timerText = String.format("%02d:%02d", minutes, seconds)

    Scaffold(
        bottomBar = { BottomNav(currentRoute = "home", onNavigate = onNavigate) },
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
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Section 1: Time Block
            Text(
                text = "Time Block: Work Level",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = timerText,
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 64.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Progress bar
            Box(modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)).background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))) {
                Box(modifier = Modifier.fillMaxWidth(
                    (activeTimerSeconds / 3600f)
                        .coerceIn(0f, 1f)
                ).height(8.dp).clip(RoundedCornerShape(4.dp)).background(MaterialTheme.colorScheme.primary))
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Violations Alert Card (tappable → Violation Log)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.error.copy(alpha = 0.08f))
                    .clickable { onNavigate("violation_log") }
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "VIOLATIONS: ${String.format("%02d", activeViolationsCount)}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "View Log →",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Section 2: Cycles
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).background(Color.White).padding(16.dp)) {
                    Column {
                        Text(text = "Current", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant))
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(text = currentCycles.toString(), style = MaterialTheme.typography.displayMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "CYCLES", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant), modifier = Modifier.padding(bottom = 4.dp))
                        }
                    }
                }
                Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).background(Color.White).padding(16.dp)) {
                    Column {
                        Text(text = "Your Best", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant))
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(text = bestCycles.toString(), style = MaterialTheme.typography.displayMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "CYCLES", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant), modifier = Modifier.padding(bottom = 4.dp))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { onNavigate("lockdown") },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Terminate Session", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
            }
            Spacer(modifier = Modifier.height(40.dp))

            // Section 3: Habit Tracker
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Habit Tracker", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant))
                Text(text = "STATUS: ${sessionStatus}", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 9.sp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            
            habits.take(2).forEach { habit ->
                Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White).padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { viewModel.removeHabit(habit.id) }, modifier = Modifier.size(24.dp)) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = habit.name, style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                            Text(text = habit.category, style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp))
                        }
                        Text(text = if (habit.progress.isNotEmpty()) habit.progress else habit.goal, style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            Spacer(modifier = Modifier.height(28.dp))

            // Section 4: Upcoming Events
            Text(text = "Upcoming Events", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant))
            Spacer(modifier = Modifier.height(16.dp))
            
            events.forEach { event ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.Top) {
                    Text(text = event.time, style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant), modifier = Modifier.width(60.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = event.title, style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                        val color = when(event.importance) {
                            Importance.CRITICAL -> MaterialTheme.colorScheme.error
                            Importance.ROUTINE -> MaterialTheme.colorScheme.primary
                            Importance.MAINTENANCE -> MaterialTheme.colorScheme.onSurfaceVariant
                        }
                        Text(text = event.type, style = MaterialTheme.typography.labelSmall.copy(color = color, fontSize = 10.sp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(100.dp)) // padding for bottom nav
        }
    }
}

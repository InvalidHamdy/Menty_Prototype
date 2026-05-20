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
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.components.BottomNav

@Composable
fun ScheduleScreen(onNavigate: (String) -> Unit) {
    Scaffold(
        bottomBar = { BottomNav(currentRoute = "schedule", onNavigate = onNavigate) },
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
            Spacer(modifier = Modifier.height(24.dp))

            // View Toggle (Timeline / List)
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
                    Text("Timeline", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color.White))
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Transparent)
                        .padding(vertical = 10.dp)
                        .clickable { onNavigate("event_schedule_list") },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Event List", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Title
            Text(
                text = "PROTOCOL: DAILY_EXECUTION",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "T-04:00:00",
                style = MaterialTheme.typography.displayLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(8.dp).clip(RoundedCornerShape(4.dp)).background(MaterialTheme.colorScheme.primary))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "SYNCED  •  NET: SECURE  •  ACTIVE", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.primary))
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Timeline Items
            ScheduleTimelineItem(
                timeStart = "00:00", timeEnd = "06:00",
                title = "REST_CYCLE", subtitle = "Regeneration Phase",
                icon = Icons.Default.Bedtime, statusIcon = Icons.Default.CheckCircle, statusColor = MaterialTheme.colorScheme.primary
            )
            ScheduleTimelineItem(
                timeStart = "06:00", timeEnd = "07:00",
                title = "PHYSICAL_CONDITIONING", subtitle = "Strength & Mobility",
                icon = Icons.Default.FitnessCenter, statusIcon = Icons.Default.CheckCircle, statusColor = MaterialTheme.colorScheme.primary
            )
            ScheduleTimelineItem(
                timeStart = "07:00", timeEnd = "08:00",
                title = "MAINTENANCE", subtitle = "Fueling & Prep",
                icon = Icons.Default.Coffee, statusIcon = Icons.Default.CheckCircle, statusColor = MaterialTheme.colorScheme.primary
            )

            // Active Block
            ActiveScheduleBlock()

            ScheduleTimelineItem(
                timeStart = "13:00", timeEnd = "17:00",
                title = "CORE_EXECUTION_BETA", subtitle = "Implementation Phase",
                icon = Icons.Default.DeveloperBoard, statusIcon = Icons.Outlined.Circle, statusColor = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun ScheduleTimelineItem(
    timeStart: String, timeEnd: String,
    title: String, subtitle: String,
    icon: ImageVector, statusIcon: ImageVector, statusColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.width(60.dp)) {
            Text(text = timeStart, style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onBackground))
            Text(text = timeEnd, style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
        }
        Box(
            modifier = Modifier.weight(1f).clip(RoundedCornerShape(12.dp)).background(Color.White).padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = title, style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                    Text(text = subtitle, style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                }
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Icon(imageVector = statusIcon, contentDescription = null, tint = statusColor, modifier = Modifier.size(24.dp))
    }
}

@Composable
fun ActiveScheduleBlock() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.width(60.dp)) {
            Text(text = "08:00", style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.primary))
            Text(text = "12:00", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)))
        }
        Box(
            modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).background(MaterialTheme.colorScheme.primary).padding(16.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Terminal, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "CORE_EXECUTION [ACTIVE]", style = MaterialTheme.typography.titleMedium.copy(color = Color.White))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Deep Work Sequence Alpha\nSystem Architecture Design", style = MaterialTheme.typography.bodyMedium.copy(color = Color.White.copy(alpha = 0.9f)))
                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    Box(modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(Color.White.copy(alpha = 0.2f)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                        Text("FOCUS: HIGH", style = MaterialTheme.typography.labelSmall.copy(color = Color.White))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(Color.White.copy(alpha = 0.2f)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                        Text("DND: ON", style = MaterialTheme.typography.labelSmall.copy(color = Color.White))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Box(modifier = Modifier.size(24.dp).clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
        }
    }
}

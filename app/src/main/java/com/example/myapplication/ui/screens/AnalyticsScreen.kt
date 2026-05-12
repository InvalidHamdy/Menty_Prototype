package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.BottomNav

@Composable
fun AnalyticsScreen(onNavigate: (String) -> Unit) {
    var selectedTab by remember { mutableStateOf("Today") }
    Scaffold(
        bottomBar = { BottomNav(currentRoute = "analytics", onNavigate = onNavigate) },
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

            // Title
            Text(
                text = "ANALYTICS",
                style = MaterialTheme.typography.displayLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Tabs
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { selectedTab = "Today" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTab == "Today") MaterialTheme.colorScheme.primary else Color.White,
                        contentColor = if (selectedTab == "Today") MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Today", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = { selectedTab = "This Week" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTab == "This Week") MaterialTheme.colorScheme.primary else Color.White,
                        contentColor = if (selectedTab == "This Week") MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("This Week", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Section 1: Discipline Score
            Text(
                text = "DISCIPLINE SCORE",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White).padding(24.dp)) {
                Column {
                    Text(
                        text = if (selectedTab == "Today") "87.4" else "82.1",
                        style = MaterialTheme.typography.displayLarge.copy(color = MaterialTheme.colorScheme.primary, fontSize = 72.sp, fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (selectedTab == "Today") "STATUS: NOMINAL" else "STATUS: WARNING",
                        style = MaterialTheme.typography.labelSmall.copy(color = if (selectedTab == "Today") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error)
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Section 2: Habit Completion
            Text(
                text = "HABIT COMPLETION",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White).padding(24.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(text = if (selectedTab == "Today") "75%" else "68%", style = MaterialTheme.typography.displayMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "YIELD", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 8.sp), modifier = Modifier.padding(bottom = 6.dp))
                        }
                    }
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Column {
                            Text(text = "COMPLETED", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 11.sp))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = if (selectedTab == "Today") "6" else "42", style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                        }
                        Column {
                            Text(text = "PENDING", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiary, fontSize = 11.sp))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = if (selectedTab == "Today") "2" else "15", style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Section 3: Interventions Tracker
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).background(Color.White).padding(16.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "INITIATED", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = if (selectedTab == "Today") "14" else "68", style = MaterialTheme.typography.displayMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                    }
                }
                Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).background(Color.White).padding(16.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "ANSWERED", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = if (selectedTab == "Today") "11" else "52", style = MaterialTheme.typography.displayMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                    }
                }
                Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).background(Color.White).padding(16.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "MISSED", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error, fontSize = 11.sp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = if (selectedTab == "Today") "3" else "16", style = MaterialTheme.typography.displayMedium.copy(color = MaterialTheme.colorScheme.error))
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Section 4: Time Balance
            Text(
                text = "TIME BALANCE (MIN)",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White).padding(24.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Column {
                            Text(text = "EARNED YIELD", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = if (selectedTab == "Today") "+120" else "+850", style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary))
                        }
                        Column {
                            Text(text = "EXPENDITURE", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = if (selectedTab == "Today") "-45" else "-320", style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.error))
                        }
                    }
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                        Text(text = "NET REMAINING", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = if (selectedTab == "Today") "75" else "530", style = MaterialTheme.typography.displayMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Section 5: Recent Violations
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate("violation_log") }
                    .padding(vertical = 4.dp)
            ) {
                Icon(imageVector = Icons.Default.Warning, contentDescription = "Warning", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "RECENT VIOLATIONS",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Violation Item 1
            if (selectedTab == "Today") {
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.Top) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "UNAUTHORIZED_ACCESS", style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                        Text(text = "ENTERTAINMENT_MODULE", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp, fontWeight = FontWeight.Bold))
                    }
                    Text(text = "14:32", style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                }
                // Violation Item 2
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.Top) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "SCHEDULE_DEVIATION", style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                        Text(text = "FOCUS_BLOCK_INTERRUPT", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp, fontWeight = FontWeight.Bold))
                    }
                    Text(text = "09:15", style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                }
            } else {
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.Top) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "BIOMETRIC_SPIKE", style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                        Text(text = "STRESS_CRITICAL", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp, fontWeight = FontWeight.Bold))
                    }
                    Text(text = "TUE 11:00", style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                }
            }

            Spacer(modifier = Modifier.height(100.dp)) // padding for bottom nav
        }
    }
}

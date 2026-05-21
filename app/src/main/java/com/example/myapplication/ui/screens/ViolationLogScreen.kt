package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.myapplication.ui.components.BottomNav
import com.example.myapplication.ui.components.ViolationItem

import androidx.compose.runtime.collectAsState
import com.example.myapplication.viewmodel.MentorViewModel

@Composable
fun ViolationLogScreen(
    viewModel: MentorViewModel,
    onNavigate: (String) -> Unit
) {
    val violations by viewModel.violations.collectAsState()
    var selectedTab by remember { mutableStateOf("Today") }
    var showHistoricalData by remember { mutableStateOf(false) }

    val filteredViolations = when(selectedTab) {
        "Today" -> violations.filter { it.date == "2023-10-27" }
        else -> violations.filter { it.date != "2023-10-27" }
    }
    Scaffold(
        bottomBar = { BottomNav(currentRoute = "violation_log", onNavigate = onNavigate) },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            item {
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
                
                // Title & Filter
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Violation Log",
                        style = MaterialTheme.typography.displayLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { /* TODO */ },
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(8.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filter",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
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
                        Text("Today")
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
                        Text("This Week")
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            if (selectedTab == "Today") {
                item {
                    Text(
                        text = "System Date: 2023-10-27",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                filteredViolations.forEach { violation ->
                    item {
                        ViolationItem(
                            title = violation.title,
                            time = violation.time,
                            description = violation.description,
                            status = violation.status,
                            isActive = violation.isActive,
                            onClick = { onNavigate("violation_details/${violation.id}") }
                        )
                    }
                }
            } else {
                val dates = filteredViolations.map { it.date }.distinct()
                dates.forEach { date ->
                    if (date == "2023-10-20" && !showHistoricalData) return@forEach
                    
                    item {
                        Text(
                            text = "System Date: $date",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }

                    filteredViolations.filter { it.date == date }.forEach { violation ->
                        item {
                            ViolationItem(
                                title = violation.title,
                                time = violation.time,
                                description = violation.description,
                                status = violation.status,
                                isActive = violation.isActive,
                                onClick = { onNavigate("violation_details/${violation.id}") }
                            )
                        }
                    }
                }

                if (!showHistoricalData) {
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        TextButton(
                            onClick = { showHistoricalData = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Load Historical Data",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

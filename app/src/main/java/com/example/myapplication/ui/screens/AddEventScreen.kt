package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.Event
import com.example.myapplication.data.Importance
import com.example.myapplication.ui.components.BottomNav
import com.example.myapplication.viewmodel.MentorViewModel
import java.util.UUID

@Composable
fun AddEventScreen(viewModel: MentorViewModel, onNavigate: (String) -> Unit, onBack: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var startHour by remember { mutableStateOf("9") }
    var startMinute by remember { mutableStateOf("00") }
    var startPeriod by remember { mutableStateOf("AM") }

    var endHour by remember { mutableStateOf("10") }
    var endMinute by remember { mutableStateOf("00") }
    var endPeriod by remember { mutableStateOf("AM") }
    var selectedImportance by remember { mutableStateOf(Importance.ROUTINE) }
    val formattedStart =
        "${startHour.toIntOrNull() ?: 0}:$startMinute $startPeriod"

    val formattedEnd =
        "${endHour.toIntOrNull() ?: 0}:$endMinute $endPeriod"

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
                IconButton(onClick = onBack, modifier = Modifier.background(Color.White, RoundedCornerShape(8.dp)).size(40.dp)) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onSurface)
                }
                Spacer(modifier = Modifier.width(16.dp))
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

            Text("Add Protocol Event", style = MaterialTheme.typography.displayLarge.copy(color = MaterialTheme.colorScheme.onBackground))
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Event Title") },
                placeholder = { Text("e.g., System Maintenance") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Time Window",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    "Start Time",
                    style = MaterialTheme.typography.labelSmall
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    OutlinedTextField(
                        value = startHour,
                        onValueChange = {
                            if (it.length <= 2) startHour = it
                        },
                        label = { Text("HH") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )

                    OutlinedTextField(
                        value = startMinute,
                        onValueChange = {
                            if (it.length <= 2) startMinute = it
                        },
                        label = { Text("MM") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )

                    Button(
                        onClick = {
                            startPeriod =
                                if (startPeriod == "AM") "PM"
                                else "AM"
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(startPeriod)
                    }
                }

                Text(
                    "End Time",
                    style = MaterialTheme.typography.labelSmall
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    OutlinedTextField(
                        value = endHour,
                        onValueChange = {
                            if (it.length <= 2) endHour = it
                        },
                        label = { Text("HH") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )

                    OutlinedTextField(
                        value = endMinute,
                        onValueChange = {
                            if (it.length <= 2) endMinute = it
                        },
                        label = { Text("MM") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )

                    Button(
                        onClick = {
                            endPeriod =
                                if (endPeriod == "AM") "PM"
                                else "AM"
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(endPeriod)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text("Classification", style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Importance.values().forEach { importance ->
                    Button(
                        onClick = { selectedImportance = importance },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedImportance == importance) MaterialTheme.colorScheme.primary else Color.White,
                            contentColor = if (selectedImportance == importance) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text(importance.name, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        viewModel.addEvent(
                            Event(
                                id = UUID.randomUUID().toString(),
                                time = formattedStart,
                                startTime = formattedStart,
                                endTime = formattedEnd,
                                title = title,
                                type = selectedImportance.name,
                                importance = selectedImportance
                            )
                        )
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.Save, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Save Event", style = MaterialTheme.typography.titleMedium.copy(color = Color.White))
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

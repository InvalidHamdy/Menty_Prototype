package com.example.myapplication.presentation.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.components.MentorBottomNav
import com.example.myapplication.ui.theme.MentorColors
import com.example.myapplication.ui.theme.StrictnessLevel

@Composable
fun StrictnessSetupScreen(
    onContinue: () -> Unit
) {
    var selected by remember { mutableStateOf(StrictnessLevel.BALANCED) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "System Strictness",
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Configure response threshold and friction profile for your behavioral contract.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(32.dp))

        StrictnessLevel.entries.forEach { level ->
            StrictnessOptionCard(
                level = level,
                selected = selected == level,
                onSelect = { selected = level }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text("CONTINUE SETUP", fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun StrictnessOptionCard(
    level: StrictnessLevel,
    selected: Boolean,
    onSelect: () -> Unit
) {
    val borderColor = if (selected) MentorColors.strictnessBorder(level)
    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onSelect)
            .padding(16.dp)
            .semantics {
                contentDescription = "${level.label} strictness. ${level.description}"
            }
    ) {
        Column {
            Text(
                text = level.label,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = if (selected) borderColor else MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = level.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SettingsScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    var selected by remember { mutableStateOf(StrictnessLevel.STRICT) }

    Scaffold(
        bottomBar = { MentorBottomNav(currentRoute = currentRoute, onNavigate = onNavigate) },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text("Tone & Strictness", style = MaterialTheme.typography.displayMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Adjust enforcement parameters at any time.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))
            StrictnessLevel.entries.forEach { level ->
                StrictnessOptionCard(
                    level = level,
                    selected = selected == level,
                    onSelect = { selected = level }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

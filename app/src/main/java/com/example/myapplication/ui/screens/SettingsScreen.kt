package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Anchor
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
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
fun SettingsScreen(onNavigate: (String) -> Unit) {
    Scaffold(
        bottomBar = { BottomNav(currentRoute = "settings", onNavigate = onNavigate) },
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
                text = "Tone & Strictness",
                style = MaterialTheme.typography.displayLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Configure system response parameters and establish behavioral boundaries for enforcement protocols.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Response Matrix
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Tune, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Response Matrix", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold))
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Matrix Options
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).background(MaterialTheme.colorScheme.primary).padding(16.dp)) {
                    Column {
                        Icon(imageVector = Icons.Default.Gavel, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Strict", style = MaterialTheme.typography.titleMedium.copy(color = Color.White))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Zero-tolerance structural enforcement.", style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha=0.8f)))
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).background(Color.White).padding(16.dp)) {
                    Column {
                        Icon(imageVector = Icons.Default.Psychology, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Rational", style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Logic-driven, analytical feedback.", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).background(Color.White).padding(16.dp)) {
                    Column {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Supportive", style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Encouraging positive reinforcement.", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).background(Color.White).padding(16.dp)) {
                    Column {
                        Icon(imageVector = Icons.Default.Anchor, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Grounding", style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Calm, centering prompts.", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Lockdown Mode
            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White).padding(24.dp)) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.error.copy(alpha=0.1f)).padding(8.dp)) {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Lock down mode", style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.error), modifier = Modifier.weight(1f))
                        Switch(
                            checked = true,
                            onCheckedChange = { /* TODO */ },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = MaterialTheme.colorScheme.error)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Overrides all lenient settings. Enforces immediate consequence rules upon violation without warning periods.",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Threshold Configuration
            Text("Threshold Configuration", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant))
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White).padding(24.dp)) {
                Column {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(text = "08", style = MaterialTheme.typography.displayLarge.copy(color = MaterialTheme.colorScheme.primary, fontSize = 48.sp))
                        Text(text = ".0", style = MaterialTheme.typography.displayMedium.copy(color = MaterialTheme.colorScheme.primary, fontSize = 32.sp), modifier = Modifier.padding(bottom = 4.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "High Strictness", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant), modifier = Modifier.padding(bottom = 12.dp))
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    // Slider visual mock
                    Box(modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)).background(MaterialTheme.colorScheme.onSurface.copy(alpha=0.1f))) {
                        Box(modifier = Modifier.fillMaxWidth(0.8f).height(8.dp).clip(RoundedCornerShape(4.dp)).background(MaterialTheme.colorScheme.primary))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Lenient", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant))
                        Text("Strict", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant))
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Consequence Rules
            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White).padding(24.dp)) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Consequence rules", style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface), modifier = Modifier.weight(1f))
                        Text("3 Active", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary, fontSize = 10.sp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(imageVector = Icons.Default.ExpandMore, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    // Table Header
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text("Trigger Condition", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant), modifier = Modifier.weight(1f))
                        Text("System Action", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant), modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    // Row 1
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text("Time limit > 5m", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface), modifier = Modifier.weight(1f))
                        Text("Force close target app.", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface), modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    // Row 2
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text("Missed Check-in", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface), modifier = Modifier.weight(1f))
                        Text("Log violation, block net.", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface), modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    // Row 3
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text("Bypass attempt", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface), modifier = Modifier.weight(1f))
                        Text("Initiate Lock down.", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.error), modifier = Modifier.weight(1f))
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Reward Rules
            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White).padding(24.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.MilitaryTech, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Reward rules", style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface), modifier = Modifier.weight(1f))
                    Text("1 Active", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary, fontSize = 10.sp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Spacer(modifier = Modifier.height(100.dp)) // padding for bottom nav
        }
    }
}

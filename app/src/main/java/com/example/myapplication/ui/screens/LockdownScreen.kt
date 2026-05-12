package com.example.myapplication.ui.screens

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun LockdownScreen(onNavigateToOverride: () -> Unit) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.error
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            Icon(Icons.Default.Lock, contentDescription = null, tint = Color.White, modifier = Modifier.size(64.dp))
            Spacer(modifier = Modifier.height(24.dp))
            Text("Lock down mode", style = MaterialTheme.typography.displayLarge.copy(color = Color.White))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Active since 14:00 – 16:30, 150min elapsed", style = MaterialTheme.typography.bodyMedium.copy(color = Color.White.copy(alpha=0.8f)))
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(Color.White.copy(alpha=0.2f)).padding(horizontal = 12.dp, vertical = 6.dp)) {
                Text("3 consecutive violations", style = MaterialTheme.typography.labelSmall.copy(color = Color.White))
            }
            Spacer(modifier = Modifier.height(48.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Why lock down was triggered", style = MaterialTheme.typography.titleMedium.copy(color = Color.White))
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Color.Black.copy(alpha=0.3f)).padding(16.dp)) {
                    Text(
                        "SYSTEM DETECTED REPEATED ATTEMPTS TO BYPASS CORE PROTOCOLS. UNAUTHORIZED ACCESS PATTERNS IDENTIFIED IN SECTOR 4G. AUTOMATIC ENFORCEMENT PROTOCOL EPSILON DEPLOYED TO PREVENT DATA COMPROMISE.",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text("What is restricted", style = MaterialTheme.typography.titleMedium.copy(color = Color.White))
                Spacer(modifier = Modifier.height(16.dp))
                RestrictionItem("Outbound network communications severed.")
                RestrictionItem("Secondary storage volumes dismounted.")
                RestrictionItem("User privilege level reduced to OBSERVER_ONLY.")
            }
            Spacer(modifier = Modifier.height(32.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Exit conditions", style = MaterialTheme.typography.titleMedium.copy(color = Color.White))
                Spacer(modifier = Modifier.height(16.dp))
                ExitConditionItem("Acknowledge security breach")
                ExitConditionItem("Complete identity verification protocol")
                ExitConditionItem("Await supervisor authorization")
            }
            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = onNavigateToOverride,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.Default.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Request override", style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun RestrictionItem(text: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Block, contentDescription = null, tint = Color.White.copy(alpha=0.7f), modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium.copy(color = Color.White))
    }
}

@Composable
private fun ExitConditionItem(text: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(20.dp).clip(RoundedCornerShape(4.dp)).background(Color.White.copy(alpha=0.3f)))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium.copy(color = Color.White))
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Lockdown Screen")
@Composable
fun LockdownScreenPreview() {
    com.example.myapplication.ui.theme.MentorTheme {
        LockdownScreen(onNavigateToOverride = {})
    }
}

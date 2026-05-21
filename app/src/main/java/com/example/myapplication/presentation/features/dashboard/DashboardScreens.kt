package com.example.myapplication.presentation.features.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.core.accessibility.LocalAccessibilityState
import com.example.myapplication.presentation.navigation.Screen
import com.example.myapplication.ui.components.MentorBottomNav
import com.example.myapplication.ui.theme.JetBrainsMono
import com.example.myapplication.ui.theme.MentorColors
import com.example.myapplication.ui.theme.StatusColors

@Composable
fun HomeDashboardScreen(
    viewModel: DashboardViewModel,
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val accessState = LocalAccessibilityState.current
    val gridPadding = if (accessState.touchTargetScaling) 16.dp else 12.dp
    val displayName = state.session.username.ifBlank { "Disciplined One" }
    val score = state.disciplineScore.toInt()

    Scaffold(
        bottomBar = { MentorBottomNav(currentRoute = currentRoute, onNavigate = onNavigate) },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(28.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "AI MENTOR",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "INDEX ${score}",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontFamily = JetBrainsMono,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Welcome back, $displayName.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f), RoundedCornerShape(20.dp))
                .padding(20.dp)
                .semantics {
                    contentDescription = "Discipline score $score out of 100"
                }
        ) {
            Column {
                Text(
                    text = "DISCIPLINE SCORE",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Text(
                    text = "$score / 100",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontFamily = JetBrainsMono,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = "Top 5% of global users today.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MentorColors.SuccessEmerald
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // System Alerts Banner (If active violations exist)
        if (state.violations.any { it.isActive }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(StatusColors.error(accessState.colorBlindMode).copy(alpha = 0.15f))
                    .border(1.dp, StatusColors.error(accessState.colorBlindMode), RoundedCornerShape(12.dp))
                    .padding(12.dp)
                    .clickable { onNavigate("violation_log") }
                    .semantics {
                        contentDescription = "Active system violations found! Click to open violation logs."
                    }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = StatusColors.error(accessState.colorBlindMode)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ACTIVE PROTOCOL VIOLATION DETECTED",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = StatusColors.error(accessState.colorBlindMode)
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = "Current Focus:",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.12f), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${state.currentFocusTitle} [Active]",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = { state.focusProgressPercent / 100f },
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${state.focusProgressPercent}% complete",
                    style = MaterialTheme.typography.labelMedium.copy(fontFamily = JetBrainsMono),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onNavigate(Screen.FocusSession.route) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("START FOCUS", fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Operations Panel Label
        Text(
            text = "SYSTEM CONTROLS",
            style = MaterialTheme.typography.labelMedium.copy(
                letterSpacing = 2.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Command Grid
        Column(verticalArrangement = Arrangement.spacedBy(gridPadding)) {
            Row(horizontalArrangement = Arrangement.spacedBy(gridPadding)) {
                GridItem(
                    label = "FOCUS BLOCK",
                    icon = Icons.Default.Timer,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(Screen.FocusSession.route) }
                )
                GridItem(
                    label = "MENTOR CALL",
                    icon = Icons.Default.PhoneCallback,
                    color = MentorColors.SuccessEmerald,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(Screen.CallOverlay.route) }
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(gridPadding)) {
                GridItem(
                    label = "VIOLATIONS",
                    icon = Icons.Default.Receipt,
                    color = MentorColors.WarningBurnedOrange,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(Screen.ViolationLog.route) }
                )
                GridItem(
                    label = "LOCKDOWN",
                    icon = Icons.Default.Lock,
                    color = MentorColors.CriticalCrimson,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(Screen.Lockdown.route) }
                )
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
    }
}

@Composable
fun GridItem(
    label: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val accessState = LocalAccessibilityState.current
    val itemHeight = if (accessState.touchTargetScaling) 110.dp else 96.dp

    Box(
        modifier = modifier
            .height(itemHeight)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp)
            .semantics {
                contentDescription = "Control item: $label"
            },
        contentAlignment = Alignment.CenterStart
    ) {
        Column {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

@Composable
fun ProfileScreen(
    viewModel: DashboardViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val accessState = LocalAccessibilityState.current
    val backButtonHeight = if (accessState.touchTargetScaling) 56.dp else 48.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(28.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.semantics { contentDescription = "Back button" }
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "COMMANDER PROFILE",
                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Avatar HUD
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(110.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Security,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = state.session.username.uppercase(),
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Black),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "DISCIPLINE LEVEL: ${state.session.disciplineRank}",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Statistics Block
        ProfileStatCard(label = "DIFFICULTY CYCLE BEST", value = "${state.bestCycles} HOURS")
        Spacer(modifier = Modifier.height(16.dp))
        ProfileStatCard(label = "COMPLIANCE SCORE", value = "92.4% COGNITIVE ACCURACY")
        Spacer(modifier = Modifier.height(16.dp))
        ProfileStatCard(label = "ACTIVE FOCUS SESSIONS", value = "28 BLOCKS LOGGED")

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.logout()
                onBack()
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier
                .fillMaxWidth()
                .height(backButtonHeight)
                .semantics {
                    contentDescription = "Log out of current Commander terminal session"
                },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "TERMINATE SESSION",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onError
                )
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
fun ProfileStatCard(label: String, value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
            .padding(16.dp)
            .semantics {
                contentDescription = "$label: $value"
            }
    ) {
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    letterSpacing = 1.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

@Composable
fun NotificationsScreen(
    viewModel: DashboardViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val accessState = LocalAccessibilityState.current

    val notifications = listOf(
        Pair("SYSTEM STATUS", "All modules online. High contrast accessibility calibrated."),
        Pair("SCHEDULER UPDATE", "Time block scheduled at 14:00 today. Strict lockdown bounds applied."),
        Pair("TELEMETRY LOG", "Compliance audit: 100% focused cycle logged yesterday."),
        Pair("SECURITY ENFORCER", "No bypass vectors detected. Active sandbox secure.")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(28.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.semantics { contentDescription = "Back button" }
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "AUDIT ALERTS",
                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(notifications) { notif ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
                        .padding(16.dp)
                        .semantics {
                            contentDescription = "${notif.first}: ${notif.second}"
                        }
                ) {
                    Column {
                        Text(
                            text = notif.first,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = notif.second,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }
            }
        }
    }
}

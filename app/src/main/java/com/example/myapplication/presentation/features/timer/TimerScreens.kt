package com.example.myapplication.presentation.features.timer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.core.accessibility.LocalAccessibilityState
import com.example.myapplication.ui.theme.JetBrainsMono
import com.example.myapplication.ui.theme.MentorColors
import com.example.myapplication.ui.theme.StatusColors

@Composable
fun FocusSessionScreen(
    viewModel: TimerViewModel,
    onNavigateToSummary: () -> Unit,
    onNavigateToLockdown: () -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val accessState = LocalAccessibilityState.current
    
    val buttonPadding = if (accessState.touchTargetScaling) 20.dp else 12.dp
    val buttonHeight = if (accessState.touchTargetScaling) 60.dp else 48.dp

    // Format timer seconds
    val hrs = state.activeTimerSeconds / 3600
    val mins = (state.activeTimerSeconds % 3600) / 60
    val secs = state.activeTimerSeconds % 60
    val timeFormatted = String.format("%02d:%02d:%02d", hrs, mins, secs)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(28.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.semantics { contentDescription = "Back to main HUD" }
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "FOCUS BLOCK",
                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Large Circular Diagnostic Clock
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(240.dp)
                .semantics {
                    contentDescription = "Circular active focus clock. Remaining time is $timeFormatted"
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawArc(
                    color = Color.DarkGray,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 8.dp.toPx())
                )
                drawArc(
                    color = Color(0xFF06B6D4),
                    startAngle = -90f,
                    sweepAngle = (state.activeTimerSeconds.toFloat() / 3600f) * 360f,
                    useCenter = false,
                    style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = timeFormatted,
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontFamily = JetBrainsMono,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 32.sp
                    )
                )
                Text(
                    text = state.sessionStatus,
                    style = MaterialTheme.typography.labelSmall.copy(
                        letterSpacing = 1.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (state.isTimerRunning) StatusColors.success(accessState.colorBlindMode) else StatusColors.warning(accessState.colorBlindMode)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Session Information Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(12.dp)
            ) {
                Column {
                    Text("CYCLE SPEED", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("${state.currentCycles}x SECURE", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(12.dp)
            ) {
                Column {
                    Text("LOCK DOWN", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("ACTIVE (AA)", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary))
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Dynamic Action Controls
        Column(verticalArrangement = Arrangement.spacedBy(buttonPadding), modifier = Modifier.fillMaxWidth()) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
                if (state.isTimerRunning) {
                    Button(
                        onClick = { viewModel.pauseSession() },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier
                            .weight(1f)
                            .height(buttonHeight)
                            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Pause, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("PAUSE CORE", style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold))
                        }
                    }
                } else {
                    Button(
                        onClick = { viewModel.resumeSession() },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .weight(1f)
                            .height(buttonHeight),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("RESUME CORE", style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold))
                        }
                    }
                }

                Button(
                    onClick = onNavigateToSummary,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    modifier = Modifier
                        .weight(1f)
                        .height(buttonHeight),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Done, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("FINISH BLOCK", style = MaterialTheme.typography.titleMedium.copy(color = Color.White, fontWeight = FontWeight.Bold))
                    }
                }
            }

            Button(
                onClick = onNavigateToLockdown,
                colors = ButtonDefaults.buttonColors(containerColor = StatusColors.error(accessState.colorBlindMode)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(buttonHeight)
                    .semantics {
                        contentDescription = "Trigger physical system lockdown take-over"
                    },
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.onError)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("TRIGGER TAKE-OVER (TEST)", style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onError, fontWeight = FontWeight.Bold))
                }
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
fun SessionSummaryScreen(
    viewModel: TimerViewModel,
    onBackToHome: () -> Unit
) {
    val accessState = LocalAccessibilityState.current
    val buttonHeight = if (accessState.touchTargetScaling) 60.dp else 48.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(Color(0xFF10B981).copy(alpha = 0.1f))
                .border(2.dp, Color(0xFF10B981), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(36.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "CYCLE SECURED",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Black, color = Color(0xFF10B981))
        )
        Text(
            text = "Time block successfully synchronized.",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Diagnostic logs
        SummaryRow(label = "TOTAL FOCUS RATIO", value = "100% SECURE")
        SummaryRow(label = "DEVIATIONS RESOLVED", value = "0 PROTOCOL INCIDENTS")
        SummaryRow(label = "DISCIPLINE GAIN", value = "+15 RATING POINTS")

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.terminateSession()
                onBackToHome()
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonHeight)
                .semantics {
                    contentDescription = "Confirm metrics and return to main Commander console"
                },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "SYNCHRONIZE CONSOLE",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .semantics {
                contentDescription = "$label is $value"
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface))
    }
    HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
}

@Composable
fun LockdownScreen(
    viewModel: TimerViewModel,
    onNavigateToOverride: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val accessState = LocalAccessibilityState.current
    val hrs = state.activeTimerSeconds / 3600
    val mins = (state.activeTimerSeconds % 3600) / 60
    val secs = state.activeTimerSeconds % 60
    val timeFormatted = String.format("%02d:%02d:%02d", hrs, mins, secs)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MentorColors.LockdownAmbient)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "LOCKDOWN MODE",
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Black,
                color = MentorColors.LockdownCrimsonGlow
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Sandbox enforced. Navigation disabled.",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.LightGray),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(220.dp)
                .semantics { contentDescription = "Lockdown countdown $timeFormatted remaining" }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawArc(
                    color = Color.DarkGray,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 10.dp.toPx())
                )
                drawArc(
                    color = MentorColors.CriticalCrimson,
                    startAngle = -90f,
                    sweepAngle = (state.activeTimerSeconds.toFloat() / 3600f) * 360f,
                    useCenter = false,
                    style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
                )
            }
            Text(
                text = timeFormatted,
                style = MaterialTheme.typography.displayLarge.copy(
                    fontFamily = JetBrainsMono,
                    color = MentorColors.CriticalCrimson,
                    fontSize = 28.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Objective time remaining on active block.",
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Emergency escape (−20 lifetime discipline score)",
            style = MaterialTheme.typography.labelSmall.copy(color = MentorColors.WarningBurnedOrange),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        com.example.myapplication.ui.components.LongPressHoldButton(
            label = "HOLD FOR EMERGENCY ESCAPE (5 MIN)",
            holdDurationMs = 300_000L,
            onComplete = onNavigateToOverride
        )
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
fun OverrideLockdownScreen(
    viewModel: TimerViewModel,
    onOverrideSuccess: () -> Unit
) {
    var reason by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val accessState = LocalAccessibilityState.current
    val fieldHeight = if (accessState.touchTargetScaling) 140.dp else 120.dp
    val buttonHeight = if (accessState.touchTargetScaling) 60.dp else 48.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = "EXECUTE BYPASS",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Black, color = StatusColors.error(accessState.colorBlindMode))
        )
        Text(
            text = "Bypassing lockdown requires a telemetry justification of 25 characters minimum. Your entry will be saved directly to the audit telemetry database with CRITICAL severity.",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.LightGray),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = reason,
            onValueChange = {
                reason = it
                error = null
            },
            label = { Text("JUSTIFICATION TEXT") },
            modifier = Modifier
                .fillMaxWidth()
                .height(fieldHeight)
                .semantics {
                    contentDescription = "Enter justification to bypass lockdown. Minimum 25 characters."
                },
            maxLines = 4,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = StatusColors.error(accessState.colorBlindMode),
                unfocusedBorderColor = Color.DarkGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        error?.let { err ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = err, color = StatusColors.error(accessState.colorBlindMode), style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
        }

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(32.dp))

        var readyToConfirm by remember { mutableStateOf(false) }
        if (!readyToConfirm) {
            com.example.myapplication.ui.components.LongPressHoldButton(
                label = "HOLD TO CONFIRM BYPASS (2.5s)",
                holdDurationMs = 2_500L,
                modifier = Modifier.fillMaxWidth(),
                onComplete = {
                    if (reason.length < 25) {
                        error = "Justification must be at least 25 characters. Currently ${reason.length}."
                    } else {
                        readyToConfirm = true
                    }
                }
            )
        } else {
            Button(
                onClick = {
                    viewModel.triggerLockdownOverride(reason)
                    onOverrideSuccess()
                },
                colors = ButtonDefaults.buttonColors(containerColor = StatusColors.error(accessState.colorBlindMode)),
                modifier = Modifier.fillMaxWidth().height(buttonHeight),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("CONFIRM TERMINATION (−20 SCORE)", fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
fun CallOverlayScreen(
    onCommit: () -> Unit,
    onPostpone: () -> Unit,
    onFailure: () -> Unit
) {
    val accessState = LocalAccessibilityState.current
    val buttonHeight = if (accessState.touchTargetScaling) 60.dp else 48.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MentorColors.BackgroundDark.copy(alpha = 0.98f))
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(MentorColors.PrimarySlateBlue.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Security,
                contentDescription = null,
                tint = MentorColors.SuccessEmerald,
                modifier = Modifier.size(48.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "AI MENTOR",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = "Intervention Required",
            style = MaterialTheme.typography.titleMedium,
            color = MentorColors.WarningBurnedOrange
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Your attention is drifting. Focus on System Design or system limits will engage in 30 seconds.",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.LightGray),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = onCommit,
            modifier = Modifier.fillMaxWidth().height(buttonHeight),
            colors = ButtonDefaults.buttonColors(containerColor = MentorColors.SuccessEmerald),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("I Will Work Now", fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(
            onClick = onPostpone,
            modifier = Modifier.fillMaxWidth().height(buttonHeight),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Give Me 5 Mins (−2 score)")
        }
        Spacer(modifier = Modifier.height(24.dp))
        TextButton(onClick = onFailure) {
            Text(
                "FAILURE / LOCKDOWN",
                color = MentorColors.CriticalCrimson,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

package com.example.myapplication.presentation.features.violations

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.core.accessibility.LocalAccessibilityState
import com.example.myapplication.domain.model.Severity
import com.example.myapplication.domain.model.Violation
import com.example.myapplication.ui.theme.JetBrainsMono
import com.example.myapplication.ui.theme.StatusColors

@Composable
fun ViolationLogScreen(
    viewModel: ViolationsViewModel,
    onNavigateToDetail: (String) -> Unit,
    onBack: () -> Unit
) {
    val violations by viewModel.violations.collectAsStateWithLifecycle()
    val accessState = LocalAccessibilityState.current

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
                text = "VIOLATION TELEMETRY",
                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Summary bar
        val activeCount = violations.count { it.isActive }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(
                    if (activeCount > 0) StatusColors.error(accessState.colorBlindMode).copy(alpha = 0.12f)
                    else MaterialTheme.colorScheme.surface
                )
                .border(
                    1.dp,
                    if (activeCount > 0) StatusColors.error(accessState.colorBlindMode).copy(alpha = 0.3f)
                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                    RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = if (activeCount > 0) "$activeCount ACTIVE VIOLATION(S) DETECTED" else "ALL VIOLATIONS RESOLVED — COMPLIANCE NOMINAL",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = JetBrainsMono,
                    color = if (activeCount > 0) StatusColors.error(accessState.colorBlindMode) else StatusColors.success(accessState.colorBlindMode)
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (violations.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.VerifiedUser,
                        contentDescription = null,
                        tint = StatusColors.success(accessState.colorBlindMode),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "NO VIOLATIONS LOGGED",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(violations, key = { it.id }) { violation ->
                    ViolationCard(
                        violation = violation,
                        onClick = { onNavigateToDetail(violation.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun ViolationCard(
    violation: Violation,
    onClick: () -> Unit
) {
    val accessState = LocalAccessibilityState.current
    val severityColor = when (violation.severity) {
        Severity.CRITICAL -> StatusColors.error(accessState.colorBlindMode)
        Severity.MEDIUM -> StatusColors.warning(accessState.colorBlindMode)
        Severity.MINOR -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    val borderColor = if (violation.isActive) severityColor.copy(alpha = 0.5f)
    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp)
            .semantics {
                contentDescription = "Violation ${violation.code}: ${violation.title}. Severity ${violation.severity.name}. Status ${violation.status}"
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.Top) {
                // Severity indicator dot
                Box(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .size(10.dp)
                        .clip(RoundedCornerShape(50))
                        .background(severityColor)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = violation.title.uppercase(),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "${violation.code} · ${violation.time} · ${violation.date}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontFamily = JetBrainsMono
                        )
                    )
                }
            }
            // Status badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (violation.isActive) severityColor.copy(alpha = 0.15f)
                        else MaterialTheme.colorScheme.surfaceVariant
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = violation.status,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (violation.isActive) severityColor else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}

@Composable
fun ViolationDetailScreen(
    viewModel: ViolationsViewModel,
    violationId: String,
    onBack: () -> Unit
) {
    val violation by viewModel.selectedViolation.collectAsStateWithLifecycle()
    val accessState = LocalAccessibilityState.current

    LaunchedEffect(violationId) {
        viewModel.loadViolationDetails(violationId)
    }

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
                onClick = {
                    viewModel.clearDetails()
                    onBack()
                },
                modifier = Modifier.semantics { contentDescription = "Back button" }
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "VIOLATION DETAIL",
                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (violation == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else {
            val v = violation!!
            val severityColor = when (v.severity) {
                Severity.CRITICAL -> StatusColors.error(accessState.colorBlindMode)
                Severity.MEDIUM -> StatusColors.warning(accessState.colorBlindMode)
                Severity.MINOR -> MaterialTheme.colorScheme.onSurfaceVariant
            }

            // Header Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, severityColor.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Warning,
                            contentDescription = null,
                            tint = severityColor,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = v.title.uppercase(),
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = v.code,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontFamily = JetBrainsMono,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Status + Severity Row
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(severityColor.copy(alpha = 0.15f))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                "SEVERITY: ${v.severity.name}",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = severityColor)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (v.isActive) severityColor.copy(alpha = 0.15f)
                                    else MaterialTheme.colorScheme.surfaceVariant
                                )
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                v.status,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (v.isActive) severityColor else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Detail Fields
            DetailField(label = "TIMESTAMP", value = "${v.time} · ${v.date}")
            DetailField(label = "DESCRIPTION", value = v.description)
            DetailField(label = "STATUS", value = if (v.isActive) "ACTIVE — REQUIRES RESOLUTION" else "RESOLVED — NO ACTION REQUIRED")
            DetailField(label = "AUDIT TRACE", value = "Logged by MENTOR_OS telemetry engine. Record ID: ${v.id}")
        }
    }
}

@Composable
fun DetailField(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

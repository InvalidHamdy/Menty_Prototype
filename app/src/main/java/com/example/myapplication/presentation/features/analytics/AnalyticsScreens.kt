package com.example.myapplication.presentation.features.analytics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.presentation.features.dashboard.DashboardViewModel
import com.example.myapplication.ui.components.MentorBottomNav
import com.example.myapplication.ui.theme.JetBrainsMono
import com.example.myapplication.ui.theme.MentorColors

@Composable
fun AnalyticsScreen(
    viewModel: DashboardViewModel,
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val disciplineScore = state.disciplineScore
    val weeklyScores = listOf(72f, 78f, 81f, 79f, 68f, 84f, disciplineScore)

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
            Text("Behavioral Review", style = MaterialTheme.typography.displayMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Scientific performance telemetry — no decorative metrics.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))

            MetricCard(title = "DISCIPLINE SCORE (7D)") {
                DisciplineChart(scores = weeklyScores)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "${disciplineScore.toInt()} / 100",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontFamily = JetBrainsMono,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            MetricCard(title = "CORRELATION INSIGHT") {
                Text(
                    text = "Friday evening drop-offs detected between 18:00–22:00. " +
                        "Average score falls 12 points vs weekday baseline.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "ACTIONABLE IMPROVEMENTS",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                listOf(
                    "Pre-schedule a Strict chrono-block before 17:30 on Fridays.",
                    "Enable Extreme lockdown for the final study segment.",
                    "Reduce postpone allowances to zero after 20:00."
                ).forEach { item ->
                    Text("• $item", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            MetricCard(title = "ONE PERCENT RULE") {
                val delta = disciplineScore - 82f
                Text(
                    text = if (delta >= 0) "+${"%.1f".format(delta)}% vs rolling average"
                    else "${"%.1f".format(delta)}% vs rolling average",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = JetBrainsMono,
                        color = if (delta >= 0) MentorColors.SuccessEmerald else MentorColors.WarningBurnedOrange
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { (disciplineScore / 100f).coerceIn(0f, 1f) },
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun MetricCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(20.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(12.dp))
        content()
    }
}

@Composable
private fun DisciplineChart(scores: List<Float>) {
    val primary = MaterialTheme.colorScheme.primary
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        if (scores.size < 2) return@Canvas
        val max = 100f
        val min = scores.minOrNull() ?: 0f
        val range = (max - min).coerceAtLeast(1f)
        val stepX = size.width / (scores.size - 1)
        val path = Path()
        scores.forEachIndexed { index, score ->
            val x = index * stepX
            val y = size.height - ((score - min) / range) * size.height
            if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        drawPath(
            path = path,
            color = primary,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )
        scores.forEachIndexed { index, score ->
            val x = index * stepX
            val y = size.height - ((score - min) / range) * size.height
            drawCircle(color = primary, radius = 4.dp.toPx(), center = Offset(x, y))
        }
    }
}

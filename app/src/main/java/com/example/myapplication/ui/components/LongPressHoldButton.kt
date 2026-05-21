package com.example.myapplication.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun LongPressHoldButton(
    label: String,
    holdDurationMs: Long = 300_000L,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onComplete: () -> Unit
) {
    var holding by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(holding) {
        if (!holding) {
            progress = 0f
            return@LaunchedEffect
        }
        val steps = 50
        val stepMs = holdDurationMs / steps
        repeat(steps) { step ->
            if (!holding) return@LaunchedEffect
            delay(stepMs)
            progress = (step + 1) / steps.toFloat()
        }
        if (holding) {
            holding = false
            progress = 0f
            onComplete()
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .pointerInput(enabled) {
                if (!enabled) return@pointerInput
                detectTapGestures(
                    onPress = {
                        holding = true
                        tryAwaitRelease()
                        holding = false
                    }
                )
            }
            .semantics {
                contentDescription = "$label. Hold for ${holdDurationMs / 1000} seconds to confirm."
            },
        contentAlignment = Alignment.Center
    ) {
        if (holding && progress > 0f) {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                color = MaterialTheme.colorScheme.error
            )
        }
        Text(
            text = if (holding) "HOLD… ${(progress * 100).toInt()}%" else label,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

package com.example.myapplication.presentation.features.schedule

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.myapplication.domain.model.Event
import com.example.myapplication.domain.model.Importance
import com.example.myapplication.ui.components.MentorBottomNav
import com.example.myapplication.ui.theme.JetBrainsMono
import com.example.myapplication.ui.theme.MentorColors
import com.example.myapplication.ui.theme.StatusColors

private data class ChronoBlock(
    val label: String,
    val timeRange: String,
    val color: Color,
    val locked: Boolean = false
)

@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel,
    currentRoute: String,
    onNavigateToAddEvent: () -> Unit,
    onNavigateToEventDetail: (String) -> Unit,
    onNavigate: (String) -> Unit,
    onBack: () -> Unit
) {
    val events by viewModel.events.collectAsStateWithLifecycle()
    val accessState = LocalAccessibilityState.current
    val chronoBlocks = listOf(
        ChronoBlock("Study", "06:00 – 12:00", MentorColors.PrimarySlateBlue, locked = true),
        ChronoBlock("Habits", "12:00 – 14:00", MentorColors.SuccessEmerald),
        ChronoBlock("Free", "14:00 – 18:00", MentorColors.WarningBurnedOrange),
        ChronoBlock("Sleep", "22:00 – 06:00", MentorColors.OnSurfaceMuted.copy(alpha = 0.6f))
    )

    Scaffold(
        bottomBar = { MentorBottomNav(currentRoute = currentRoute, onNavigate = onNavigate) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddEvent,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.semantics { contentDescription = "Add new schedule event" }
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingVals ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingVals)
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.semantics { contentDescription = "Back button" }
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Chrono-blocks",
                        style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = null,
                            tint = MentorColors.WarningBurnedOrange,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Active block locked — edits require approval",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            chronoBlocks.forEach { block ->
                ChronoBlockRow(block = block)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Scheduled events",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(12.dp))

            if (events.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.CalendarToday,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "NO EVENTS SCHEDULED",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            "Tap + to create an event",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(events, key = { it.id }) { event ->
                        EventCard(
                            event = event,
                            onClick = {
                                viewModel.selectEvent(event)
                                onNavigateToEventDetail(event.id)
                            },
                            onDelete = { viewModel.deleteEvent(event.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EventCard(
    event: Event,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val accessState = LocalAccessibilityState.current
    val importanceColor = when (event.importance) {
        Importance.CRITICAL -> StatusColors.error(accessState.colorBlindMode)
        Importance.ROUTINE -> MaterialTheme.colorScheme.primary
        Importance.MAINTENANCE -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp)
            .semantics {
                contentDescription = "${event.title}. From ${event.startTime} to ${event.endTime}. Importance: ${event.importance.name}"
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.Top) {
                // Time column
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = event.startTime,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontFamily = JetBrainsMono,
                            fontWeight = FontWeight.Bold,
                            color = importanceColor
                        )
                    )
                    Text(
                        text = event.endTime,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontFamily = JetBrainsMono,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                // Details
                Column {
                    Text(
                        text = event.title.uppercase(),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    if (event.subtitle.isNotBlank()) {
                        Text(
                            text = event.subtitle,
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(importanceColor.copy(alpha = 0.12f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = event.importance.name,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = importanceColor
                        )
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(36.dp)
                        .semantics { contentDescription = "Delete event ${event.title}" }
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}

@Composable
fun EventDetailScreen(
    viewModel: ScheduleViewModel,
    onBack: () -> Unit
) {
    val event by viewModel.selectedEvent.collectAsStateWithLifecycle()
    val accessState = LocalAccessibilityState.current

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
                    viewModel.clearSelection()
                    onBack()
                },
                modifier = Modifier.semantics { contentDescription = "Back button" }
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "EVENT DETAIL",
                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (event == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Event not found", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            val e = event!!
            val importanceColor = when (e.importance) {
                Importance.CRITICAL -> StatusColors.error(accessState.colorBlindMode)
                Importance.ROUTINE -> MaterialTheme.colorScheme.primary
                Importance.MAINTENANCE -> MaterialTheme.colorScheme.onSurfaceVariant
            }

            // Header Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, importanceColor.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Event,
                            contentDescription = null,
                            tint = importanceColor,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = e.title.uppercase(),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    if (e.subtitle.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = e.subtitle,
                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(importanceColor.copy(alpha = 0.12f))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                e.importance.name,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = importanceColor)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                e.type,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Time Block
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("START", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                        Text(
                            e.startTime,
                            style = MaterialTheme.typography.titleMedium.copy(fontFamily = JetBrainsMono, fontWeight = FontWeight.Bold)
                        )
                    }
                    Icon(Icons.Default.ArrowForward, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Column(horizontalAlignment = Alignment.End) {
                        Text("END", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                        Text(
                            e.endTime,
                            style = MaterialTheme.typography.titleMedium.copy(fontFamily = JetBrainsMono, fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Status
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        if (e.isCompleted) Icons.Default.CheckCircle else Icons.Default.Schedule,
                        contentDescription = null,
                        tint = if (e.isCompleted) StatusColors.success(accessState.colorBlindMode) else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = if (e.isCompleted) "COMPLETED" else "PENDING",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}

@Composable
fun AddEventScreen(
    viewModel: ScheduleViewModel,
    onSuccess: () -> Unit,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("ROUTINE") }
    var importance by remember { mutableStateOf(Importance.ROUTINE) }
    var error by remember { mutableStateOf<String?>(null) }

    val accessState = LocalAccessibilityState.current
    val fieldHeight = if (accessState.touchTargetScaling) 60.dp else 52.dp
    val buttonHeight = if (accessState.touchTargetScaling) 60.dp else 48.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(28.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack, modifier = Modifier.semantics { contentDescription = "Back" }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("ADD EVENT", style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold))
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f), RoundedCornerShape(24.dp))
                .padding(24.dp)
        ) {
            Column {
                OutlinedTextField(
                    value = title, onValueChange = { title = it; error = null },
                    label = { Text("EVENT TITLE") },
                    modifier = Modifier.fillMaxWidth().height(fieldHeight),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = subtitle, onValueChange = { subtitle = it },
                    label = { Text("SUBTITLE (optional)") },
                    modifier = Modifier.fillMaxWidth().height(fieldHeight),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = startTime, onValueChange = { startTime = it; error = null },
                        label = { Text("START (e.g. 2:00 PM)") },
                        modifier = Modifier.weight(1f).height(fieldHeight),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = endTime, onValueChange = { endTime = it; error = null },
                        label = { Text("END (e.g. 4:00 PM)") },
                        modifier = Modifier.weight(1f).height(fieldHeight),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("IMPORTANCE LEVEL", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Importance.entries.forEach { imp ->
                        val selected = importance == imp
                        val color = when (imp) {
                            Importance.CRITICAL -> StatusColors.error(accessState.colorBlindMode)
                            Importance.ROUTINE -> MaterialTheme.colorScheme.primary
                            Importance.MAINTENANCE -> MaterialTheme.colorScheme.onSurfaceVariant
                        }
                        FilterChip(
                            selected = selected,
                            onClick = { importance = imp; type = imp.name },
                            label = { Text(imp.name, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = color.copy(alpha = 0.15f),
                                selectedLabelColor = color
                            )
                        )
                    }
                }

                error?.let { err ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(err, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (title.isBlank() || startTime.isBlank() || endTime.isBlank()) {
                            error = "Title, start time, and end time are mandatory."
                        } else {
                            viewModel.createEvent(title, subtitle, startTime, endTime, type, importance)
                            onSuccess()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.fillMaxWidth().height(buttonHeight),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("SCHEDULE EVENT", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary))
                }
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
private fun ChronoBlockRow(block: ChronoBlock) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 2.dp,
                color = block.color,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(block.color)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = block.label,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = block.timeRange,
                style = MaterialTheme.typography.labelMedium.copy(fontFamily = JetBrainsMono),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        if (block.locked) {
            Icon(
                Icons.Default.Lock,
                contentDescription = "Locked block",
                tint = MentorColors.WarningBurnedOrange
            )
        }
    }
}

package com.example.myapplication.presentation.features.habits

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
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.core.accessibility.LocalAccessibilityState
import com.example.myapplication.domain.model.Habit
import com.example.myapplication.domain.model.HabitType
import com.example.myapplication.ui.theme.JetBrainsMono
import com.example.myapplication.ui.theme.StatusColors

@Composable
fun HabitBuilderScreen(
    viewModel: HabitsViewModel,
    onNavigateToAddHabit: () -> Unit,
    onBack: () -> Unit
) {
    val habitsList by viewModel.habits.collectAsStateWithLifecycle()
    val goodHabits = habitsList.filter { it.type == HabitType.GOOD }
    val accessState = LocalAccessibilityState.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddHabit,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.semantics {
                    contentDescription = "Construct new custom habit builder"
                }
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
                Text(
                    text = "HABIT BUILDERS",
                    style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (goodHabits.isEmpty()) {
                Box(modifier = Modifier.fillGridFull(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No good habits configured. Build habits by tapping the action button below.",
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(goodHabits, key = { it.id }) { habit ->
                        HabitItemCard(
                            habit = habit,
                            onCheckChange = { viewModel.checkOffHabit(habit) },
                            onDelete = { viewModel.deleteHabit(habit.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HabitBreakerScreen(
    viewModel: HabitsViewModel,
    onNavigateToAddHabit: () -> Unit,
    onBack: () -> Unit
) {
    val habitsList by viewModel.habits.collectAsStateWithLifecycle()
    val badHabits = habitsList.filter { it.type == HabitType.BAD }
    val accessState = LocalAccessibilityState.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddHabit,
                containerColor = Color(0xFFEF4444),
                contentColor = Color.White,
                modifier = Modifier.semantics {
                    contentDescription = "Construct new custom bad habit breaker"
                }
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
                Text(
                    text = "HABIT BREAKERS",
                    style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFFEF4444))
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (badHabits.isEmpty()) {
                Box(modifier = Modifier.fillGridFull(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No bad habits monitored. Keep discipline high by creating rules to break bad loops.",
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(badHabits, key = { it.id }) { habit ->
                        HabitItemCard(
                            habit = habit,
                            onCheckChange = { viewModel.checkOffHabit(habit) },
                            onDelete = { viewModel.deleteHabit(habit.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HabitItemCard(
    habit: Habit,
    onCheckChange: () -> Unit,
    onDelete: () -> Unit
) {
    val accessState = LocalAccessibilityState.current
    val strokeColor = if (habit.isCompleted) {
        if (habit.type == HabitType.GOOD) Color(0xFF10B981) else Color(0xFFEF4444)
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)
    }
    
    val accessibilityDesc = "${habit.name}. Streak is ${habit.streak} days. Category is ${habit.category}. Status: ${if (habit.isCompleted) "Completed today" else "Pending completion"}"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, strokeColor, RoundedCornerShape(16.dp))
            .padding(16.dp)
            .semantics {
                contentDescription = accessibilityDesc
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Checkbox(
                    checked = habit.isCompleted,
                    onCheckedChange = { onCheckChange() },
                    colors = CheckboxDefaults.colors(
                        checkedColor = if (habit.type == HabitType.GOOD) Color(0xFF10B981) else Color(0xFFEF4444)
                    ),
                    modifier = Modifier.semantics {
                        contentDescription = "Check off ${habit.name}"
                    }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = habit.name.uppercase(),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "GOAL: ${habit.goal}",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Streak Indicator
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${habit.streak} STREAK",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = JetBrainsMono,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.semantics { contentDescription = "Delete habit ${habit.name}" }
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
fun AddHabitScreen(
    viewModel: HabitsViewModel,
    onSuccess: () -> Unit,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var goal by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(HabitType.GOOD) }
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
            IconButton(
                onClick = onBack,
                modifier = Modifier.semantics { contentDescription = "Back button" }
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "ADD RULE",
                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Frosted Card Form
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
                    value = name,
                    onValueChange = { name = it; error = null },
                    label = { Text("HABIT RULE NAME") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(fieldHeight)
                        .semantics { contentDescription = "Enter habit rule name" },
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it; error = null },
                    label = { Text("CATEGORY TAG (e.g. FOCUS, HEALTH)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(fieldHeight)
                        .semantics { contentDescription = "Enter category tag" },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = goal,
                    onValueChange = { goal = it; error = null },
                    label = { Text("DIFFICULTY GOAL DESCRIPTION") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(fieldHeight)
                        .semantics { contentDescription = "Enter difficulty goal description" },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "RULE DESIGNATION",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { type = HabitType.GOOD }
                            .padding(vertical = 8.dp)
                    ) {
                        RadioButton(selected = type == HabitType.GOOD, onClick = { type = HabitType.GOOD })
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("GOOD HABIT", style = MaterialTheme.typography.bodyMedium)
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { type = HabitType.BAD }
                            .padding(vertical = 8.dp)
                    ) {
                        RadioButton(
                            selected = type == HabitType.BAD, 
                            onClick = { type = HabitType.BAD },
                            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFEF4444))
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("BAD HABIT", style = MaterialTheme.typography.bodyMedium)
                    }
                }

                error?.let { err ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = err, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (name.isBlank() || category.isBlank() || goal.isBlank()) {
                            error = "All parameters are mandatory to enforce loop sync."
                        } else {
                            viewModel.createHabit(name, category, type, goal)
                            onSuccess()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (type == HabitType.GOOD) MaterialTheme.colorScheme.primary else Color(0xFFEF4444)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(buttonHeight)
                        .semantics {
                            contentDescription = "Save rule to local database"
                        },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "ESTABLISH RULE",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (type == HabitType.GOOD) MaterialTheme.colorScheme.onPrimary else Color.White
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
    }
}

// Helpers
@Composable
fun Modifier.fillGridFull(): Modifier = this
    .fillMaxWidth()
    .fillMaxHeight()
    .padding(24.dp)

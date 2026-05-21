package com.example.myapplication.data

data class Habit(
    val id: String,
    val name: String,
    val category: String,
    val type: HabitType,
    val goal: String,
    val progress: String = "",
    val isCompleted: Boolean = false,
    val streak: Int = 0
)

enum class HabitType {
    GOOD, BAD
}

data class Violation(
    val id: String,
    val title: String,
    val code: String,
    val time: String,
    val date: String,
    val description: String,
    val status: String,
    val isActive: Boolean,
    val severity: Severity = Severity.MEDIUM
)

enum class Severity {
    CRITICAL, MEDIUM, MINOR
}

data class Event(
    val id: String,
    val time: String,
    val startTime: String,
    val endTime: String,
    val title: String,
    val subtitle: String = "",
    val type: String,
    val importance: Importance = Importance.ROUTINE,
    val isCompleted: Boolean = false
)

enum class Importance {
    CRITICAL, ROUTINE, MAINTENANCE
}

data class SystemStats(
    val disciplineScore: Float,
    val currentCycles: Int,
    val bestCycles: Int,
    val activeTimerSeconds: Int,
    val netTimeBalance: Int
)

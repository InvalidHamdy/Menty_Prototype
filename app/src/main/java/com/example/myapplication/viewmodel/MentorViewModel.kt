package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Event
import com.example.myapplication.data.Habit
import com.example.myapplication.data.HabitType
import com.example.myapplication.data.Importance
import com.example.myapplication.data.Severity
import com.example.myapplication.data.Violation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MentorViewModel : ViewModel() {

    // ----------------------------
    // HABITS
    // ----------------------------

    private val _habits = MutableStateFlow(
        listOf(
            Habit(
                id = "1",
                name = "Deep Work",
                category = "Focus",
                type = HabitType.GOOD,
                goal = "4 Hours",
                progress = "2 / 4 Hours",
                streak = 5
            ),

            Habit(
                id = "2",
                name = "Workout",
                category = "Health",
                type = HabitType.GOOD,
                goal = "1 Session",
                progress = "Completed",
                isCompleted = true,
                streak = 12
            ),

            Habit(
                id = "3",
                name = "Social Media",
                category = "Distraction",
                type = HabitType.BAD,
                goal = "0 Usage",
                progress = "45 Minutes"
            )
        )
    )

    val habits: StateFlow<List<Habit>> =
        _habits.asStateFlow()

    // ----------------------------
    // VIOLATIONS
    // ----------------------------

    private val _violations = MutableStateFlow(
        listOf(
            Violation(
                id = "1",
                title = "Instagram Detected",
                code = "V-201",
                time = "10:45 PM",
                date = "Today",
                description = "Distracting app usage detected",
                status = "ACTIVE",
                isActive = true,
                severity = Severity.CRITICAL
            ),

            Violation(
                id = "2",
                title = "Session Interrupted",
                code = "V-105",
                time = "9:10 PM",
                date = "Today",
                description = "Focus session manually terminated",
                status = "RESOLVED",
                isActive = false,
                severity = Severity.MEDIUM
            )
        )
    )

    val violations: StateFlow<List<Violation>> =
        _violations.asStateFlow()

    // ----------------------------
    // EVENTS
    // ----------------------------

    private val _events = MutableStateFlow(
        listOf(
            Event(
                id = "1",
                time = "2:00 PM",
                startTime = "2:00 PM",
                endTime = "4:00 PM",
                title = "Software Engineering Lecture",
                subtitle = "AAST",
                type = "CRITICAL",
                importance = Importance.CRITICAL
            ),

            Event(
                id = "2",
                time = "6:00 PM",
                startTime = "6:00 PM",
                endTime = "7:00 PM",
                title = "Gym Session",
                subtitle = "Workout",
                type = "ROUTINE",
                importance = Importance.ROUTINE
            )
        )
    )

    val events: StateFlow<List<Event>> =
        _events.asStateFlow()

    // ----------------------------
    // TIMER
    // ----------------------------

    private val sessionDuration = 3600

    private val _activeTimerSeconds =
        MutableStateFlow(sessionDuration)

    val activeTimerSeconds: StateFlow<Int> =
        _activeTimerSeconds.asStateFlow()

    // ----------------------------
    // CYCLES
    // ----------------------------

    private val _currentCycles =
        MutableStateFlow(14)

    val currentCycles: StateFlow<Int> =
        _currentCycles.asStateFlow()

    private val _bestCycles =
        MutableStateFlow(28)

    val bestCycles: StateFlow<Int> =
        _bestCycles.asStateFlow()

    // ----------------------------
    // SESSION STATUS
    // ----------------------------

    private val _sessionStatus =
        MutableStateFlow("MONITORING")

    val sessionStatus: StateFlow<String> =
        _sessionStatus.asStateFlow()

    // ----------------------------
    // TIMER RUNNING
    // ----------------------------

    private val _isTimerRunning =
        MutableStateFlow(true)

    val isTimerRunning: StateFlow<Boolean> =
        _isTimerRunning.asStateFlow()

    init {
        startTimer()
    }

    // ----------------------------
    // TIMER LOGIC
    // ----------------------------

    private fun startTimer() {

        viewModelScope.launch {

            while (true) {

                delay(1000)

                if (_isTimerRunning.value) {

                    if (_activeTimerSeconds.value > 0) {

                        _activeTimerSeconds.value -= 1

                    } else {

                        _currentCycles.value += 1

                        if (_currentCycles.value >
                            _bestCycles.value
                        ) {

                            _bestCycles.value =
                                _currentCycles.value
                        }

                        _activeTimerSeconds.value =
                            sessionDuration
                    }
                }
            }
        }
    }

    // ----------------------------
    // SESSION CONTROLS
    // ----------------------------

    fun pauseSession() {
        _isTimerRunning.value = false
        _sessionStatus.value = "BREAK"
    }

    fun resumeSession() {
        _isTimerRunning.value = true
        _sessionStatus.value = "MONITORING"
    }

    fun terminateSession() {
        _isTimerRunning.value = false
        _activeTimerSeconds.value = sessionDuration
        _sessionStatus.value = "IDLE"
    }

    // ----------------------------
    // HABITS
    // ----------------------------

    fun addHabit(habit: Habit) {
        _habits.value =
            _habits.value + habit
    }

    fun removeHabit(id: String) {
        _habits.value =
            _habits.value.filter {
                it.id != id
            }
    }

    // ----------------------------
    // EVENTS
    // ----------------------------

    fun addEvent(event: Event) {
        _events.value =
            _events.value + event
    }

    fun removeEvent(id: String) {
        _events.value =
            _events.value.filter {
                it.id != id
            }
    }

    // ----------------------------
    // VIOLATIONS
    // ----------------------------

    fun getViolationById(
        id: String
    ): Violation? {

        return _violations.value.find {
            it.id == id
        }
    }
}
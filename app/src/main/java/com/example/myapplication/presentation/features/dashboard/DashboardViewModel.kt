package com.example.myapplication.presentation.features.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.Event
import com.example.myapplication.domain.model.Habit
import com.example.myapplication.domain.model.HabitType
import com.example.myapplication.domain.model.UserSession
import com.example.myapplication.domain.model.Violation
import com.example.myapplication.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val habits: List<Habit> = emptyList(),
    val violations: List<Violation> = emptyList(),
    val events: List<Event> = emptyList(),
    val activeTimerSeconds: Int = 3600,
    val currentCycles: Int = 14,
    val bestCycles: Int = 28,
    val sessionStatus: String = "MONITORING",
    val isTimerRunning: Boolean = false,
    val session: UserSession = UserSession("", false),
    val currentFocusTitle: String = "Deep Work: System Design",
    val focusProgressPercent: Int = 65
) {
    val disciplineScore: Float
        get() {
            val penalty = violations.count { it.isActive } * 5f +
                habits.count { !it.isCompleted } * 2f
            return (84f - penalty).coerceIn(0f, 100f)
        }
}

@HiltViewModel
class DashboardViewModel @Inject constructor(
    getHabitsUseCase: GetHabitsUseCase,
    getViolationsUseCase: GetViolationsUseCase,
    getEventsUseCase: GetEventsUseCase,
    getTimerStateUseCase: GetTimerStateUseCase,
    getSessionUseCase: GetSessionUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val removeHabitUseCase: RemoveHabitUseCase,
    private val removeEventUseCase: RemoveEventUseCase
) : ViewModel() {

    private val combinedData = combine(
        getHabitsUseCase(),
        getViolationsUseCase(),
        getEventsUseCase()
    ) { habits, violations, events ->
        Triple(habits, violations, events)
    }

    private val combinedTimer = combine(
        getTimerStateUseCase.activeTimerSeconds(),
        getTimerStateUseCase.currentCycles(),
        getTimerStateUseCase.bestCycles(),
        getTimerStateUseCase.sessionStatus(),
        getTimerStateUseCase.isTimerRunning()
    ) { timer, current, best, status, running ->
        TimerInfo(timer, current, best, status, running)
    }

    data class TimerInfo(
        val timer: Int,
        val current: Int,
        val best: Int,
        val status: String,
        val running: Boolean
    )

    val uiState: StateFlow<DashboardUiState> = combine(
        combinedData,
        combinedTimer,
        getSessionUseCase()
    ) { data, timer, session ->
        val focusHabit = data.first.firstOrNull { it.type == HabitType.GOOD && !it.isCompleted }
        DashboardUiState(
            habits = data.first,
            violations = data.second,
            events = data.third,
            activeTimerSeconds = timer.timer,
            currentCycles = timer.current,
            bestCycles = timer.best,
            sessionStatus = timer.status,
            isTimerRunning = timer.running,
            session = session,
            currentFocusTitle = focusHabit?.name?.let { "Deep Work: $it" } ?: "Deep Work: System Design",
            focusProgressPercent = if (data.first.isEmpty()) 65 else {
                val done = data.first.count { it.isCompleted }
                ((done.toFloat() / data.first.size) * 100).toInt().coerceIn(0, 100)
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardUiState()
    )

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    fun deleteHabit(id: String) {
        viewModelScope.launch {
            removeHabitUseCase(id)
        }
    }

    fun deleteEvent(id: String) {
        viewModelScope.launch {
            removeEventUseCase(id)
        }
    }
}

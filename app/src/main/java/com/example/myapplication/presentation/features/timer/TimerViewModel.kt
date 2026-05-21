package com.example.myapplication.presentation.features.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.GetTimerStateUseCase
import com.example.myapplication.domain.usecase.TimerControlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TimerUiState(
    val activeTimerSeconds: Int = 3600,
    val currentCycles: Int = 14,
    val bestCycles: Int = 28,
    val sessionStatus: String = "MONITORING",
    val isTimerRunning: Boolean = false
)

@HiltViewModel
class TimerViewModel @Inject constructor(
    getTimerStateUseCase: GetTimerStateUseCase,
    private val timerControlUseCase: TimerControlUseCase
) : ViewModel() {

    val uiState: StateFlow<TimerUiState> = combine(
        getTimerStateUseCase.activeTimerSeconds(),
        getTimerStateUseCase.currentCycles(),
        getTimerStateUseCase.bestCycles(),
        getTimerStateUseCase.sessionStatus(),
        getTimerStateUseCase.isTimerRunning()
    ) { timer, current, best, status, running ->
        TimerUiState(
            activeTimerSeconds = timer,
            currentCycles = current,
            bestCycles = best,
            sessionStatus = status,
            isTimerRunning = running
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TimerUiState()
    )

    private val _overrideLogs = MutableStateFlow<List<String>>(emptyList())
    val overrideLogs = _overrideLogs.asStateFlow()

    fun pauseSession() {
        viewModelScope.launch {
            timerControlUseCase.pause()
        }
    }

    fun resumeSession() {
        viewModelScope.launch {
            timerControlUseCase.resume()
        }
    }

    fun terminateSession() {
        viewModelScope.launch {
            timerControlUseCase.terminate()
        }
    }

    fun triggerLockdownOverride(reason: String) {
        viewModelScope.launch {
            _overrideLogs.value = _overrideLogs.value + reason
            timerControlUseCase.terminate()
        }
    }
}

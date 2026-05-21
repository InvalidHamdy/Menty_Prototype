package com.example.myapplication.data.repository

import com.example.myapplication.domain.repository.TimerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockTimerRepositoryImpl @Inject constructor() : TimerRepository {
    private val sessionDuration = 3600
    private val _activeTimerSeconds = MutableStateFlow(sessionDuration)
    private val _currentCycles = MutableStateFlow(14)
    private val _bestCycles = MutableStateFlow(28)
    private val _sessionStatus = MutableStateFlow("MONITORING")
    private val _isTimerRunning = MutableStateFlow(true)

    override fun getActiveTimerSeconds(): Flow<Int> = _activeTimerSeconds.asStateFlow()
    override fun getCurrentCycles(): Flow<Int> = _currentCycles.asStateFlow()
    override fun getBestCycles(): Flow<Int> = _bestCycles.asStateFlow()
    override fun getSessionStatus(): Flow<String> = _sessionStatus.asStateFlow()
    override fun isTimerRunning(): Flow<Boolean> = _isTimerRunning.asStateFlow()

    override suspend fun updateTimerSeconds(seconds: Int) {
        _activeTimerSeconds.value = seconds
    }

    override suspend fun updateCycles(current: Int, best: Int) {
        _currentCycles.value = current
        _bestCycles.value = best
    }

    override suspend fun setSessionStatus(status: String) {
        _sessionStatus.value = status
    }

    override suspend fun setTimerRunning(isRunning: Boolean) {
        _isTimerRunning.value = isRunning
    }
}

package com.example.myapplication.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeTimerRepository : TimerRepository {
    private val _seconds = MutableStateFlow(3600)
    private val _currentCycles = MutableStateFlow(0)
    private val _bestCycles = MutableStateFlow(0)
    private val _status = MutableStateFlow("IDLE")
    private val _running = MutableStateFlow(false)

    override fun getActiveTimerSeconds(): Flow<Int> = _seconds.asStateFlow()
    override fun getCurrentCycles(): Flow<Int> = _currentCycles.asStateFlow()
    override fun getBestCycles(): Flow<Int> = _bestCycles.asStateFlow()
    override fun getSessionStatus(): Flow<String> = _status.asStateFlow()
    override fun isTimerRunning(): Flow<Boolean> = _running.asStateFlow()

    override suspend fun updateTimerSeconds(seconds: Int) { _seconds.value = seconds }
    override suspend fun updateCycles(current: Int, best: Int) {
        _currentCycles.value = current
        _bestCycles.value = best
    }
    override suspend fun setSessionStatus(status: String) { _status.value = status }
    override suspend fun setTimerRunning(isRunning: Boolean) { _running.value = isRunning }
}

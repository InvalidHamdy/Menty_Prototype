package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.*
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getSession(): Flow<UserSession>
    suspend fun login(username: String, pass: String): Result<UserSession>
    suspend fun signUp(username: String, pass: String): Result<UserSession>
    suspend fun logout()
}

interface HabitRepository {
    fun getHabits(): Flow<List<Habit>>
    suspend fun addHabit(habit: Habit)
    suspend fun removeHabit(id: String)
}

interface ViolationRepository {
    fun getViolations(): Flow<List<Violation>>
    suspend fun getViolationById(id: String): Violation?
    suspend fun addViolation(violation: Violation)
}

interface EventRepository {
    fun getEvents(): Flow<List<Event>>
    suspend fun addEvent(event: Event)
    suspend fun removeEvent(id: String)
}

interface TimerRepository {
    fun getActiveTimerSeconds(): Flow<Int>
    fun getCurrentCycles(): Flow<Int>
    fun getBestCycles(): Flow<Int>
    fun getSessionStatus(): Flow<String>
    fun isTimerRunning(): Flow<Boolean>
    
    suspend fun updateTimerSeconds(seconds: Int)
    suspend fun updateCycles(current: Int, best: Int)
    suspend fun setSessionStatus(status: String)
    suspend fun setTimerRunning(isRunning: Boolean)
}

interface AccessibilityRepository {
    fun getAccessibilityState(): Flow<AccessibilityState>
    suspend fun updateAccessibilityState(state: AccessibilityState)
}

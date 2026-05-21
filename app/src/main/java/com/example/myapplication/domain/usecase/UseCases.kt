package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.*
import com.example.myapplication.domain.repository.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// ----------------------------
// AUTH USE CASES
// ----------------------------
class LoginUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, pass: String): Result<UserSession> =
        repository.login(username, pass)
}

class SignUpUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, pass: String): Result<UserSession> =
        repository.signUp(username, pass)
}

class GetSessionUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(): Flow<UserSession> = repository.getSession()
}

class LogoutUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.logout()
}

// ----------------------------
// HABIT USE CASES
// ----------------------------
class GetHabitsUseCase @Inject constructor(private val repository: HabitRepository) {
    operator fun invoke(): Flow<List<Habit>> = repository.getHabits()
}

class AddHabitUseCase @Inject constructor(private val repository: HabitRepository) {
    suspend operator fun invoke(habit: Habit) = repository.addHabit(habit)
}

class RemoveHabitUseCase @Inject constructor(private val repository: HabitRepository) {
    suspend operator fun invoke(id: String) = repository.removeHabit(id)
}

// ----------------------------
// VIOLATION USE CASES
// ----------------------------
class GetViolationsUseCase @Inject constructor(private val repository: ViolationRepository) {
    operator fun invoke(): Flow<List<Violation>> = repository.getViolations()
}

class GetViolationDetailUseCase @Inject constructor(private val repository: ViolationRepository) {
    suspend operator fun invoke(id: String): Violation? = repository.getViolationById(id)
}

class AddViolationUseCase @Inject constructor(private val repository: ViolationRepository) {
    suspend operator fun invoke(violation: Violation) = repository.addViolation(violation)
}

// ----------------------------
// EVENT USE CASES
// ----------------------------
class GetEventsUseCase @Inject constructor(private val repository: EventRepository) {
    operator fun invoke(): Flow<List<Event>> = repository.getEvents()
}

class AddEventUseCase @Inject constructor(private val repository: EventRepository) {
    suspend operator fun invoke(event: Event) = repository.addEvent(event)
}

class RemoveEventUseCase @Inject constructor(private val repository: EventRepository) {
    suspend operator fun invoke(id: String) = repository.removeEvent(id)
}

// ----------------------------
// TIMER USE CASES
// ----------------------------
class GetTimerStateUseCase @Inject constructor(private val repository: TimerRepository) {
    fun activeTimerSeconds(): Flow<Int> = repository.getActiveTimerSeconds()
    fun currentCycles(): Flow<Int> = repository.getCurrentCycles()
    fun bestCycles(): Flow<Int> = repository.getBestCycles()
    fun sessionStatus(): Flow<String> = repository.getSessionStatus()
    fun isTimerRunning(): Flow<Boolean> = repository.isTimerRunning()
}

class TimerControlUseCase @Inject constructor(private val repository: TimerRepository) {
    suspend fun pause() {
        repository.setTimerRunning(false)
        repository.setSessionStatus("BREAK")
    }

    suspend fun resume() {
        repository.setTimerRunning(true)
        repository.setSessionStatus("MONITORING")
    }

    suspend fun terminate() {
        repository.setTimerRunning(false)
        repository.updateTimerSeconds(3600)
        repository.setSessionStatus("IDLE")
    }

    suspend fun updateSeconds(seconds: Int) = repository.updateTimerSeconds(seconds)
    suspend fun updateCycles(current: Int, best: Int) = repository.updateCycles(current, best)
}

// ----------------------------
// ACCESSIBILITY USE CASES
// ----------------------------
class GetAccessibilitySettingsUseCase @Inject constructor(private val repository: AccessibilityRepository) {
    operator fun invoke(): Flow<AccessibilityState> = repository.getAccessibilityState()
}

class UpdateAccessibilitySettingsUseCase @Inject constructor(private val repository: AccessibilityRepository) {
    suspend operator fun invoke(state: AccessibilityState) = repository.updateAccessibilityState(state)
}

package com.example.myapplication.data.repository

import com.example.myapplication.data.Habit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * In-memory fake for unit tests. No Room, no Android context needed.
 */
class FakeHabitRepository : HabitRepository {
    private val _habits = MutableStateFlow<List<Habit>>(emptyList())

    override fun getHabits(): Flow<List<Habit>> = _habits.asStateFlow()

    override suspend fun addHabit(habit: Habit) {
        _habits.value = _habits.value + habit
    }

    override suspend fun removeHabit(id: String) {
        _habits.value = _habits.value.filter { it.id != id }
    }

    /** Test helper — seed data without going through the public API. */
    fun seed(habits: List<Habit>) {
        _habits.value = habits
    }
}

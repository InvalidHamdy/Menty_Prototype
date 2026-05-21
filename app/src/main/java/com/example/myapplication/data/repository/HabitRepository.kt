package com.example.myapplication.data.repository

import com.example.myapplication.data.local.dao.HabitDao
import com.example.myapplication.data.local.entities.HabitEntity
import com.example.myapplication.domain.model.Habit
import com.example.myapplication.domain.model.HabitType
import com.example.myapplication.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomHabitRepositoryImpl @Inject constructor(
    private val habitDao: HabitDao
) : HabitRepository {

    override fun getHabits(): Flow<List<Habit>> = habitDao.getHabits().map { entities ->
        entities.map { it.toDomain() }
    }

    override suspend fun addHabit(habit: Habit) {
        habitDao.insertHabit(habit.toEntity())
    }

    override suspend fun removeHabit(id: String) {
        habitDao.deleteHabit(id)
    }
}

// Mappers
fun HabitEntity.toDomain() = Habit(
    id = id,
    name = name,
    category = category,
    type = try {
        HabitType.valueOf(type.name)
    } catch(e: Exception) {
        HabitType.GOOD
    },
    goal = goal,
    progress = progress,
    isCompleted = isCompleted,
    streak = streak
)

fun Habit.toEntity() = HabitEntity(
    id = id,
    name = name,
    category = category,
    type = try {
        com.example.myapplication.data.HabitType.valueOf(type.name)
    } catch(e: Exception) {
        com.example.myapplication.data.HabitType.GOOD
    },
    goal = goal,
    progress = progress,
    isCompleted = isCompleted,
    streak = streak
)

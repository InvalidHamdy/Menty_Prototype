package com.example.myapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.local.entities.EventEntity
import com.example.myapplication.data.local.entities.HabitEntity
import com.example.myapplication.data.local.entities.ViolationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getHabits(): Flow<List<HabitEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity)

    @Query("DELETE FROM habits WHERE id = :id")
    suspend fun deleteHabit(id: String)
}

@Dao
interface ViolationDao {
    @Query("SELECT * FROM violations")
    fun getViolations(): Flow<List<ViolationEntity>>

    @Query("SELECT * FROM violations WHERE id = :id")
    suspend fun getViolationById(id: String): ViolationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertViolation(violation: ViolationEntity)
}

@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    fun getEvents(): Flow<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Query("DELETE FROM events WHERE id = :id")
    suspend fun deleteEvent(id: String)
}

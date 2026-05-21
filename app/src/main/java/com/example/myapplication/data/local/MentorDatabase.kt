package com.example.myapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.data.local.dao.EventDao
import com.example.myapplication.data.local.dao.HabitDao
import com.example.myapplication.data.local.dao.ViolationDao
import com.example.myapplication.data.local.entities.EventEntity
import com.example.myapplication.data.local.entities.HabitEntity
import com.example.myapplication.data.local.entities.ViolationEntity

@Database(
    entities = [HabitEntity::class, ViolationEntity::class, EventEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MentorDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun violationDao(): ViolationDao
    abstract fun eventDao(): EventDao
}

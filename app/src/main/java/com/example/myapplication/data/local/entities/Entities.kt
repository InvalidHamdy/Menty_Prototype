package com.example.myapplication.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.data.HabitType
import com.example.myapplication.data.Importance
import com.example.myapplication.data.Severity

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val category: String,
    val type: HabitType,
    val goal: String,
    val progress: String,
    val isCompleted: Boolean,
    val streak: Int
)

@Entity(tableName = "violations")
data class ViolationEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val code: String,
    val time: String,
    val date: String,
    val description: String,
    val status: String,
    val isActive: Boolean,
    val severity: Severity
)

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey
    val id: String,
    val time: String,
    val startTime: String,
    val endTime: String,
    val title: String,
    val subtitle: String,
    val type: String,
    val importance: Importance,
    val isCompleted: Boolean
)

package com.example.myapplication.data.local

import androidx.room.TypeConverter
import com.example.myapplication.data.HabitType
import com.example.myapplication.data.Importance
import com.example.myapplication.data.Severity

class Converters {
    @TypeConverter
    fun fromHabitType(value: HabitType): String = value.name

    @TypeConverter
    fun toHabitType(value: String): HabitType = enumValueOf(value)

    @TypeConverter
    fun fromSeverity(value: Severity): String = value.name

    @TypeConverter
    fun toSeverity(value: String): Severity = enumValueOf(value)

    @TypeConverter
    fun fromImportance(value: Importance): String = value.name

    @TypeConverter
    fun toImportance(value: String): Importance = enumValueOf(value)
}

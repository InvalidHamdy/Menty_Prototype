package com.example.myapplication.data.repository

import com.example.myapplication.data.*
import com.example.myapplication.data.local.entities.EventEntity
import com.example.myapplication.data.local.entities.HabitEntity
import com.example.myapplication.data.local.entities.ViolationEntity
import org.junit.Assert.*
import org.junit.Test

/**
 * Verifies that domain ↔ entity mappers are perfectly symmetric:
 *   domain → entity → domain  ==  original domain object
 */
class MapperTest {

    @Test
    fun `Habit round-trips through HabitEntity`() {
        val original = Habit(
            id = "h1", name = "Read", category = "Learning",
            type = HabitType.GOOD, goal = "30 min",
            progress = "15 min", isCompleted = false, streak = 7
        )
        val entity = original.toEntity()
        val restored = entity.toDomain()

        assertEquals(original, restored)
    }

    @Test
    fun `Violation round-trips through ViolationEntity`() {
        val original = Violation(
            id = "v1", title = "Phone Usage", code = "V-001",
            time = "14:30", date = "2026-05-20",
            description = "Used phone", status = "ACTIVE",
            isActive = true, severity = Severity.CRITICAL
        )
        val entity = original.toEntity()
        val restored = entity.toDomain()

        assertEquals(original, restored)
    }

    @Test
    fun `Event round-trips through EventEntity`() {
        val original = Event(
            id = "e1", time = "09:00", startTime = "09:00", endTime = "10:00",
            title = "Standup", subtitle = "Daily", type = "Meeting",
            importance = Importance.CRITICAL, isCompleted = false
        )
        val entity = original.toEntity()
        val restored = entity.toDomain()

        assertEquals(original, restored)
    }

    @Test
    fun `HabitEntity fields map correctly`() {
        val habit = Habit(
            id = "h2", name = "Meditate", category = "Wellness",
            type = HabitType.GOOD, goal = "10 min",
            progress = "5 min", isCompleted = true, streak = 12
        )
        val entity = habit.toEntity()

        assertEquals("h2", entity.id)
        assertEquals("Meditate", entity.name)
        assertEquals("Wellness", entity.category)
        assertEquals(HabitType.GOOD, entity.type)
        assertEquals("10 min", entity.goal)
        assertEquals("5 min", entity.progress)
        assertTrue(entity.isCompleted)
        assertEquals(12, entity.streak)
    }

    @Test
    fun `ViolationEntity severity preserves enum`() {
        for (severity in Severity.entries) {
            val v = Violation(
                "v-$severity", "test", "C-001", "00:00", "2026-01-01",
                "desc", "ACTIVE", true, severity
            )
            assertEquals(severity, v.toEntity().toDomain().severity)
        }
    }

    @Test
    fun `EventEntity importance preserves enum`() {
        for (importance in Importance.entries) {
            val e = Event(
                "e-$importance", "09:00", "09:00", "10:00",
                "Test", "sub", "Type", importance, false
            )
            assertEquals(importance, e.toEntity().toDomain().importance)
        }
    }
}

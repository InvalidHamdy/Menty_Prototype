package com.example.myapplication.data.repository

import com.example.myapplication.data.local.dao.EventDao
import com.example.myapplication.data.local.entities.EventEntity
import com.example.myapplication.domain.model.Event
import com.example.myapplication.domain.model.Importance
import com.example.myapplication.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomEventRepositoryImpl @Inject constructor(
    private val eventDao: EventDao
) : EventRepository {

    override fun getEvents(): Flow<List<Event>> = eventDao.getEvents().map { entities ->
        entities.map { it.toDomain() }
    }

    override suspend fun addEvent(event: Event) {
        eventDao.insertEvent(event.toEntity())
    }

    override suspend fun removeEvent(id: String) {
        eventDao.deleteEvent(id)
    }
}

// Mappers
fun EventEntity.toDomain() = Event(
    id = id,
    time = time,
    startTime = startTime,
    endTime = endTime,
    title = title,
    subtitle = subtitle,
    type = type,
    importance = try {
        Importance.valueOf(importance.name)
    } catch(e: Exception) {
        Importance.ROUTINE
    },
    isCompleted = isCompleted
)

fun Event.toEntity() = EventEntity(
    id = id,
    time = time,
    startTime = startTime,
    endTime = endTime,
    title = title,
    subtitle = subtitle,
    type = type,
    importance = try {
        com.example.myapplication.data.Importance.valueOf(importance.name)
    } catch(e: Exception) {
        com.example.myapplication.data.Importance.ROUTINE
    },
    isCompleted = isCompleted
)

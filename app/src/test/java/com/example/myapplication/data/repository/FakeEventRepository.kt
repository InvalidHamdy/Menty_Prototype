package com.example.myapplication.data.repository

import com.example.myapplication.data.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeEventRepository : EventRepository {
    private val _events = MutableStateFlow<List<Event>>(emptyList())

    override fun getEvents(): Flow<List<Event>> = _events.asStateFlow()

    override suspend fun addEvent(event: Event) {
        _events.value = _events.value + event
    }

    override suspend fun removeEvent(id: String) {
        _events.value = _events.value.filter { it.id != id }
    }

    fun seed(events: List<Event>) {
        _events.value = events
    }
}

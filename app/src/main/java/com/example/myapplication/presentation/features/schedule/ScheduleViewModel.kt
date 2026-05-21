package com.example.myapplication.presentation.features.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.Event
import com.example.myapplication.domain.model.Importance
import com.example.myapplication.domain.usecase.AddEventUseCase
import com.example.myapplication.domain.usecase.GetEventsUseCase
import com.example.myapplication.domain.usecase.RemoveEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    getEventsUseCase: GetEventsUseCase,
    private val addEventUseCase: AddEventUseCase,
    private val removeEventUseCase: RemoveEventUseCase
) : ViewModel() {

    val events: StateFlow<List<Event>> = getEventsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _selectedEvent = MutableStateFlow<Event?>(null)
    val selectedEvent = _selectedEvent.asStateFlow()

    fun selectEvent(event: Event) {
        _selectedEvent.value = event
    }

    fun clearSelection() {
        _selectedEvent.value = null
    }

    fun createEvent(
        title: String,
        subtitle: String,
        startTime: String,
        endTime: String,
        type: String,
        importance: Importance
    ) {
        if (title.isBlank()) return
        val event = Event(
            id = UUID.randomUUID().toString(),
            time = startTime,
            startTime = startTime,
            endTime = endTime,
            title = title,
            subtitle = subtitle,
            type = type,
            importance = importance,
            isCompleted = false
        )
        viewModelScope.launch {
            addEventUseCase(event)
        }
    }

    fun deleteEvent(id: String) {
        viewModelScope.launch {
            removeEventUseCase(id)
        }
    }
}

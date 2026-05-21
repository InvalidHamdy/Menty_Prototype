package com.example.myapplication.presentation.features.habits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.Habit
import com.example.myapplication.domain.model.HabitType
import com.example.myapplication.domain.usecase.AddHabitUseCase
import com.example.myapplication.domain.usecase.GetHabitsUseCase
import com.example.myapplication.domain.usecase.RemoveHabitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HabitsViewModel @Inject constructor(
    getHabitsUseCase: GetHabitsUseCase,
    private val addHabitUseCase: AddHabitUseCase,
    private val removeHabitUseCase: RemoveHabitUseCase
) : ViewModel() {

    val habits: StateFlow<List<Habit>> = getHabitsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun createHabit(name: String, category: String, type: HabitType, goal: String) {
        if (name.isBlank() || goal.isBlank()) return
        val habit = Habit(
            id = UUID.randomUUID().toString(),
            name = name,
            category = category,
            type = type,
            goal = goal,
            progress = "0%",
            isCompleted = false,
            streak = 0
        )
        viewModelScope.launch {
            addHabitUseCase(habit)
        }
    }

    fun deleteHabit(id: String) {
        viewModelScope.launch {
            removeHabitUseCase(id)
        }
    }

    fun checkOffHabit(habit: Habit) {
        val updated = habit.copy(
            isCompleted = !habit.isCompleted,
            streak = if (!habit.isCompleted) habit.streak + 1 else maxOf(0, habit.streak - 1),
            progress = if (!habit.isCompleted) "100%" else "0%"
        )
        viewModelScope.launch {
            addHabitUseCase(updated)
        }
    }
}

package com.example.myapplication.presentation.features.violations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.Violation
import com.example.myapplication.domain.usecase.GetViolationDetailUseCase
import com.example.myapplication.domain.usecase.GetViolationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViolationsViewModel @Inject constructor(
    getViolationsUseCase: GetViolationsUseCase,
    private val getViolationDetailUseCase: GetViolationDetailUseCase
) : ViewModel() {

    val violations: StateFlow<List<Violation>> = getViolationsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _selectedViolation = MutableStateFlow<Violation?>(null)
    val selectedViolation = _selectedViolation.asStateFlow()

    fun loadViolationDetails(id: String) {
        viewModelScope.launch {
            _selectedViolation.value = getViolationDetailUseCase(id)
        }
    }

    fun clearDetails() {
        _selectedViolation.value = null
    }
}

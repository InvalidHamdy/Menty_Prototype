package com.example.myapplication.presentation.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.AccessibilityState
import com.example.myapplication.domain.usecase.GetAccessibilitySettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getAccessibilitySettingsUseCase: GetAccessibilitySettingsUseCase
) : ViewModel() {
    val accessibilityState: StateFlow<AccessibilityState> =
        getAccessibilitySettingsUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = AccessibilityState()
            )
}

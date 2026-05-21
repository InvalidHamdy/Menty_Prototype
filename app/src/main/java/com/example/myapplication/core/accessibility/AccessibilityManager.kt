package com.example.myapplication.core.accessibility

import androidx.compose.runtime.compositionLocalOf
import com.example.myapplication.domain.model.AccessibilityState

val LocalAccessibilityState = compositionLocalOf { AccessibilityState() }

package com.example.myapplication.data.repository

import android.content.Context
import com.example.myapplication.domain.model.AccessibilityState
import com.example.myapplication.domain.model.ColorBlindMode
import com.example.myapplication.domain.repository.AccessibilityRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessibilityRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AccessibilityRepository {
    private val prefs = context.getSharedPreferences("accessibility_prefs", Context.MODE_PRIVATE)

    private val _state = MutableStateFlow(loadState())

    override fun getAccessibilityState(): Flow<AccessibilityState> = _state.asStateFlow()

    override suspend fun updateAccessibilityState(state: AccessibilityState) {
        prefs.edit().apply {
            putFloat("fontScale", state.fontScale)
            putBoolean("reduceMotion", state.reduceMotion)
            putBoolean("highContrast", state.highContrast)
            putString("colorBlindMode", state.colorBlindMode.name)
            putBoolean("touchTargetScaling", state.touchTargetScaling)
            apply()
        }
        _state.value = state
    }

    private fun loadState(): AccessibilityState {
        val fontScale = prefs.getFloat("fontScale", 1.0f)
        val reduceMotion = prefs.getBoolean("reduceMotion", false)
        val highContrast = prefs.getBoolean("highContrast", false)
        val modeStr = prefs.getString("colorBlindMode", ColorBlindMode.NONE.name) ?: ColorBlindMode.NONE.name
        val colorBlindMode = try {
            ColorBlindMode.valueOf(modeStr)
        } catch (e: Exception) {
            ColorBlindMode.NONE
        }
        val touchTargetScaling = prefs.getBoolean("touchTargetScaling", false)
        return AccessibilityState(
            fontScale = fontScale,
            reduceMotion = reduceMotion,
            highContrast = highContrast,
            colorBlindMode = colorBlindMode,
            touchTargetScaling = touchTargetScaling
        )
    }
}

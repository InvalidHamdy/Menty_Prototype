package com.example.myapplication.ui.theme

import androidx.compose.ui.graphics.Color

object MentorColors {
    val PrimarySlateBlue = Color(0xFF1A237E)
    val SuccessEmerald = Color(0xFF2E7D32)
    val WarningBurnedOrange = Color(0xFFEF6C00)
    val CriticalCrimson = Color(0xFFC62828)

    val BackgroundLight = Color(0xFFF8F9FA)
    val SurfaceLight = Color(0xFFFFFFFF)
    val OnSurfaceMuted = Color(0xFF37474F)

    val BackgroundDark = Color(0xFF0B0C10)
    val SurfaceDark = Color(0xFF1F2833)
    val SurfaceVariantDark = Color(0xFF2C3E50)

    val LockdownAmbient = Color(0xFF1A0A0A)
    val LockdownCrimsonGlow = Color(0xFFB71C1C)

    fun strictnessBorder(level: StrictnessLevel): Color = when (level) {
        StrictnessLevel.SOFT -> SuccessEmerald
        StrictnessLevel.BALANCED -> Color(0xFF558B2F)
        StrictnessLevel.STRICT -> WarningBurnedOrange
        StrictnessLevel.EXTREME -> CriticalCrimson
    }
}

enum class StrictnessLevel(val label: String, val description: String) {
    SOFT("Soft", "Subtle push updates; delay up to 15 minutes permitted."),
    BALANCED("Balanced", "Interval call interruptions; moderate distraction detection."),
    STRICT("Strict", "Rapid intervention; forced sandboxing; frequent eye-contact prompts."),
    EXTREME("Extreme", "Default lockdown during study blocks; minimal emergency escapes.")
}

enum class InterfaceState {
    NORMAL,
    MENTOR_CALL,
    LOCKDOWN
}

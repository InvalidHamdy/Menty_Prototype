package com.example.myapplication.data

object MockData {
    val habits = listOf(
        Habit("1", "Deep Work Block", "Focus", HabitType.GOOD, "2h 00m", progress = "45m", streak = 12),
        Habit("2", "Hydration Protocol", "Physiology", HabitType.GOOD, "8 L", "4/8 L", streak = 8),
        Habit("3", "Caffeine Intake", "Stimulants", HabitType.BAD, "Max 200mg", streak = 3),
        Habit("4", "Digital Consumption", "Dopamine", HabitType.BAD, "Max 30m", streak = 1)
    )

    val violations = listOf(
        Violation("curfew", "Curfew Breach", "ERR_CURFEW_BREACH", "23:42:01", "2023-10-27", "Subject detected outside designated perimeter. Protocol 7 engaged.", "ACTIVE", true, Severity.CRITICAL),
        Violation("biometric", "Biometric Spike", "WARN_BIOMETRIC_SPIKE", "18:15:22", "2023-10-27", "Elevated stress markers detected during scheduled activity.", "ACTIVE", true, Severity.MEDIUM),
        Violation("deviation", "Deviation Minor", "LOG_DEVIATION_MINOR", "09:04:11", "2023-10-27", "Minor deviation from optimal transit path. Auto-corrected.", "RESOLVED", false, Severity.MINOR),
        Violation("auth", "Auth Failure", "ERR_AUTH_FAILURE", "14:33:05", "2023-10-26", "Failed terminal authentication attempt. Sector 4.", "RESOLVED", false, Severity.MEDIUM),
        Violation("sleep", "Sleep Deprivation", "WARN_SLEEP_DEPRIVATION", "02:15:00", "2023-10-26", "Rest period interrupted. Core function at risk.", "ACTIVE", true, Severity.CRITICAL),
        Violation("nutrition", "Nutrition Miss", "LOG_NUTRITION_MISS", "13:00:00", "2023-10-20", "Scheduled caloric intake missed.", "RESOLVED", false, Severity.MINOR)
    )

    val events = listOf(
        Event("1", "08:00 - 10:00", "08:00", "10:00", "System Diagnostics", "Routine Checkup", "ROUTINE", Importance.ROUTINE, true),
        Event("2", "11:00 - 11:30", "11:00", "11:30", "Enforcement Protocol", "Security Sweep", "CRITICAL", Importance.CRITICAL),
        Event("3", "13:00 - 15:00", "13:00", "15:00", "Data Archive & Backup", "Full Sync", "MAINTENANCE", Importance.MAINTENANCE)
    )
}

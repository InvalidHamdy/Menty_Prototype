package com.example.myapplication.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object StrictnessSetup : Screen("strictness_setup")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Home : Screen("home")
    object FocusSession : Screen("focus_session")
    object HabitBuilder : Screen("habit_builder")
    object HabitBreaker : Screen("habit_breaker")
    object AddHabit : Screen("add_habit")
    object ViolationLog : Screen("violation_log")
    
    object ViolationDetail : Screen("violation_detail/{id}") {
        fun createRoute(id: String) = "violation_detail/$id"
    }
    
    object Schedule : Screen("schedule")
    
    object EventDetail : Screen("event_detail/{id}") {
        fun createRoute(id: String) = "event_detail/$id"
    }
    
    object AddEvent : Screen("add_event")
    object Analytics : Screen("analytics")
    object Settings : Screen("settings")
    object Accessibility : Screen("accessibility")
    object Profile : Screen("profile")
    object Notifications : Screen("notifications")
    object SessionSummary : Screen("session_summary")
    object Lockdown : Screen("lockdown")
    object OverrideLockdown : Screen("override_lockdown")
    object CallOverlay : Screen("call_overlay")
}

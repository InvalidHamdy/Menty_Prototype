package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.screens.AddBadHabitScreen
import com.example.myapplication.ui.screens.AddGoodHabitScreen
import com.example.myapplication.ui.screens.AnalyticsScreen
import com.example.myapplication.ui.screens.CallOverlayScreen
import com.example.myapplication.ui.screens.EventScheduleListScreen
import com.example.myapplication.ui.screens.HabitBreakerScreen
import com.example.myapplication.ui.screens.HabitBuilderScreen
import com.example.myapplication.ui.screens.HomeDashboardScreen
import com.example.myapplication.ui.screens.LockdownScreen
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.OverrideLockdownScreen
import com.example.myapplication.ui.screens.ScheduleScreen
import com.example.myapplication.ui.screens.SettingsScreen
import com.example.myapplication.ui.screens.SignUpScreen
import com.example.myapplication.ui.screens.ViolationDetailsScreen
import com.example.myapplication.ui.screens.ViolationLogScreen
import com.example.myapplication.ui.theme.MentorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MentorTheme {
                MentorAppNavigation()
            }
        }
    }
}

@Composable
fun MentorAppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("home") { popUpTo("login") { inclusive = true } } },
                onNavigateToSignUp = { navController.navigate("signup") }
            )
        }
        composable("signup") {
            SignUpScreen(
                onSignUpSuccess = { navController.navigate("home") { popUpTo("signup") { inclusive = true } } },
                onNavigateToLogin = { navController.navigate("login") { popUpTo("signup") { inclusive = true } } }
            )
        }
        composable("home") {
            HomeDashboardScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo("home") { inclusive = false }
                    }
                }
            )
        }
        composable("schedule") {
            ScheduleScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo("home") { inclusive = false }
                    }
                }
            )
        }
        composable("analytics") {
            AnalyticsScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo("home") { inclusive = false }
                    }
                }
            )
        }
        composable("settings") {
            SettingsScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo("home") { inclusive = false }
                    }
                }
            )
        }
        composable("violation_log") {
            ViolationLogScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo("home") { inclusive = false }
                    }
                }
            )
        }
        composable("event_schedule_list") {
            EventScheduleListScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo("home") { inclusive = false }
                    }
                }
            )
        }
        composable("call_overlay") {
            CallOverlayScreen(
                onNavigate = { route -> navController.navigate(route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable("lockdown") {
            LockdownScreen(
                onNavigateToOverride = { navController.navigate("override_lockdown") }
            )
        }
        composable("override_lockdown") {
            OverrideLockdownScreen(
                onConfirm = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                onCancel = { navController.popBackStack() }
            )
        }
        composable("builder") {
            HabitBuilderScreen(
                onNavigate = { route -> navController.navigate(route) }
            )
        }
        composable("add_good_habit") {
            AddGoodHabitScreen(
                onNavigate = { route -> navController.navigate(route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable("breaker") {
            HabitBreakerScreen(
                onNavigate = { route -> navController.navigate(route) }
            )
        }
        composable("add_bad_habit") {
            AddBadHabitScreen(
                onNavigate = { route -> navController.navigate(route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "violation_details/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            ViolationDetailsScreen(
                violationId = id,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
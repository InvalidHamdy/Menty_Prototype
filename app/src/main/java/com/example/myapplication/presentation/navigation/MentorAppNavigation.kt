package com.example.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.myapplication.domain.model.AccessibilityState
import com.example.myapplication.presentation.features.analytics.AnalyticsScreen
import com.example.myapplication.presentation.features.auth.*
import com.example.myapplication.presentation.features.dashboard.*
import com.example.myapplication.presentation.features.habits.*
import com.example.myapplication.presentation.features.schedule.*
import com.example.myapplication.presentation.features.settings.SettingsScreen
import com.example.myapplication.presentation.features.settings.StrictnessSetupScreen
import com.example.myapplication.presentation.features.timer.*
import com.example.myapplication.presentation.features.violations.*
import com.example.myapplication.ui.theme.InterfaceState
import com.example.myapplication.ui.theme.MentorTheme

@Composable
fun MentorAppNavigation(
    accessibilityState: AccessibilityState = AccessibilityState()
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: Screen.Splash.route

    val interfaceState = when (currentRoute) {
        Screen.Lockdown.route, Screen.OverrideLockdown.route -> InterfaceState.LOCKDOWN
        Screen.CallOverlay.route -> InterfaceState.MENTOR_CALL
        else -> InterfaceState.NORMAL
    }

    val darkTheme = currentRoute == Screen.Lockdown.route ||
        currentRoute == Screen.OverrideLockdown.route ||
        currentRoute == Screen.CallOverlay.route ||
        currentRoute == Screen.FocusSession.route

    MentorTheme(
        accessibilityState = accessibilityState,
        darkTheme = darkTheme,
        interfaceState = interfaceState
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route
        ) {
            composable(Screen.Splash.route) {
                val vm: AuthViewModel = hiltViewModel()
                SplashScreen(
                    viewModel = vm,
                    onNavigateToOnboarding = {
                        navController.navigate(Screen.Onboarding.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    },
                    onNavigateToHome = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.Onboarding.route) {
                OnboardingScreen(
                    onNavigateToSetup = {
                        navController.navigate(Screen.StrictnessSetup.route)
                    }
                )
            }
            composable(Screen.StrictnessSetup.route) {
                StrictnessSetupScreen(
                    onContinue = {
                        navController.navigate(Screen.Login.route)
                    }
                )
            }
            composable(Screen.Login.route) {
                val vm: AuthViewModel = hiltViewModel()
                LoginScreen(
                    viewModel = vm,
                    onLoginSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToSignUp = { navController.navigate(Screen.SignUp.route) }
                )
            }
            composable(Screen.SignUp.route) {
                val vm: AuthViewModel = hiltViewModel()
                SignUpScreen(
                    viewModel = vm,
                    onSignUpSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.SignUp.route) { inclusive = true }
                        }
                    },
                    onNavigateToLogin = { navController.popBackStack() }
                )
            }
            composable(Screen.Home.route) {
                val vm: DashboardViewModel = hiltViewModel()
                HomeDashboardScreen(
                    viewModel = vm,
                    currentRoute = Screen.Home.route,
                    onNavigate = { route -> navController.navigate(route) }
                )
            }
            composable(Screen.FocusSession.route) {
                val vm: TimerViewModel = hiltViewModel()
                FocusSessionScreen(
                    viewModel = vm,
                    onNavigateToSummary = { navController.navigate(Screen.SessionSummary.route) },
                    onNavigateToLockdown = { navController.navigate(Screen.Lockdown.route) },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.SessionSummary.route) {
                val vm: TimerViewModel = hiltViewModel()
                SessionSummaryScreen(
                    viewModel = vm,
                    onBackToHome = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = false }
                        }
                    }
                )
            }
            composable(Screen.CallOverlay.route) {
                CallOverlayScreen(
                    onCommit = { navController.popBackStack() },
                    onPostpone = { navController.popBackStack() },
                    onFailure = { navController.navigate(Screen.Lockdown.route) }
                )
            }
            composable(Screen.Lockdown.route) {
                val vm: TimerViewModel = hiltViewModel()
                LockdownScreen(
                    viewModel = vm,
                    onNavigateToOverride = { navController.navigate(Screen.OverrideLockdown.route) }
                )
            }
            composable(Screen.OverrideLockdown.route) {
                val vm: TimerViewModel = hiltViewModel()
                OverrideLockdownScreen(
                    viewModel = vm,
                    onOverrideSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = false }
                        }
                    }
                )
            }
            composable(Screen.Schedule.route) {
                val vm: ScheduleViewModel = hiltViewModel()
                ScheduleScreen(
                    viewModel = vm,
                    currentRoute = Screen.Schedule.route,
                    onNavigateToAddEvent = { navController.navigate(Screen.AddEvent.route) },
                    onNavigateToEventDetail = { id ->
                        navController.navigate(Screen.EventDetail.createRoute(id))
                    },
                    onNavigate = { route -> navController.navigate(route) },
                    onBack = { navController.navigate(Screen.Home.route) }
                )
            }
            composable(Screen.AddEvent.route) {
                val vm: ScheduleViewModel = hiltViewModel()
                AddEventScreen(
                    viewModel = vm,
                    onSuccess = { navController.popBackStack() },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(
                route = Screen.EventDetail.route,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) {
                val vm: ScheduleViewModel = hiltViewModel()
                EventDetailScreen(
                    viewModel = vm,
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.Analytics.route) {
                val vm: DashboardViewModel = hiltViewModel()
                AnalyticsScreen(
                    viewModel = vm,
                    currentRoute = Screen.Analytics.route,
                    onNavigate = { route -> navController.navigate(route) }
                )
            }
            composable(Screen.Settings.route) {
                SettingsScreen(
                    currentRoute = Screen.Settings.route,
                    onNavigate = { route -> navController.navigate(route) }
                )
            }
            composable(Screen.HabitBuilder.route) {
                val vm: HabitsViewModel = hiltViewModel()
                HabitBuilderScreen(
                    viewModel = vm,
                    onNavigateToAddHabit = { navController.navigate(Screen.AddHabit.route) },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.AddHabit.route) {
                val vm: HabitsViewModel = hiltViewModel()
                AddHabitScreen(
                    viewModel = vm,
                    onSuccess = { navController.popBackStack() },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.HabitBreaker.route) {
                val vm: HabitsViewModel = hiltViewModel()
                HabitBreakerScreen(
                    viewModel = vm,
                    onNavigateToAddHabit = { navController.navigate(Screen.AddHabit.route) },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.ViolationLog.route) {
                val vm: ViolationsViewModel = hiltViewModel()
                ViolationLogScreen(
                    viewModel = vm,
                    onNavigateToDetail = { id ->
                        navController.navigate(Screen.ViolationDetail.createRoute(id))
                    },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(
                route = Screen.ViolationDetail.route,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { entry ->
                val vm: ViolationsViewModel = hiltViewModel()
                val violationId = entry.arguments?.getString("id").orEmpty()
                ViolationDetailScreen(
                    viewModel = vm,
                    violationId = violationId,
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.Profile.route) {
                val vm: DashboardViewModel = hiltViewModel()
                ProfileScreen(
                    viewModel = vm,
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.Notifications.route) {
                val vm: DashboardViewModel = hiltViewModel()
                NotificationsScreen(
                    viewModel = vm,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

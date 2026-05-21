package com.example.myapplication.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.myapplication.R
import com.example.myapplication.core.accessibility.LocalAccessibilityState
import com.example.myapplication.domain.model.AccessibilityState
import com.example.myapplication.domain.model.ColorBlindMode

val LocalInterfaceState = staticCompositionLocalOf { InterfaceState.NORMAL }

val Inter = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_bold, FontWeight.Black)
)

val JetBrainsMono = FontFamily(
    Font(R.font.jetbrains_mono_regular, FontWeight.Normal),
    Font(R.font.jetbrains_mono_bold, FontWeight.Bold),
    Font(R.font.jetbrains_mono_bold, FontWeight.Medium)
)

object StatusColors {
    fun success(mode: ColorBlindMode) = when (mode) {
        ColorBlindMode.PROTANOPIA, ColorBlindMode.DEUTERANOPIA -> MentorColors.PrimarySlateBlue
        else -> MentorColors.SuccessEmerald
    }

    fun warning(mode: ColorBlindMode) = when (mode) {
        ColorBlindMode.TRITANOPIA -> MentorColors.WarningBurnedOrange
        else -> MentorColors.WarningBurnedOrange
    }

    fun error(mode: ColorBlindMode) = when (mode) {
        ColorBlindMode.TRITANOPIA -> MentorColors.CriticalCrimson
        else -> MentorColors.CriticalCrimson
    }
}

private val MentorLightColorScheme = lightColorScheme(
    primary = MentorColors.PrimarySlateBlue,
    secondary = MentorColors.SuccessEmerald,
    tertiary = MentorColors.WarningBurnedOrange,
    background = MentorColors.BackgroundLight,
    surface = MentorColors.SurfaceLight,
    surfaceVariant = MentorColors.BackgroundLight,
    onPrimary = MentorColors.SurfaceLight,
    onSecondary = MentorColors.SurfaceLight,
    onBackground = MentorColors.OnSurfaceMuted,
    onSurface = MentorColors.OnSurfaceMuted,
    onSurfaceVariant = MentorColors.OnSurfaceMuted.copy(alpha = 0.7f),
    error = MentorColors.CriticalCrimson,
    onError = MentorColors.SurfaceLight
)

private val MentorDarkColorScheme = darkColorScheme(
    primary = MentorColors.PrimarySlateBlue,
    secondary = MentorColors.SuccessEmerald,
    tertiary = MentorColors.WarningBurnedOrange,
    background = MentorColors.BackgroundDark,
    surface = MentorColors.SurfaceDark,
    surfaceVariant = MentorColors.SurfaceVariantDark,
    onPrimary = MentorColors.SurfaceLight,
    onBackground = MentorColors.BackgroundLight,
    onSurface = MentorColors.BackgroundLight,
    onSurfaceVariant = MentorColors.BackgroundLight.copy(alpha = 0.7f),
    error = MentorColors.CriticalCrimson,
    onError = MentorColors.SurfaceLight
)

private val HighContrastDarkColorScheme = darkColorScheme(
    primary = MentorColors.PrimarySlateBlue,
    secondary = MentorColors.SuccessEmerald,
    background = MentorColors.BackgroundDark,
    surface = MentorColors.BackgroundDark,
    onBackground = MentorColors.SurfaceLight,
    onSurface = MentorColors.SurfaceLight,
    error = MentorColors.CriticalCrimson,
    onError = MentorColors.BackgroundDark
)

private val HighContrastLightColorScheme = lightColorScheme(
    primary = MentorColors.PrimarySlateBlue,
    background = MentorColors.SurfaceLight,
    surface = MentorColors.SurfaceLight,
    onBackground = MentorColors.BackgroundDark,
    onSurface = MentorColors.BackgroundDark,
    error = MentorColors.CriticalCrimson,
    onError = MentorColors.SurfaceLight
)

@Composable
fun MentorTheme(
    accessibilityState: AccessibilityState = AccessibilityState(),
    darkTheme: Boolean = false,
    interfaceState: InterfaceState = InterfaceState.NORMAL,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        accessibilityState.highContrast ->
            if (darkTheme) HighContrastDarkColorScheme else HighContrastLightColorScheme
        darkTheme || interfaceState == InterfaceState.LOCKDOWN -> MentorDarkColorScheme
        else -> MentorLightColorScheme
    }

    val fontScale = accessibilityState.fontScale
    val mentorTypography = Typography(
        displayLarge = TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.Bold,
            fontSize = (32 * fontScale).sp,
            letterSpacing = (-0.02).sp
        ),
        displayMedium = TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.Bold,
            fontSize = (24 * fontScale).sp,
            letterSpacing = (-0.02).sp
        ),
        headlineMedium = TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.Medium,
            fontSize = (20 * fontScale).sp,
            letterSpacing = (-0.02).sp
        ),
        titleLarge = TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.Bold,
            fontSize = (20 * fontScale).sp
        ),
        titleMedium = TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.Medium,
            fontSize = (18 * fontScale).sp
        ),
        bodyLarge = TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.Normal,
            fontSize = (15 * fontScale).sp
        ),
        bodyMedium = TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.Normal,
            fontSize = (14 * fontScale).sp
        ),
        bodySmall = TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.Normal,
            fontSize = (11 * fontScale).sp
        ),
        labelMedium = TextStyle(
            fontFamily = JetBrainsMono,
            fontWeight = FontWeight.Medium,
            fontSize = (14 * fontScale).sp
        ),
        labelSmall = TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.Bold,
            fontSize = (9 * fontScale).sp
        )
    )

    val statusBarColor = when (interfaceState) {
        InterfaceState.NORMAL -> colorScheme.primary
        InterfaceState.MENTOR_CALL -> MentorColors.SuccessEmerald
        InterfaceState.LOCKDOWN -> MentorColors.CriticalCrimson
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = statusBarColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                interfaceState == InterfaceState.NORMAL && !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalAccessibilityState provides accessibilityState,
        LocalInterfaceState provides interfaceState
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = mentorTypography,
            content = content
        )
    }
}

package com.example.taskapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ─── Colors ───────────────────────────────────────────────────────────────────

val Indigo900  = Color(0xFF1A1040)
val Indigo800  = Color(0xFF251659)
val Indigo600  = Color(0xFF4527A0)
val Violet500  = Color(0xFF7C3AED)
val Violet400  = Color(0xFF9B59F5)
val Mint400    = Color(0xFF34D399)
val Mint300    = Color(0xFF6EE7B7)
val Rose500    = Color(0xFFF43F5E)
val Amber400   = Color(0xFFFBBF24)
val Surface100 = Color(0xFFF5F3FF)
val Surface200 = Color(0xFFEDE9FE)
val OnDark     = Color(0xFFF0EBFF)

private val DarkColors = darkColorScheme(
    primary        = Violet400,
    onPrimary      = Color.White,
    primaryContainer   = Indigo800,
    onPrimaryContainer = Mint300,
    secondary      = Mint400,
    onSecondary    = Indigo900,
    background     = Indigo900,
    onBackground   = OnDark,
    surface        = Indigo800,
    onSurface      = OnDark,
    surfaceVariant = Indigo600,
    error          = Rose500,
    outline        = Violet500
)

private val LightColors = lightColorScheme(
    primary        = Violet500,
    onPrimary      = Color.White,
    primaryContainer   = Surface200,
    onPrimaryContainer = Indigo800,
    secondary      = Mint400,
    onSecondary    = Indigo900,
    background     = Surface100,
    onBackground   = Indigo900,
    surface        = Color.White,
    onSurface      = Indigo900,
    surfaceVariant = Surface200,
    error          = Rose500,
    outline        = Violet500
)

// ─── Typography ───────────────────────────────────────────────────────────────

val AppTypography = androidx.compose.material3.Typography(
    displayLarge = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize   = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = (-1).sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize   = 24.sp,
        lineHeight = 32.sp
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize   = 18.sp,
        lineHeight = 26.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize   = 16.sp,
        lineHeight = 24.sp
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize   = 15.sp,
        lineHeight = 22.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize   = 13.sp,
        lineHeight = 20.sp
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize   = 14.sp,
        letterSpacing = 0.5.sp
    )
)

// ─── Theme ────────────────────────────────────────────────────────────────────

@Composable
fun TaskAppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography  = AppTypography,
        content     = content
    )
}

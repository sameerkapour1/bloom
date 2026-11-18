package io.github.sameerkapour1.bloom.core.designsystem

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/** Light, dark, or follow the system (plan.md §6.3). */
enum class ThemeMode { SYSTEM, LIGHT, DARK }

/**
 * Bloom's theme. The brand scheme is the default; dynamic colour is an opt-in
 * (decision D4) that maps system colours onto our roles while keeping our shape,
 * type and motion. Passing `dynamicColor = false` always yields the Bloom identity.
 */
@Composable
fun BloomTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> BloomDarkColorScheme
        else -> BloomLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = BloomTypography,
        shapes = BloomShapes,
        content = content,
    )
}

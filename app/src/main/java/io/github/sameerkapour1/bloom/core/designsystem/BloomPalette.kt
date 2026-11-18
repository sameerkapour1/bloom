package io.github.sameerkapour1.bloom.core.designsystem

/**
 * Every colour Bloom uses, stored as an ARGB [Long] so the contrast gate
 * (`BLOOM-M1-07`) can be a plain JVM unit test with no Android or Compose
 * dependency.
 *
 * The raw values here are *inputs* to the schemes in Color.kt. Screens must use
 * `MaterialTheme.colorScheme` roles and never these constants directly
 * (plan.md §7). Brand seeds are the brief's palette; the roles below are a
 * derived scheme, not the seeds themselves.
 */
object BloomPalette {
    // Brand seeds — input to the tonal palettes, not usable as roles.
    const val LavenderSeed: Long = 0xFF7651B5
    const val CoralSeed: Long = 0xFFF28B72
    const val MintSeed: Long = 0xFF77B99A
    const val SunSeed: Long = 0xFFF2CC72
    const val SkySeed: Long = 0xFF80B9DC

    // Light scheme roles
    const val LightPrimary: Long = 0xFF5B3F94
    const val LightOnPrimary: Long = 0xFFFFFFFF
    const val LightPrimaryContainer: Long = 0xFFE9DDF8
    const val LightOnPrimaryContainer: Long = 0xFF231046
    const val LightSecondary: Long = 0xFF8B4A38
    const val LightOnSecondary: Long = 0xFFFFFFFF
    const val LightSecondaryContainer: Long = 0xFFFBE1D9
    const val LightOnSecondaryContainer: Long = 0xFF3A1109
    const val LightTertiary: Long = 0xFF2F6A4F
    const val LightOnTertiary: Long = 0xFFFFFFFF
    const val LightBackground: Long = 0xFFF8F5FC
    const val LightOnBackground: Long = 0xFF1C1B1F
    const val LightSurface: Long = 0xFFF3EFF8
    const val LightOnSurface: Long = 0xFF1C1B1F
    const val LightSurfaceVariant: Long = 0xFFE7E0EC
    const val LightOnSurfaceVariant: Long = 0xFF4A4458
    const val LightError: Long = 0xFFBA1A1A
    const val LightOnError: Long = 0xFFFFFFFF
    const val LightOutline: Long = 0xFF79747E

    // Dark scheme roles
    const val DarkPrimary: Long = 0xFFCFBCFF
    const val DarkOnPrimary: Long = 0xFF35215F
    const val DarkPrimaryContainer: Long = 0xFF4B3A78
    const val DarkOnPrimaryContainer: Long = 0xFFE9DDF8
    const val DarkSecondary: Long = 0xFFE7BDB2
    const val DarkOnSecondary: Long = 0xFF442A22
    const val DarkSecondaryContainer: Long = 0xFF5D4037
    const val DarkOnSecondaryContainer: Long = 0xFFFBE1D9
    const val DarkTertiary: Long = 0xFF9CD3B4
    const val DarkOnTertiary: Long = 0xFF04372A
    const val DarkBackground: Long = 0xFF141218
    const val DarkOnBackground: Long = 0xFFE6E1E9
    const val DarkSurface: Long = 0xFF1C1B20
    const val DarkOnSurface: Long = 0xFFE6E1E9
    const val DarkSurfaceVariant: Long = 0xFF48454E
    const val DarkOnSurfaceVariant: Long = 0xFFC9C4D0
    const val DarkError: Long = 0xFFFFB4AB
    const val DarkOnError: Long = 0xFF690005
    const val DarkOutline: Long = 0xFF938F99
}

/** A foreground/background pair whose contrast ratio is asserted in CI. */
data class BloomColorPair(val name: String, val foreground: Long, val background: Long)

/**
 * Every pair where text sits on a filled or tonal surface. Each must reach
 * WCAG AA for body text (4.5:1); see `ContrastTest`.
 */
val bloomBodyColorPairs: List<BloomColorPair> = listOf(
    BloomColorPair("light onPrimary / primary", BloomPalette.LightOnPrimary, BloomPalette.LightPrimary),
    BloomColorPair("light onPrimaryContainer / primaryContainer", BloomPalette.LightOnPrimaryContainer, BloomPalette.LightPrimaryContainer),
    BloomColorPair("light onSecondary / secondary", BloomPalette.LightOnSecondary, BloomPalette.LightSecondary),
    BloomColorPair("light onSecondaryContainer / secondaryContainer", BloomPalette.LightOnSecondaryContainer, BloomPalette.LightSecondaryContainer),
    BloomColorPair("light onTertiary / tertiary", BloomPalette.LightOnTertiary, BloomPalette.LightTertiary),
    BloomColorPair("light onBackground / background", BloomPalette.LightOnBackground, BloomPalette.LightBackground),
    BloomColorPair("light onSurface / surface", BloomPalette.LightOnSurface, BloomPalette.LightSurface),
    BloomColorPair("light onSurfaceVariant / surfaceVariant", BloomPalette.LightOnSurfaceVariant, BloomPalette.LightSurfaceVariant),
    BloomColorPair("light onError / error", BloomPalette.LightOnError, BloomPalette.LightError),
    BloomColorPair("dark onPrimary / primary", BloomPalette.DarkOnPrimary, BloomPalette.DarkPrimary),
    BloomColorPair("dark onPrimaryContainer / primaryContainer", BloomPalette.DarkOnPrimaryContainer, BloomPalette.DarkPrimaryContainer),
    BloomColorPair("dark onSecondary / secondary", BloomPalette.DarkOnSecondary, BloomPalette.DarkSecondary),
    BloomColorPair("dark onSecondaryContainer / secondaryContainer", BloomPalette.DarkOnSecondaryContainer, BloomPalette.DarkSecondaryContainer),
    BloomColorPair("dark onTertiary / tertiary", BloomPalette.DarkOnTertiary, BloomPalette.DarkTertiary),
    BloomColorPair("dark onBackground / background", BloomPalette.DarkOnBackground, BloomPalette.DarkBackground),
    BloomColorPair("dark onSurface / surface", BloomPalette.DarkOnSurface, BloomPalette.DarkSurface),
    BloomColorPair("dark onSurfaceVariant / surfaceVariant", BloomPalette.DarkOnSurfaceVariant, BloomPalette.DarkSurfaceVariant),
    BloomColorPair("dark onError / error", BloomPalette.DarkOnError, BloomPalette.DarkError),
)

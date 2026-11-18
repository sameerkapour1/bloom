package io.github.sameerkapour1.bloom.designsystem

import io.github.sameerkapour1.bloom.core.designsystem.BloomPalette
import io.github.sameerkapour1.bloom.core.designsystem.bloomBodyColorPairs
import java.util.Locale
import kotlin.math.pow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * `BLOOM-M1-07`: the design system's contrast gate.
 *
 * This is deliberately a plain JVM test over ARGB longs rather than a Compose
 * screenshot check, so it runs on every push in the PR gate instead of only on
 * the emulator job. If any pair regresses, the build fails with the ratio.
 */
class ContrastTest {

    @Test
    fun `formula sanity check matches the WCAG reference values`() {
        // White on black is the canonical maximum, 21:1.
        assertEquals(21.0, contrastRatio(0xFFFFFFFF, 0xFF000000), 0.01)
        // A colour against itself is the canonical minimum, 1:1.
        assertEquals(1.0, contrastRatio(BloomPalette.LightPrimary, BloomPalette.LightPrimary), 0.001)
    }

    @Test
    fun `body text pairs meet WCAG AA of 4_5 to 1`() {
        val failures = bloomBodyColorPairs.mapNotNull { pair ->
            val ratio = contrastRatio(pair.foreground, pair.background)
            if (ratio >= BODY_MIN) {
                null
            } else {
                "${pair.name}: ${format(ratio)}:1"
            }
        }

        assertTrue(
            "These foreground/background pairs are below 4.5:1 (plan.md §7):\n" +
                failures.joinToString(separator = "\n"),
            failures.isEmpty(),
        )
    }

    private fun format(value: Double): String = String.format(Locale.ROOT, "%.2f", value)

    /** WCAG 2.x relative luminance of an ARGB colour. */
    private fun luminance(argb: Long): Double {
        val red = channel((argb shr 16) and 0xFF)
        val green = channel((argb shr 8) and 0xFF)
        val blue = channel(argb and 0xFF)
        return 0.2126 * red + 0.7152 * green + 0.0722 * blue
    }

    private fun channel(component: Long): Double {
        val srgb = component / 255.0
        return if (srgb <= 0.03928) {
            srgb / 12.92
        } else {
            ((srgb + 0.055) / 1.055).pow(2.4)
        }
    }

    private fun contrastRatio(foreground: Long, background: Long): Double {
        val first = luminance(foreground)
        val second = luminance(background)
        val lighter = maxOf(first, second)
        val darker = minOf(first, second)
        return (lighter + 0.05) / (darker + 0.05)
    }

    private companion object {
        /** WCAG AA for normal-size body text. */
        const val BODY_MIN = 4.5
    }
}

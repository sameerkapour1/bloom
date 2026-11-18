package io.github.sameerkapour1.bloom.core.designsystem

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontWeight

/**
 * Roboto through the Material 3 type roles (plan.md §7): display and headline
 * for the few hero moments, title for cards and sections, body for explanation,
 * label for controls and metadata. Never bypassed with literal `fontSize`.
 */
private val base = Typography()

internal val BloomTypography = base.copy(
    headlineSmall = base.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
    titleLarge = base.titleLarge.copy(fontWeight = FontWeight.SemiBold),
    titleMedium = base.titleMedium.copy(fontWeight = FontWeight.Medium),
)

package io.github.sameerkapour1.bloom.feature.progress

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.sameerkapour1.bloom.R
import io.github.sameerkapour1.bloom.core.ui.BloomPlaceholderScreen

/**
 * M1 placeholder. Completion rates and streaks arrive in M5 and are computed by
 * the pure domain functions from M3 (plan.md §9).
 */
@Composable
fun ProgressScreen(modifier: Modifier = Modifier) {
    BloomPlaceholderScreen(
        title = stringResource(R.string.nav_progress),
        body = stringResource(R.string.progress_body),
        modifier = modifier,
    )
}

package io.github.sameerkapour1.bloom.feature.goals

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.sameerkapour1.bloom.R
import io.github.sameerkapour1.bloom.core.ui.BloomPlaceholderScreen

/** M1 placeholder. Goals arrive in M5 (BLOOM-M5-03, BLOOM-M5-04). */
@Composable
fun GoalsScreen(modifier: Modifier = Modifier) {
    BloomPlaceholderScreen(
        title = stringResource(R.string.nav_goals),
        body = stringResource(R.string.goals_body),
        modifier = modifier,
    )
}

package io.github.sameerkapour1.bloom.feature.habits

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.sameerkapour1.bloom.R
import io.github.sameerkapour1.bloom.core.ui.BloomPlaceholderScreen

/** M1 placeholder. The habit library arrives in M4 (BLOOM-M4-02). */
@Composable
fun HabitsScreen(modifier: Modifier = Modifier) {
    BloomPlaceholderScreen(
        title = stringResource(R.string.nav_habits),
        body = stringResource(R.string.habits_body),
        modifier = modifier,
    )
}

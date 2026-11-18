package io.github.sameerkapour1.bloom.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.sameerkapour1.bloom.R
import io.github.sameerkapour1.bloom.core.designsystem.BloomSpacing
import io.github.sameerkapour1.bloom.core.ui.BloomPlaceholderScreen

/**
 * M1 placeholder for the Home dashboard. M5 replaces this with today's habits,
 * progress and the empty/all-done states (plan.md §6, BLOOM-M5-01).
 */
@Composable
fun HomeScreen(
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = BloomSpacing.base,
                    end = BloomSpacing.xs,
                    top = BloomSpacing.sm,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.home_title),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f),
            )
            IconButton(onClick = onOpenSettings) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = stringResource(R.string.settings_title),
                )
            }
        }
        BloomPlaceholderScreen(
            title = stringResource(R.string.app_name),
            body = stringResource(R.string.home_body),
            modifier = Modifier.weight(1f),
        )
    }
}

package io.github.sameerkapour1.bloom.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
 * M1 placeholder for Settings. Appearance, language and notification sections
 * arrive in M5 and M6; data export in M8 (plan.md §6).
 */
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = BloomSpacing.xs,
                    end = BloomSpacing.base,
                    top = BloomSpacing.sm,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.action_back),
                )
            }
            Text(
                text = stringResource(R.string.settings_title),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(start = BloomSpacing.sm),
            )
        }
        BloomPlaceholderScreen(
            title = stringResource(R.string.settings_title),
            body = stringResource(R.string.settings_body),
            modifier = Modifier.weight(1f),
        )
    }
}

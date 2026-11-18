package io.github.sameerkapour1.bloom.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.sameerkapour1.bloom.R

/**
 * The four top-level destinations (plan.md decision D5). Settings is reached
 * from the Home header rather than a fifth tab, because five labels crowd a
 * narrow screen and French labels are longer.
 *
 * Routes are plain strings in M1; type-safe routes with arguments arrive in M4
 * with the first screen that needs a parameter.
 */
enum class BloomDestination(
    val route: String,
    @param:StringRes val labelRes: Int,
    val icon: ImageVector,
) {
    HOME("home", R.string.nav_home, Icons.Filled.Home),
    HABITS("habits", R.string.nav_habits, Icons.Filled.List),
    PROGRESS("progress", R.string.nav_progress, Icons.Filled.DateRange),
    GOALS("goals", R.string.nav_goals, Icons.Filled.Star),
}

/** Secondary routes that are not bottom-bar destinations. */
object BloomRoutes {
    const val SETTINGS = "settings"
}

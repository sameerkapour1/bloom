package io.github.sameerkapour1.bloom.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.sameerkapour1.bloom.feature.goals.GoalsScreen
import io.github.sameerkapour1.bloom.feature.habits.HabitsScreen
import io.github.sameerkapour1.bloom.feature.home.HomeScreen
import io.github.sameerkapour1.bloom.feature.progress.ProgressScreen
import io.github.sameerkapour1.bloom.feature.settings.SettingsScreen

/**
 * The navigation shell: one NavHost, four destinations, single Scaffold
 * (plan.md §6). Switching tabs keeps each destination's state and never stacks
 * duplicates on the back stack.
 */
@Composable
fun BloomApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                BloomDestination.entries.forEach { destination ->
                    NavigationBarItem(
                        selected = currentRoute == destination.route,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = null,
                            )
                        },
                        label = { Text(text = stringResource(destination.labelRes)) },
                    )
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BloomDestination.HOME.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(BloomDestination.HOME.route) {
                HomeScreen(onOpenSettings = { navController.navigate(BloomRoutes.SETTINGS) })
            }
            composable(BloomDestination.HABITS.route) { HabitsScreen() }
            composable(BloomDestination.PROGRESS.route) { ProgressScreen() }
            composable(BloomDestination.GOALS.route) { GoalsScreen() }
            composable(BloomRoutes.SETTINGS) {
                SettingsScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}

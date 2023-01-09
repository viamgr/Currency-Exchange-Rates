package app.vahid.base_ui.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.composable(
    screen: Screen,
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    composable(
        route = screen.route,
        arguments = screen.navArguments,
        content = content
    )
}
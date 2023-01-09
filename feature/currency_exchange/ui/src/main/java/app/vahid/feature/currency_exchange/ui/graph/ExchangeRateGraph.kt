package app.vahid.feature.currency_exchange.ui.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import app.vahid.base_ui.common.navigation.ArgumentScreen
import app.vahid.base_ui.common.navigation.Graph
import app.vahid.base_ui.common.navigation.composable
import app.vahid.feature.currency_exchange.ui.R
import app.vahid.feature.currency_exchange.ui.components.template.ExchangerScreen

object ExchangeRateGraph : Graph {
    override val route: String = "main"
    override val startDestination = RateListScreen.route

    object RateListScreen : ArgumentScreen(
        route = "rate-list",
        graph = ExchangeRateGraph,
    ) {
        override fun getLabelResourceId(): Int = R.string.screen_title_rate_list
    }

}

fun NavGraphBuilder.addRateListGraph(
    onNavigateSuccessDialog: (String) -> Unit,
) {

    navigation(
        route = ExchangeRateGraph.route,
        startDestination = ExchangeRateGraph.startDestination,
    ) {

        composable(
            screen = ExchangeRateGraph.RateListScreen
        ) {
            ExchangerScreen(onNavigateSuccessDialog = onNavigateSuccessDialog)
        }

    }
}

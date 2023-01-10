package app.vahid.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import app.vahid.base_ui.common.graph.MessageDialog
import app.vahid.base_ui.common.graph.addCommonGraph
import app.vahid.feature.currency_exchange.ui.graph.ExchangeRateGraph
import app.vahid.feature.currency_exchange.ui.graph.addRateListGraph

@Composable
fun AppNavigator(
    navController: NavHostController = rememberNavController(),
) {
    Surface(Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = ExchangeRateGraph.route,
        ) {
            addRateListGraph {
                navController.navigate(MessageDialog.createRoute(it))
            }

            addCommonGraph {
                navController.navigateUp()
            }
        }
    }

}

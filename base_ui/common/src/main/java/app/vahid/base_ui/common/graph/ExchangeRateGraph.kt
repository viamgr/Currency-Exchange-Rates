package app.vahid.base_ui.common.graph

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import app.vahid.base_ui.common.components.templates.MessageDialogScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


object MessageDialog {

    const val URL_ARGUMENT = "message"
    const val route = "common/message/{$URL_ARGUMENT}"

    val navArguments: List<NamedNavArgument> = listOf(navArgument(URL_ARGUMENT) {
        type = NavType.StringType
    })

    fun createRoute(message: String): String {
        val encodedUrl = URLEncoder.encode(message, StandardCharsets.UTF_8.toString())
        return "common/message/$encodedUrl"
    }

}

fun NavGraphBuilder.addCommonGraph(
) {
    dialog(
        route = MessageDialog.route,
        arguments = MessageDialog.navArguments,
    ) { stackEntry ->
        val message = stackEntry.arguments?.getString(MessageDialog.URL_ARGUMENT)
            .let {
                URLDecoder.decode(it, "utf-8")
            }
            .orEmpty()

        MessageDialogScreen(message = message)
    }
}

@file:OptIn(ExperimentalMaterial3Api::class)

package app.vahid.base_ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AppMaterialTheme(
    content: @Composable () -> Unit,
) {
    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(
        color = Theme.colorScheme.toolbarBg
    )

    CompositionLocalProvider(
        LocalTypography provides typographyValues,
        LocalColorValues provides colorValues,
        LocalDimens provides dimensionValues
    ) {
        MaterialTheme(content = {
            AppScaffold(
                content = content)
        })
    }
}

@Composable
private fun AppScaffold(
    content: @Composable () -> Unit,
) {

    Scaffold(
        contentColor = Theme.colorScheme.primary, modifier = Modifier.padding(),

        content = { paddingValues ->
            Surface(modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(), content = content)
        })
}

object Theme {
    val dimensions: DimensionValues
        @Composable @ReadOnlyComposable get() = LocalDimens.current
    val colorScheme: ColorValues
        @Composable @ReadOnlyComposable get() = LocalColorValues.current
    val typography: TypographyValues
        @Composable @ReadOnlyComposable get() = LocalTypography.current
}


val colorValues = ColorValues()
val dimensionValues = DimensionValues()
val typographyValues: TypographyValues
    @Composable get() = with(MaterialTheme.typography) {
        return TypographyValues(
            labelSmall = bodySmall.copy(color = Theme.colorScheme.toolbarText),
            labelMedium = bodySmall.copy(color = Theme.colorScheme.labelMedium),
            secondaryText = bodySmall.copy(color = Theme.colorScheme.primaryBg),
            bodySmall = bodySmall.copy(color = Theme.colorScheme.primary),
            bodyMedium = bodyMedium.copy(color = Theme.colorScheme.primary),
            bodyMediumReceive = bodyMedium.copy(color = Theme.colorScheme.receive),
            bodyLarge = bodyLarge.copy(color = Theme.colorScheme.primary),
            button = bodyLarge.copy(
                color = Theme.colorScheme.primaryBg
            ),
            confirm = bodyMedium.copy(color = Theme.colorScheme.secondary)
        )
    }


val LocalColorValues = staticCompositionLocalOf {
    ColorValues()
}
val LocalDimens = staticCompositionLocalOf {
    DimensionValues()
}
val LocalTypography = staticCompositionLocalOf<TypographyValues> {
    error("TypographyValues is not provided")
}

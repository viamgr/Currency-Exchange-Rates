package app.vahid.feature.currency_exchange.ui.components.molecule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import app.vahid.base_ui.theme.Theme
import app.vahid.feature.currency_exchange.ui.R

@Composable
fun ToolbarUi() {

    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize()
        .background(Theme.colorScheme.toolbarBg)
    ) {

        Text(
            text = stringResource(R.string.currency_converter),
            style = Theme.typography.labelSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Theme.dimensions.spaceLarge), textAlign = TextAlign.Center)

    }

}
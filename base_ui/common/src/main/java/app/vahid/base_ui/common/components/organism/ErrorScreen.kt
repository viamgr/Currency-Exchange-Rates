package app.vahid.base_ui.common.components.organism

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import app.vahid.base_ui.theme.Theme
import app.vahid.common.presentation.error_handling.IntResError
import app.vahid.common.presentation.error_handling.UiErrorType

@Composable
fun ErrorScreen(
    uiErrorType: UiErrorType?,
    onRetryClicked: () -> Unit = {},
) {
    if (uiErrorType is IntResError) {
        Card(
            modifier = Modifier
                .padding(Theme.dimensions.spaceLarge)
                .clickable { onRetryClicked() }
                .fillMaxWidth()
                .testTag("ErrorScreen"),
            colors = CardDefaults.cardColors(
                containerColor = Theme.colorScheme.sell,
            )

        ) {
            Row(Modifier.padding(Theme.dimensions.spaceLarge),
                verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = uiErrorType.icon),
                    contentDescription = stringResource(id = uiErrorType.message),
                    colorFilter = ColorFilter.tint(Theme.colorScheme.warning)
                )

                Spacer(modifier = Modifier.padding(Theme.dimensions.spaceLarge))

                Text(
                    text = stringResource(id = uiErrorType.title),
                    style = Theme.typography.secondaryText,
                    modifier = Modifier
                        .clickable { onRetryClicked() }
                )
                Spacer(modifier = Modifier.padding(Theme.dimensions.spaceLarge))

            }

            Text(
                text = stringResource(id = uiErrorType.message),
                style = Theme.typography.secondaryText,
                modifier = Modifier
                    .padding(Theme.dimensions.spaceLarge)
            )
        }

    }
}
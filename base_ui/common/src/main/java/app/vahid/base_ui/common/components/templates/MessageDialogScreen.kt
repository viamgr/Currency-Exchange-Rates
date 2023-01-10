package app.vahid.base_ui.common.components.templates

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import app.vahid.base_ui.common.R
import app.vahid.base_ui.theme.Theme

@Composable
fun MessageDialogScreen(message: String, onDismissClicked: () -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = Theme.colorScheme.messageCardBg),
        modifier = Modifier
            .padding(Theme.dimensions.spaceExtraLarge)
            .background(Color.White)
            .wrapContentSize(Alignment.Center)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = stringResource(id = R.string.currency_converted),
                style = Theme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = Theme.dimensions.spaceSmall,
                    top = Theme.dimensions.spaceLarge))

            Text(text = message,
                textAlign = TextAlign.Center,
                style = Theme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = Theme.dimensions.spaceExtraLarge))

            Divider(Modifier.padding(bottom = Theme.dimensions.spaceSmall,
                top = Theme.dimensions.spaceLarge))

            Text(text = stringResource(R.string.done),
                textAlign = TextAlign.Center,
                style = Theme.typography.confirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Theme.dimensions.spaceLarge)
                    .clickable(onClick = onDismissClicked))
        }
    }
}
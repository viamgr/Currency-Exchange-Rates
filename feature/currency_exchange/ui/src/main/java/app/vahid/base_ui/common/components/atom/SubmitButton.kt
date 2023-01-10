package app.vahid.base_ui.common.components.atom

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.vahid.base_ui.theme.Theme
import app.vahid.feature.currency_exchange.ui.R
import java.util.Locale

@Composable
fun SubmitButton(isSubmitButtonEnabled: Boolean, onSubmitClicked: () -> Unit) {

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Theme.dimensions.spaceExtraLarge),
        onClick = onSubmitClicked,
        colors = ButtonDefaults.buttonColors(
            containerColor = Theme.colorScheme.buttonBg,
            contentColor = Theme.colorScheme.labelMedium,
        ),
        enabled = isSubmitButtonEnabled) {
        Text(
            text = stringResource(R.string.submit).uppercase(Locale.getDefault()),
            style = Theme.typography.button
        )
    }
}
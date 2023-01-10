package app.vahid.feature.currency_exchange.ui.components.molecule

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.vahid.base_ui.common.utils.formatPrice
import app.vahid.base_ui.theme.Theme
import app.vahid.domain.gateway.model.Balance

@Composable
fun BalanceItem(
    onClick: (String) -> Unit,
    it: Balance,
) {
    Row(modifier = Modifier
        .clickable {
            onClick(it.currencyId)
        }
        .padding(horizontal = Theme.dimensions.spaceExtraLarge,
            vertical = Theme.dimensions.spaceMedium)) {

        Text(text = it.amount.formatPrice(), style = Theme.typography.bodyLarge)

        Text(text = it.currencyId, style = Theme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = Theme.dimensions.spaceMedium))

    }
}
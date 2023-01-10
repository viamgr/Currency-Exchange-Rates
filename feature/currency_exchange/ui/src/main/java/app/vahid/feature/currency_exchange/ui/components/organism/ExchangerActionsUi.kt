package app.vahid.feature.currency_exchange.ui.components.organism

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import app.vahid.base_ui.common.utils.formatPrice
import app.vahid.base_ui.theme.Theme
import app.vahid.feature.currency_exchange.ui.R
import app.vahid.feature.currency_exchange.ui.components.molecule.ExchangerActionRow
import java.math.BigDecimal

@Composable
fun ExchangerActionsUi(
    originRateList: List<String>,
    destinationRateList: List<String>,
    selectedDestinationCurrency: String,
    selectedOriginCurrency: String,
    originAmount: BigDecimal = BigDecimal.ZERO,
    destinationAmount: BigDecimal = BigDecimal.ZERO,
    onOriginAmountChanged: (value: String) -> Unit,
    onOriginCurrencyChanged: (String) -> Unit,
    onDestinationCurrencyChanged: (String) -> Unit,
) {
    Column {

        ExchangerActionRow(
            amount = originAmount.toString(),
            currency = selectedOriginCurrency,
            itemList = originRateList,
            label = stringResource(R.string.sell),
            iconTint = Theme.colorScheme.receive,
            onCurrencyChanged = onOriginCurrencyChanged,
            iconRotation = 90F,
            onOriginAmountChanged = onOriginAmountChanged,
            readOnly = false,
            exchangeTypeTextColor = Theme.colorScheme.primary,
        )

        ExchangerActionRow(
            amount = "+ ${destinationAmount.formatPrice()}",
            currency = selectedDestinationCurrency,
            itemList = destinationRateList,
            label = stringResource(R.string.receive),
            iconTint = Theme.colorScheme.sell,
            onCurrencyChanged = onDestinationCurrencyChanged,
            iconRotation = 270F,
            readOnly = true,
            exchangeTypeTextColor = Theme.colorScheme.receive
        )
    }
}
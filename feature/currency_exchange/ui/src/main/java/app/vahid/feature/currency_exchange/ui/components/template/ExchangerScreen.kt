@file:OptIn(ExperimentalMaterial3Api::class)

package app.vahid.feature.currency_exchange.ui.components.template

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import app.vahid.base_ui.common.components.organism.DropDownItem
import app.vahid.base_ui.common.components.organism.ErrorScreen
import app.vahid.base_ui.theme.Theme
import app.vahid.common.presentation.dispatchIntent
import app.vahid.common.presentation.error_handling.UiErrorType
import app.vahid.domain.gateway.model.Balance
import app.vahid.feature.currency_exchange.presentation.exchanger.ExchangerViewModel
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerIntent
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerSideEffect
import app.vahid.feature.currency_exchange.ui.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.math.BigDecimal


@Composable
fun ExchangerScreen(
    viewModel: ExchangerViewModel = hiltViewModel(),
    onNavigateSuccessDialog: (String) -> Unit,
) {

    val context = LocalContext.current

    viewModel.collectSideEffect {
        when (it) {
            is ExchangerSideEffect.OnCurrencyConverted -> onNavigateSuccessDialog(
                context.getString(
                    R.string.message_currency_exchange_success,
                    it.originAmount.toString(),
                    it.originCurrency,
                    it.destinationAmount.toString(),
                    it.destinationCurrency,
                    it.originCurrency,
                    it.fee.toString(),
                )
            )
        }
    }

    ExchangerScreen(
        viewModel = viewModel,
        onSubmitClicked = {
            viewModel.dispatchIntent(ExchangerIntent.OnSubmitClicked)
        },
        onOriginAmountChanged = { value ->
            viewModel.dispatchIntent(ExchangerIntent.OnOriginValueUpdated(value))
        },
        onDestinationCurrencyChanged = {
            viewModel.dispatchIntent(ExchangerIntent.OnDestinationCurrencyUpdated(it))
        },
        onOriginCurrencyChanged = {
            viewModel.dispatchIntent(ExchangerIntent.OnOriginCurrencyUpdated(it))
        }
    )
}


@Composable
fun ExchangerScreen(
    viewModel: ExchangerViewModel,
    onSubmitClicked: () -> Unit,
    onOriginAmountChanged: (value: String) -> Unit,
    onDestinationCurrencyChanged: (String) -> Unit,
    onOriginCurrencyChanged: (String) -> Unit,
) {
    val state by viewModel.collectAsState()

    with(state) {
        ExchangerScreen(
            errorType = errorType,
            isLoading = isLoading,
            balanceList = balanceList,
            destinationRateList = destinationRateList,
            originRateList = originRateList,
            originAmount = originAmount,
            selectedOriginCurrency = selectedOriginCurrency,
            selectedDestinationCurrency = selectedDestinationCurrency,
            destinationAmount = destinationAmount,
            isSubmitButtonEnabled = isSubmitButtonEnabled,
            onSubmitClicked = onSubmitClicked,
            onOriginAmountChanged = onOriginAmountChanged,
            onDestinationCurrencyChanged = onDestinationCurrencyChanged,
            onOriginCurrencyChanged = onOriginCurrencyChanged,
        )

    }
}

@Composable
fun ExchangerScreen(
    errorType: UiErrorType? = null,
    isLoading: Boolean = false,
    balanceList: List<Balance> = emptyList(),
    destinationRateList: List<String> = emptyList(),
    originRateList: List<String> = emptyList(),
    originAmount: BigDecimal = BigDecimal.ZERO,
    selectedOriginCurrency: String = "",
    selectedDestinationCurrency: String = "",
    destinationAmount: BigDecimal = BigDecimal.ZERO,
    isSubmitButtonEnabled: Boolean,
    onSubmitClicked: () -> Unit,
    onOriginAmountChanged: (value: String) -> Unit,
    onDestinationCurrencyChanged: (String) -> Unit,
    onOriginCurrencyChanged: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(
            horizontal = Theme.dimensions.spaceLarge,
            vertical = Theme.dimensions.spaceLarge
        )
    ) {

        errorType?.let {
            item {
                ErrorScreen(it)
            }
        }

        item {
            ToolbarUi()
        }

        item {
            Text("My Balances")
        }

        item {
            BalancesListUi(balanceList)
        }

        item {
            Text("Currency Exchange")
        }

        item {

            ExchangerActionsUi(
                originRateList = originRateList,
                destinationRateList = destinationRateList,
                selectedDestinationCurrency = selectedDestinationCurrency,
                selectedOriginCurrency = selectedOriginCurrency,
                originAmount = originAmount,
                destinationAmount = destinationAmount,
                onOriginAmountChanged = onOriginAmountChanged,
                onOriginCurrencyChanged = onOriginCurrencyChanged,
                onDestinationCurrencyChanged = onDestinationCurrencyChanged
            )
        }

        item {
            SubmitButton(isSubmitButtonEnabled && !isLoading, onSubmitClicked)
        }

    }
}

@Composable
fun SubmitButton(isSubmitButtonEnabled: Boolean, onSubmitClicked: () -> Unit) {

    Button(
        onClick = onSubmitClicked,
        enabled = isSubmitButtonEnabled) {
        Text(text = "Submit")
    }
}

@Composable
fun ExchangerActionsUi(
    originRateList: List<String>,
    destinationRateList: List<String>,
    selectedDestinationCurrency: String,
    selectedOriginCurrency: String,
    originAmount: BigDecimal,
    destinationAmount: BigDecimal,
    onOriginAmountChanged: (value: String) -> Unit,
    onOriginCurrencyChanged: (String) -> Unit,
    onDestinationCurrencyChanged: (String) -> Unit,
) {
    Column {

        ExchangerActionRow(
            amount = originAmount,
            currency = selectedOriginCurrency,
            itemList = originRateList, onOriginAmountChanged = onOriginAmountChanged,
            onCurrencyChanged = onOriginCurrencyChanged,
            label = "Sell")

        ExchangerActionRow(
            amount = destinationAmount,
            currency = selectedDestinationCurrency,
            itemList = destinationRateList,
            onCurrencyChanged = onDestinationCurrencyChanged,
            label = "Receive"
        )
    }
}

@Composable
private fun ExchangerActionRow(
    amount: BigDecimal,
    currency: String,
    itemList: List<String>,
    label: String,
    onOriginAmountChanged: (value: String) -> Unit = {},
    onCurrencyChanged: (String) -> Unit,
) {
    Row {
        Image(
            modifier = Modifier.rotate(90F),
            painter = painterResource(id = R.drawable.ic_baseline_arrow_circle_left_24),
            contentDescription = "Up"
        )
        Text(text = label)

        EditText(
            value = amount.toString(),
            readOnly = false,
            onValueChange = onOriginAmountChanged,
            modifier = Modifier.fillMaxWidth(0.4F)
        )

        Text(text = currency)

        DropDownItem(
            items = itemList,
            onItemSelected = {
                onCurrencyChanged(itemList[it])
            },
            title = {
                Text(text = it)
            }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = currency,
                    style = Theme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = Theme.dimensions.spaceLarge),
                )
            }
        }
        Image(
            modifier = Modifier.rotate(180F),
            painter = painterResource(id = R.drawable.ic_baseline_arrow_drop_down_24),
            contentDescription = "Down"
        )
    }
}

@Composable
fun BalancesListUi(balanceList: List<Balance>) {
    LazyRow {
        items(items = balanceList, key = { it.currencyId }) {
            Row {
                Text(text = it.currencyId)
                Text(text = it.amount.toString())
            }
        }
    }
}

@Composable
private fun EditText(
    value: String,
    readOnly: Boolean = false,
    modifier: Modifier = Modifier,
    onValueChange: (value: String) -> Unit = {},
) {

    var text by remember(key1 = value) { mutableStateOf(value) }

    TextField(
        modifier = modifier,
        value = text,
        readOnly = readOnly,
        onValueChange = {
            text = it
            onValueChange(it)
        },
    )

}

@Composable
fun ToolbarUi() {

    Text(text = "toolbar")
}

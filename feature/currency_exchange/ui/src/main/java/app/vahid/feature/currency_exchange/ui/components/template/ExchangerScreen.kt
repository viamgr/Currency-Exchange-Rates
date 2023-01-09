package app.vahid.feature.currency_exchange.ui.components.template

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import app.vahid.base_ui.common.components.organism.DropDownItem
import app.vahid.base_ui.theme.Theme
import app.vahid.common.presentation.error_handling.UiErrorType
import app.vahid.domain.gateway.model.Balance
import app.vahid.feature.currency_exchange.presentation.exchanger.ExchangerViewModel
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerState
import app.vahid.feature.currency_exchange.ui.R
import org.orbitmvi.orbit.compose.collectAsState


@Composable
fun ExchangerScreen(
    viewModel: ExchangerViewModel = hiltViewModel(),
) {
    val state by viewModel.collectAsState()

    ExchangerScreen(state)
}


@Composable
fun ExchangerScreen(
    state: ExchangerState,
) = with(state) {
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
    )
}

@Composable
fun ExchangerScreen(
    errorType: UiErrorType? = null,
    isLoading: Boolean = false,
    balanceList: List<Balance> = emptyList(),
    destinationRateList: List<String> = emptyList(),
    originRateList: List<String> = emptyList(),
    originAmount: Double = 0.0,
    selectedOriginCurrency: String = "",
    selectedDestinationCurrency: String = "",
    destinationAmount: Double = 0.0,
) {
    LazyColumn(
        modifier = Modifier.padding(
            horizontal = Theme.dimensions.spaceLarge,
            vertical = Theme.dimensions.spaceLarge
        )
    ) {

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
                originRateList,
                destinationRateList,
                selectedDestinationCurrency,
                selectedOriginCurrency, originAmount, destinationAmount
            )
        }

        item {
            SubmitButton()
        }

    }
}

@Composable
fun SubmitButton() {

    Button(onClick = { /*TODO*/ }) {
        Text(text = "Submit")
    }
}

@Composable
fun ExchangerActionsUi(
    originRateList: List<String>,
    destinationRateList: List<String>,
    selectedDestinationCurrency: String,
    selectedOriginCurrency: String,
    originAmount: Double,
    destinationAmount: Double,
) {
    Column {

        ExchangerActionRow(originAmount, selectedOriginCurrency, originRateList)

        ExchangerActionRow(destinationAmount, selectedDestinationCurrency, destinationRateList)
    }
}

@Composable
private fun ExchangerActionRow(amount: Double, currency: String, itemList: List<String>) {
    Row {
        Image(
            modifier = Modifier.rotate(90F),
            painter = painterResource(id = R.drawable.ic_baseline_arrow_circle_left_24),
            contentDescription = "Up"
        )
        Text(text = "Sell")
        Text(text = amount.toString())

        Text(text = currency)

        DropDownItem(
            items = itemList,
            onItemSelected = {},
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
fun ToolbarUi() {

    Text(text = "toolbar")
}

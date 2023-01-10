package app.vahid.feature.currency_exchange.ui.components.template

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import app.vahid.base_ui.common.components.atom.HeaderLabel
import app.vahid.base_ui.common.components.atom.SubmitButton
import app.vahid.base_ui.common.components.organism.ErrorScreen
import app.vahid.base_ui.theme.Theme
import app.vahid.common.presentation.dispatchIntent
import app.vahid.common.presentation.error_handling.UiErrorType
import app.vahid.domain.gateway.model.Balance
import app.vahid.feature.currency_exchange.presentation.exchanger.ExchangerViewModel
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerIntent
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerSideEffect
import app.vahid.feature.currency_exchange.ui.R
import app.vahid.feature.currency_exchange.ui.components.molecule.ToolbarUi
import app.vahid.feature.currency_exchange.ui.components.organism.BalancesListUi
import app.vahid.feature.currency_exchange.ui.components.organism.ExchangerActionsUi
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.math.BigDecimal
import java.util.Locale


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
                    it.fee.toString(),
                    it.originCurrency,
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
    Column(Modifier
        .fillMaxSize()
        .background(Theme.colorScheme.primaryBg)) {

        ToolbarUi()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("ExchangerScreenItems")
        ) {

            errorType?.let {
                item {
                    ErrorScreen(it)
                }
            }

            item {
                HeaderLabel(stringResource(R.string.my_balances).uppercase(Locale.getDefault()))
            }

            item {
                BalancesListUi(balanceList, onClick = onOriginCurrencyChanged)
            }

            item {
                HeaderLabel(stringResource(R.string.currency_exchange).uppercase(Locale.getDefault()))
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
}
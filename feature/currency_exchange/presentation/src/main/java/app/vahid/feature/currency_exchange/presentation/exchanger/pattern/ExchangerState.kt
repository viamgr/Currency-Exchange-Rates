package app.vahid.feature.currency_exchange.presentation.exchanger.pattern

import app.vahid.common.presentation.error_handling.UiErrorType
import app.vahid.common.presentation.pattern.State

data class ExchangerState(
    val errorType: UiErrorType? = null,
    val isLoading: Boolean = false,
    val balanceList: List<String> = emptyList(),
    val destinationRateList: List<String> = emptyList(),
    val originRateList: List<String> = emptyList(),
    val originAmount: Double = 0.0,
    val selectedOriginCurrency: String = "",
    val selectedDestinationCurrency: String = "",
    val destinationAmount: Double = 0.0,
) : State

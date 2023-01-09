package app.vahid.feature.currency_exchange.presentation.exchanger.pattern

import app.vahid.common.presentation.error_handling.UiErrorType
import app.vahid.common.presentation.pattern.State
import app.vahid.domain.gateway.model.Balance

data class ExchangerState(
    val errorType: UiErrorType? = null,
    val isLoading: Boolean = true,
    val balanceList: List<Balance> = emptyList(),
    val originAmount: Double = 0.0,
    val destinationAmount: Double = 0.0,
) : State

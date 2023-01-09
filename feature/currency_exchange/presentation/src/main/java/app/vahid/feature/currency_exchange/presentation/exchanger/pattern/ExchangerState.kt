package app.vahid.feature.currency_exchange.presentation.exchanger.pattern

import app.vahid.common.presentation.error_handling.UiErrorType
import app.vahid.common.presentation.pattern.State

data class ExchangerState(
    val errorType: UiErrorType? = null,
    val isLoading: Boolean = true,
) : State

package app.vahid.feature.currency_exchange.presentation.exchanger.pattern

import app.vahid.common.presentation.error_handling.UiErrorType
import app.vahid.common.presentation.pattern.Effect

sealed interface ExchangerEffect : Effect, ExchangerEvent {

    data class OnLoadingStateChanged(val isLoading: Boolean) : ExchangerEffect

    data class OnErrorStateChanged(val uiErrorType: UiErrorType?) : ExchangerEffect

}


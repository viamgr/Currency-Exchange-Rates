package app.vahid.feature.currency_exchange.presentation.exchanger.pattern

import app.vahid.common.presentation.error_handling.UiErrorType
import app.vahid.common.presentation.pattern.Effect
import app.vahid.domain.gateway.model.Balance
import app.vahid.domain.gateway.model.CurrencyRate

sealed interface ExchangerEffect : Effect, ExchangerEvent {

    data class OnLoadingStateChanged(val isLoading: Boolean) : ExchangerEffect

    data class OnErrorStateChanged(val uiErrorType: UiErrorType?) : ExchangerEffect

    data class OnUpdateMyBalances(val balanceList: List<Balance>) : ExchangerEffect

    data class OnUpdateOriginRates(val balanceList: List<CurrencyRate>) : ExchangerEffect

    data class OnUpdateDestinationRates(val balanceList: List<CurrencyRate>) : ExchangerEffect

    data class OnUpdateDestinationValue(val amount: Double) : ExchangerEffect

}


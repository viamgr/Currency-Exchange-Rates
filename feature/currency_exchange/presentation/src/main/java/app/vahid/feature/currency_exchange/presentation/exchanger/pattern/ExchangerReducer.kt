package app.vahid.feature.currency_exchange.presentation.exchanger.pattern

import app.vahid.common.presentation.pattern.Reducer
import javax.inject.Inject

class ExchangerReducer @Inject constructor() :
    Reducer<ExchangerState, ExchangerEffect> {
    override fun reduce(
        state: ExchangerState,
        effect: ExchangerEffect,
    ): ExchangerState {
        return when (effect) {
            is ExchangerEffect.OnErrorStateChanged -> state.copy(errorType = effect.uiErrorType)
            is ExchangerEffect.OnLoadingStateChanged -> state.copy(isLoading = effect.isLoading)
            is ExchangerEffect.OnUpdateDestinationRates -> TODO()
            is ExchangerEffect.OnUpdateDestinationValue -> TODO()
            is ExchangerEffect.OnUpdateMyBalances -> TODO()
            is ExchangerEffect.OnUpdateOriginRates -> TODO()
        }
    }

}
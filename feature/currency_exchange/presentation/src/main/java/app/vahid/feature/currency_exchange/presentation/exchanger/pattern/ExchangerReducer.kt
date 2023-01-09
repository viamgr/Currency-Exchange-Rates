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
            is ExchangerEffect.OnUpdateDestinationCurrency ->
                state.copy(selectedDestinationCurrency = effect.currencyId)
            is ExchangerEffect.OnUpdateDestinationRateList ->
                state.copy(destinationRateList = effect.list)
            is ExchangerEffect.OnUpdateDestinationValue ->
                state.copy(destinationAmount = effect.amount)
            is ExchangerEffect.OnUpdateMyBalances -> state.copy(balanceList = effect.list)
            is ExchangerEffect.OnUpdateOriginCurrency -> state.copy(selectedOriginCurrency = effect.currencyId)
            is ExchangerEffect.OnUpdateOriginRateList -> state.copy(originRateList = effect.list)
            is ExchangerEffect.OnUpdateOriginValue -> state.copy(originAmount = effect.amount,
                isSubmitButtonEnabled = effect.hasEnoughBalance)
            is ExchangerEffect.OnUpdateSelectedOrigin -> state.copy(selectedOriginCurrency = effect.currencyId)
            is ExchangerEffect.OnUpdateSelectedDestination -> state.copy(selectedDestinationCurrency = effect.currencyId)
        }
    }

}
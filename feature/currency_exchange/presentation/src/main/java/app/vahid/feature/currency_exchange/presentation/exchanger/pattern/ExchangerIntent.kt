package app.vahid.feature.currency_exchange.presentation.exchanger.pattern

import app.vahid.common.presentation.pattern.Intent

sealed interface ExchangerIntent : Intent, ExchangerEvent {

    object Init : ExchangerIntent

    data class OnOriginValueUpdated(val amount: String) : ExchangerIntent

    object OnSubmitClicked : ExchangerIntent

    data class OnOriginCurrencyUpdated(val currencyId: String) : ExchangerIntent

    data class OnDestinationCurrencyUpdated(val currencyId: String) : ExchangerIntent


}



package app.vahid.feature.currency_exchange.presentation.exchanger.pattern

import app.vahid.common.presentation.pattern.Intent

sealed interface ExchangerIntent : Intent, ExchangerEvent {

    object Init : ExchangerIntent

    data class OnUpdateValue(val amount: Double) : ExchangerIntent

    object OnSubmitClicked : ExchangerIntent

    object OnSelectOriginCurrency : ExchangerIntent

}



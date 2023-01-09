package app.vahid.feature.currency_exchange.presentation.exchanger.pattern

import app.vahid.common.presentation.pattern.SideEffect

sealed interface ExchangerSideEffect : SideEffect, ExchangerEvent {
    data class OnCurrencyConverted(
        val originAmount: Double,
        val originCurrency: String,
        val destinationCurrency: String,
        val destinationAmount: Double,
        val fee: Double,
    ) : ExchangerSideEffect
}
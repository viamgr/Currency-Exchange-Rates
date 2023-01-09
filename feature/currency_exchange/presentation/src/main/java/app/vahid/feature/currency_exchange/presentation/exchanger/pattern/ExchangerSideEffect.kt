package app.vahid.feature.currency_exchange.presentation.exchanger.pattern

import app.vahid.common.presentation.pattern.SideEffect
import java.math.BigDecimal

sealed interface ExchangerSideEffect : SideEffect, ExchangerEvent {
    data class OnCurrencyConverted(
        val originAmount: BigDecimal,
        val originCurrency: String,
        val destinationCurrency: String,
        val destinationAmount: BigDecimal,
        val fee: Double,
    ) : ExchangerSideEffect
}
package app.vahid.feature.currency_exchange.presentation.exchanger.pattern

import app.vahid.common.presentation.pattern.SideEffect

sealed interface ExchangerSideEffect : SideEffect, ExchangerEvent {

}
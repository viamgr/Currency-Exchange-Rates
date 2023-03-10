package app.vahid.feature.currency_exchange.presentation.exchanger.pattern

import app.vahid.common.presentation.error_handling.UiErrorType
import app.vahid.common.presentation.pattern.Effect
import app.vahid.domain.gateway.model.Balance
import java.math.BigDecimal

sealed interface ExchangerEffect : Effect, ExchangerEvent {

    data class OnLoadingStateChanged(val isLoading: Boolean) : ExchangerEffect

    data class OnErrorStateChanged(val uiErrorType: UiErrorType?) : ExchangerEffect

    data class OnUpdateMyBalances(val list: List<Balance>) : ExchangerEffect

    data class OnUpdateOriginRateList(val list: List<String>) : ExchangerEffect

    data class OnUpdateDestinationRateList(val list: List<String>) : ExchangerEffect

    data class OnUpdateOriginCurrency(val currencyId: String) : ExchangerEffect

    data class OnUpdateDestinationCurrency(val currencyId: String) : ExchangerEffect

    data class OnUpdateDestinationValue(val amount: BigDecimal) : ExchangerEffect

    data class OnUpdateOriginValue(val amount: BigDecimal) :
        ExchangerEffect

    data class OnUpdateSubmitButtonState(val hasEnoughBalance: Boolean) : ExchangerEffect

    data class OnUpdateFeeValue(val fee: Double) : ExchangerEffect
}


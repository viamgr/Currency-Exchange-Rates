package app.vahid.feature.currency_exchange.presentation.exchanger.pattern

import app.vahid.common.presentation.error_handling.UiErrorType
import app.vahid.common.presentation.pattern.State
import app.vahid.domain.gateway.model.Balance
import java.math.BigDecimal

data class ExchangerState(
    val errorType: UiErrorType? = null,
    val isLoading: Boolean = false,
    val balanceList: List<Balance> = emptyList(),
    val destinationRateList: List<String> = emptyList(),
    val originRateList: List<String> = emptyList(),
    val originAmount: BigDecimal = BigDecimal.ZERO,
    val isSubmitButtonEnabled: Boolean = true,
    val selectedOriginCurrency: String = "",
    val selectedDestinationCurrency: String = "",
    val destinationAmount: BigDecimal = BigDecimal.ZERO,
) : State

package app.vahid.feature.currency_exchange.presentation.exchanger

import app.vahid.common.presentation.BaseViewModel
import app.vahid.common.presentation.dispatchIntent
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerEffect
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerEvent
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerIntent
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerIntent.Init
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerReducer
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerSideEffect
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class ExchangerViewModel @Inject constructor(
    override val reducer: ExchangerReducer = ExchangerReducer(),
) : BaseViewModel<ExchangerIntent, ExchangerState, ExchangerEffect, ExchangerSideEffect,
        ExchangerEvent>(ExchangerState()) {

    init {
        dispatchIntent(Init)
    }

    override suspend fun handleIntent(intent: ExchangerIntent): Flow<ExchangerEvent> =
        when (intent) {
            Init -> handleInit()
            ExchangerIntent.OnSelectOriginCurrency -> TODO()
            ExchangerIntent.OnSubmitClicked -> TODO()
            is ExchangerIntent.OnOriginValueUpdated -> TODO()
        }

    private fun handleInit(): Flow<ExchangerEvent> {
        return emptyFlow()
    }

}
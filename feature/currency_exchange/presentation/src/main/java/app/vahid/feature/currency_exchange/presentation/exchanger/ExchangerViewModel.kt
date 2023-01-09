@file:OptIn(FlowPreview::class)

package app.vahid.feature.currency_exchange.presentation.exchanger

import app.vahid.common.core.fold
import app.vahid.common.presentation.BaseViewModel
import app.vahid.common.presentation.dispatchIntent
import app.vahid.common.presentation.error_handling.UiErrorType
import app.vahid.common.presentation.error_handling.cacheErrors
import app.vahid.domain.use_case.ExchangeCurrencyUseCase
import app.vahid.domain.use_case.GetCurrencyRateListUseCase
import app.vahid.domain.use_case.GetCurrencyRatioUseCase
import app.vahid.domain.use_case.GetMyBalanceListUseCase
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerEffect
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerEvent
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerIntent
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerIntent.Init
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerReducer
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerSideEffect
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

@HiltViewModel
class ExchangerViewModel @Inject constructor(
    private val exchangeCurrencyUseCase: ExchangeCurrencyUseCase,
    private val getCurrencyRatioUseCase: GetCurrencyRatioUseCase,
    private val getMyBalanceListUseCase: GetMyBalanceListUseCase,
    private val getCurrencyRateListUseCase: GetCurrencyRateListUseCase,
    override val reducer: ExchangerReducer = ExchangerReducer(),
) : BaseViewModel<ExchangerIntent, ExchangerState, ExchangerEffect, ExchangerSideEffect,
        ExchangerEvent>(ExchangerState()) {

    init {
        dispatchIntent(Init)
    }

    override suspend fun handleIntent(intent: ExchangerIntent): Flow<ExchangerEvent> =
        when (intent) {
            Init -> handleInit()
            is ExchangerIntent.OnOriginCurrencyUpdated -> onOriginCurrencyUpdatedEffect(intent.currencyId)
            is ExchangerIntent.OnDestinationCurrencyUpdated -> onDestinationCurrencyUpdatedEffect(
                intent.currencyId)
            is ExchangerIntent.OnOriginValueUpdated -> onOriginValueUpdatedEffect(intent.amount)
            ExchangerIntent.OnSubmitClicked -> onSubmitClickedEffect()
        }

    private fun onSubmitClickedEffect(): Flow<ExchangerEvent> = flow {
        exchangeCurrency()
            .cacheErrors()
            .fold(
                onSuccess = {
                    applySuccessfulEffect()
                },
                onFailure = { errorType: UiErrorType, _ ->
                    applyFailedEffect(errorType)
                })
            .collect {
                emit(it)
            }
    }

    private fun applySuccessfulEffect(): Flow<ExchangerEvent> =
        merge(applyDialogSideEffect(), applyLoadingEffect(false))

    private fun applyDialogSideEffect(): Flow<ExchangerEvent> =
        with(container.stateFlow.value) {
            flowOf(
                ExchangerSideEffect.OnCurrencyConverted(
                    originAmount = originAmount,
                    originCurrency = selectedOriginCurrency,
                    destinationAmount = destinationAmount,
                    destinationCurrency = selectedDestinationCurrency,
                    fee = 0.0
                )

            )
        }

    private suspend fun exchangeCurrency() =
        exchangeCurrencyUseCase(
            with(container.stateFlow.value) {
                ExchangeCurrencyUseCase.Request(
                    originCurrency = selectedOriginCurrency,
                    destinationCurrency = selectedDestinationCurrency,
                    originAmount = originAmount
                )
            }
        )

    private fun applyFailedEffect(error: UiErrorType) =
        merge(applyLoadingEffect(false), applyErrorEffect(error))


    private fun applyErrorEffect(uiErrorType: UiErrorType?) =
        listOf(ExchangerEffect.OnErrorStateChanged(uiErrorType)).asFlow()

    private fun applyLoadingEffect(state: Boolean) =
        listOf(ExchangerEffect.OnLoadingStateChanged(state)).asFlow()

    private fun onOriginValueUpdatedEffect(amount: Double): Flow<ExchangerEvent> {
        return flowOf(ExchangerEffect.OnUpdateOriginValue(amount))
    }

    private fun onOriginCurrencyUpdatedEffect(currencyId: String): Flow<ExchangerEvent> {
        return flowOf(ExchangerEffect.OnUpdateOriginCurrency(currencyId))
    }

    private fun onDestinationCurrencyUpdatedEffect(currencyId: String): Flow<ExchangerEvent> {
        return listOf(
            ExchangerEffect.OnUpdateDestinationCurrency(currencyId)
        ).asFlow()
    }

    private fun handleInit(): Flow<ExchangerEvent> {
        return merge(
            getRateListEffect(),
            getMyBalanceListEffect(),
            getDestinationAmountEffect()
        )
    }

    private fun getDestinationAmountEffect(): Flow<ExchangerEvent> {
        return container
            .stateFlow
            .flatMapConcat {
                getCurrencyRatioUseCase(
                    GetCurrencyRatioUseCase.Request(
                        originCurrency = container.stateFlow.value.selectedOriginCurrency,
                        destinationCurrency = container.stateFlow.value.selectedDestinationCurrency,
                        originAmount = container.stateFlow.value.originAmount)
                )
            }
            .map {
                ExchangerEffect.OnUpdateDestinationValue(it)
            }
    }

    private fun getMyBalanceListEffect(): Flow<ExchangerEvent> {
        return getMyBalanceListUseCase(Unit)
            .flatMapConcat { balances ->
                listOf(
                    ExchangerEffect.OnUpdateOriginRateList(balances.map { it.currencyId }),
                    ExchangerEffect.OnUpdateMyBalances(balances)
                ).asFlow()
            }
    }

    private fun getRateListEffect(): Flow<ExchangerEvent> {
        return getCurrencyRateListUseCase(Unit).map { rates ->
            ExchangerEffect.OnUpdateOriginRateList(
                rates.map { it.currencyId })
        }
    }

}
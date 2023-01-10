@file:OptIn(FlowPreview::class)

package app.vahid.feature.currency_exchange.presentation.exchanger

import androidx.lifecycle.viewModelScope
import app.vahid.common.core.fold
import app.vahid.common.core.onFailure
import app.vahid.common.core.onSuccess
import app.vahid.common.presentation.BaseViewModel
import app.vahid.common.presentation.dispatchIntent
import app.vahid.common.presentation.error_handling.UiErrorType
import app.vahid.common.presentation.error_handling.cacheErrors
import app.vahid.domain.use_case.ExchangeCurrencyUseCase
import app.vahid.domain.use_case.GetCurrencyRateListUseCase
import app.vahid.domain.use_case.GetCurrencyRatioUseCase
import app.vahid.domain.use_case.GetFeeUseCase
import app.vahid.domain.use_case.GetMyBalanceListUseCase
import app.vahid.domain.use_case.UpdateCurrencyRateListUseCase
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangerViewModel @Inject constructor(
    private val exchangeCurrencyUseCase: ExchangeCurrencyUseCase,
    private val getCurrencyRatioUseCase: GetCurrencyRatioUseCase,
    private val getMyBalanceListUseCase: GetMyBalanceListUseCase,
    private val getCurrencyRateListUseCase: GetCurrencyRateListUseCase,
    private val updateCurrencyRateListUseCase: UpdateCurrencyRateListUseCase,
    private val getFeeUseCase: GetFeeUseCase,
    override val reducer: ExchangerReducer = ExchangerReducer(),
) : BaseViewModel<ExchangerIntent, ExchangerState, ExchangerEffect, ExchangerSideEffect,
        ExchangerEvent>(ExchangerState()) {

    init {
        dispatchIntent(Init)
        viewModelScope.launch {
            container.stateFlow.collect {
                getCurrencyRatioUseCase(
                    GetCurrencyRatioUseCase.Request(
                        originCurrency = it.selectedOriginCurrency,
                        destinationCurrency = it.selectedDestinationCurrency,
                        originAmount = it.originAmount)
                )
            }
        }
    }

    override suspend fun handleIntent(intent: ExchangerIntent): Flow<ExchangerEvent> {
        return when (intent) {
            Init -> handleInit()
            is ExchangerIntent.OnOriginCurrencyUpdated -> onOriginCurrencyUpdatedEffect(intent.currencyId)
            is ExchangerIntent.OnDestinationCurrencyUpdated -> onDestinationCurrencyUpdatedEffect(
                intent.currencyId)
            is ExchangerIntent.OnOriginValueUpdated -> onOriginValueUpdatedEffect(intent.amount)
            ExchangerIntent.OnSubmitClicked -> onSubmitClickedEffect()
        }
    }

    private fun onSubmitClickedEffect(): Flow<ExchangerEvent> =
        merge(applyLoadingEffect(true), applyExchangeCurrencyEffect())

    private fun applyExchangeCurrencyEffect() = flow {
        exchangeCurrency()
            .cacheErrors()
            .fold(
                onSuccess = {
                    applyLoadingEffect(false)
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
                    fee = fee,
                )
            )
        }

    private suspend fun exchangeCurrency() = with(container.stateFlow.value) {
        exchangeCurrencyUseCase(
            ExchangeCurrencyUseCase.Request(
                originCurrency = selectedOriginCurrency,
                destinationCurrency = selectedDestinationCurrency,
                originAmount = originAmount
            )
        )
    }

    private fun applyFailedEffect(error: UiErrorType?) =
        merge(applyLoadingEffect(false), applyErrorEffect(error))


    private fun applyErrorEffect(uiErrorType: UiErrorType?) =
        listOf(ExchangerEffect.OnErrorStateChanged(uiErrorType)).asFlow()

    private fun applyLoadingEffect(state: Boolean) =
        listOf(ExchangerEffect.OnLoadingStateChanged(state)).asFlow()


    private fun onSubmitButtonUpdatedEffect(): Flow<ExchangerEvent> = flow {
        container.stateFlow.combine(getFeeUseCase(Unit)) { state, fee ->
            with(state) {
                balanceList.firstOrNull { selectedOriginCurrency == it.currencyId }?.let {
                    val balanceOfCurrency = it.amount
                    val hasEnoughBalance =
                        originAmount <= balanceOfCurrency - (fee.toBigDecimal() * balanceOfCurrency)
                    emit(ExchangerEffect.OnUpdateSubmitButtonState(hasEnoughBalance))
                    emit(ExchangerEffect.OnUpdateFeeValue(fee))
                }
            }
        }.collect()

    }

    private fun onOriginValueUpdatedEffect(amount: String): Flow<ExchangerEvent> = flow {
        amount.runCatching {
            toBigDecimal()
        }.onSuccess {
            emit(ExchangerEffect.OnUpdateOriginValue(it))
        }
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
            updateCurrencyRateListEffect(),
            getRateListEffect(),
            getMyBalanceListEffect(),
            getDestinationAmountEffect(),
            onSubmitButtonUpdatedEffect()
        )
    }

    private fun updateCurrencyRateListEffect(): Flow<ExchangerEvent> = flow {
        updateCurrencyRateListUseCase(Unit).collect {
            it
                .cacheErrors()
                .onSuccess {
                    emitAll(merge(applyFailedEffect(null)))

                }
                .onFailure { errorType: UiErrorType, _ ->
                    emitAll(applyFailedEffect(errorType))
                }
        }
    }

    private fun getDestinationAmountEffect(): Flow<ExchangerEvent> {
        return container
            .stateFlow
            .filter { it.selectedOriginCurrency.isNotEmpty() && it.selectedDestinationCurrency.isNotEmpty() }
            .flatMapConcat {
                getCurrencyRatioUseCase.flow
            }
            .map {
                ExchangerEffect.OnUpdateDestinationValue(it)
            }
    }

    private fun getMyBalanceListEffect(): Flow<ExchangerEvent> {
        return getMyBalanceListUseCase(Unit)
            .filter { it.isNotEmpty() }
            .flatMapConcat { balances ->
                listOf(
                    ExchangerEffect.OnUpdateOriginCurrency(balances.first().currencyId),
                    ExchangerEffect.OnUpdateOriginRateList(balances.map { it.currencyId }),
                    ExchangerEffect.OnUpdateMyBalances(balances)
                ).asFlow()
            }
    }

    private fun getRateListEffect(): Flow<ExchangerEvent> {
        return getCurrencyRateListUseCase(Unit)
            .filter { it.isNotEmpty() }
            .flatMapConcat { rates ->
                mutableListOf<ExchangerEvent>().apply {
                    add(ExchangerEffect.OnUpdateDestinationRateList(rates.map { it.currencyId }))
                    if (container.stateFlow.value.selectedDestinationCurrency.isEmpty()) {
                        add(ExchangerEffect.OnUpdateDestinationCurrency(rates.first().currencyId))
                    }
                }
                    .asFlow()
            }
    }

}
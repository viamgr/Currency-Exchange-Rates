package app.vahid.domain.use_case

import app.vahid.common.core.IoDispatcher
import app.vahid.common.core.WrappedResult
import app.vahid.common.core.flatMap
import app.vahid.common.usecase_common.SuspendUseCase
import app.vahid.domain.gateway.model.Transaction
import app.vahid.domain.gateway.repository.CurrencyExchangeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ExchangeCurrencyUseCase @Inject constructor(
    private val getTransactionCountUseCase: GetTransactionCountUseCase,
    private val currencyExchangeRepository: CurrencyExchangeRepository,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val getCurrencyRatioUseCase: GetCurrencyRatioUseCase,
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
) : SuspendUseCase<ExchangeCurrencyUseCase.Request, WrappedResult<Unit>>(ioDispatcher) {
    override suspend fun execute(parameter: Request): WrappedResult<Unit> {
        val fee = getFee()

        val destinationAmount = getCurrencyRatioUseCase(
            GetCurrencyRatioUseCase.Request(parameter.originCurrency,
                parameter.destinationCurrency,
                parameter.originAmount
            )
        ).first()

        return currencyExchangeRepository
            .exchangeCurrency(
                parameter.originAmount,
                parameter.originCurrency,
                parameter.destinationCurrency
            )
            .flatMap {
                addTransactionUseCase(
                    Transaction(
                        originCurrency = parameter.originCurrency,
                        destinationCurrency = parameter.destinationCurrency,
                        originAmount = parameter.originAmount,
                        destinationAmount = destinationAmount,
                        fee = fee
                    )
                )
            }
    }

    private suspend fun getFee() = if (getTransactionCountUseCase(Unit).first() >= 5)
        0.007
    else 0.0

    data class Request(
        val originCurrency: String,
        val destinationCurrency: String,
        val originAmount: Double,
    )
}
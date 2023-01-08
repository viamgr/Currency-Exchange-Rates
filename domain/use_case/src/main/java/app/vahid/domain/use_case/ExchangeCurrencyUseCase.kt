package app.vahid.domain.use_case

import app.vahid.common.core.IoDispatcher
import app.vahid.common.core.WrappedResult
import app.vahid.common.core.flatMap
import app.vahid.common.usecase_common.SuspendUseCase
import app.vahid.domain.gateway.model.Transaction
import app.vahid.domain.gateway.repository.CurrencyExchangeRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ExchangeCurrencyUseCase @Inject constructor(
    private val currencyExchangeRepository: CurrencyExchangeRepository,
    private val addTransactionUseCase: AddTransactionUseCase,
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
) : SuspendUseCase<ExchangeCurrencyUseCase.Request, WrappedResult<Unit>>(ioDispatcher) {
    override suspend fun execute(parameter: Request): WrappedResult<Unit> {
        return currencyExchangeRepository
            .exchangeCurrency(
                parameter.amount,
                parameter.originCurrency,
                parameter.destinationCurrency
            )
            .flatMap {
                addTransactionUseCase(
                    Transaction(
                        originCurrency = parameter.originCurrency,
                        destinationCurrency = parameter.destinationCurrency,
                        amount = parameter.amount
                    )
                )
            }
    }

    data class Request(
        val originCurrency: String,
        val destinationCurrency: String,
        val amount: Double,
    )
}
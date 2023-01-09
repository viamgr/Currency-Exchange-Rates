package app.vahid.domain.use_case

import app.vahid.common.core.IoDispatcher
import app.vahid.common.core.WrappedResult
import app.vahid.common.core.flatMap
import app.vahid.common.usecase_common.SuspendUseCase
import app.vahid.domain.gateway.model.Transaction
import app.vahid.domain.gateway.repository.CurrencyExchangeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.math.BigDecimal
import javax.inject.Inject

private const val MIN_TRANSACTION_FEE_FREE = 5
private const val FEE_AMOUNT = 0.007

class ExchangeCurrencyUseCase @Inject constructor(
    private val getTransactionCountUseCase: GetTransactionCountUseCase,
    private val currencyExchangeRepository: CurrencyExchangeRepository,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val getCurrencyRatioUseCase: GetCurrencyRatioUseCase,
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
) : SuspendUseCase<ExchangeCurrencyUseCase.Request, WrappedResult<Unit>>(ioDispatcher) {
    override suspend fun execute(parameter: Request): WrappedResult<Unit> {
        Timber.d("ExchangeCurrencyUseCase $parameter")
        val fee = getFee()

        val currencyRatioUseCase = getCurrencyRatioUseCase
        currencyRatioUseCase(
            GetCurrencyRatioUseCase.Request(parameter.originCurrency,
                parameter.destinationCurrency,
                parameter.originAmount
            )
        )
        val destinationAmount = currencyRatioUseCase.flow.first()

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

    private suspend fun getFee() =
        if (getTransactionCountUseCase(Unit).first() >= MIN_TRANSACTION_FEE_FREE + 1)
            FEE_AMOUNT
        else 0.0

    data class Request(
        val originCurrency: String,
        val destinationCurrency: String,
        val originAmount: BigDecimal,
    )
}
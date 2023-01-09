package app.vahid.domain.use_case

import app.vahid.common.core.IoDispatcher
import app.vahid.common.usecase_common.SubjectUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.math.BigDecimal
import javax.inject.Inject

class GetCurrencyRatioUseCase @Inject constructor(
    private val exchangeCalculatorUseCase: ExchangeCalculatorUseCase,
    private val getExchangeRatesUseCase: GetExchangeRatesUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : SubjectUseCase<GetCurrencyRatioUseCase.Request, BigDecimal>(ioDispatcher) {
    override suspend fun createObservable(parameter: Request): Flow<BigDecimal> {
        return getExchangeRatesUseCase(
            GetExchangeRatesUseCase.Request(
                originCurrencyId = parameter.originCurrency,
                destinationCurrencyId = parameter.destinationCurrency)
        )
            .map { response ->
                Timber.d("GetCurrencyRatioUseCase response $response")

                val baseCurrencyAmount =
                    exchangeCalculatorUseCase(
                        ExchangeCalculatorUseCase.Request(
                            originCurrencyRate = response.originCurrencyRate,
                            destinationCurrencyRate = response.baseCurrencyRate,
                            amount = parameter.originAmount
                        )
                    )
                Timber.d("GetCurrencyRatioUseCase baseCurrencyAmount $baseCurrencyAmount")
                exchangeCalculatorUseCase(
                    ExchangeCalculatorUseCase.Request(
                        amount = baseCurrencyAmount,
                        originCurrencyRate = response.baseCurrencyRate,
                        destinationCurrencyRate = response.destinationCurrencyRate
                    )
                ).also {
                    Timber.d("GetCurrencyRatioUseCase destinationCurrencyRate $it")
                }
            }
    }


    data class Request(
        val originCurrency: String,
        val destinationCurrency: String,
        val originAmount: BigDecimal,
    )

}
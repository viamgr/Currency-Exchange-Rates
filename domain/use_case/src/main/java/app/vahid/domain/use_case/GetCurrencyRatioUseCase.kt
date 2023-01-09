package app.vahid.domain.use_case

import app.vahid.common.usecase_common.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCurrencyRatioUseCase @Inject constructor(
    private val exchangeCalculatorUseCase: ExchangeCalculatorUseCase,
    private val getExchangeRatesUseCase: GetExchangeRatesUseCase,
) :
    FlowUseCase<GetCurrencyRatioUseCase.Request, Double>() {
    override fun execute(parameter: Request): Flow<Double> {

        return getExchangeRatesUseCase(
            GetExchangeRatesUseCase.Request(
                originCurrencyId = parameter.originCurrency,
                destinationCurrencyId = parameter.destinationCurrency)
        )
            .map { response ->
                val baseCurrencyAmount =
                    exchangeCalculatorUseCase(
                        ExchangeCalculatorUseCase.Request(
                            originCurrencyRate = response.originCurrencyRate,
                            destinationCurrencyRate = response.baseCurrencyRate,
                            amount = parameter.originAmount
                        )
                    )

                exchangeCalculatorUseCase(
                    ExchangeCalculatorUseCase.Request(
                        amount = baseCurrencyAmount,
                        originCurrencyRate = response.baseCurrencyRate,
                        destinationCurrencyRate = response.destinationCurrencyRate
                    )
                )
            }
    }


    data class Request(
        val originCurrency: String,
        val destinationCurrency: String,
        val originAmount: Double,
    )

}
package app.vahid.domain.use_case

import app.vahid.common.usecase_common.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(
    private val exchangeCalculatorUseCase: ExchangeCalculatorUseCase,
    private val getTransactionRatesUseCase: GetTransactionRatesUseCase,
) :
    FlowUseCase<ConvertCurrencyUseCase.PurchaseRequest, Double>() {
    override fun execute(parameter: PurchaseRequest): Flow<Double> {

        return getTransactionRatesUseCase(
            GetTransactionRatesUseCase.Request(
                fromCurrencyId = parameter.fromCurrency,
                parameter.toCurrency)
        )
            .map { response ->
                val baseCurrencyAmount =
                    exchangeCalculatorUseCase(
                        ExchangeCalculatorUseCase.Request(
                            fromCurrencyRate = response.baseCurrencyRate,
                            toCurrencyRate = response.fromCurrencyRate,
                            amount = parameter.amount
                        )
                    )

                exchangeCalculatorUseCase(
                    ExchangeCalculatorUseCase.Request(
                        amount = baseCurrencyAmount,
                        toCurrencyRate = response.toCurrencyRate,
                        fromCurrencyRate = response.fromCurrencyRate
                    )
                )
            }
    }


    data class PurchaseRequest(
        val fromCurrency: String,
        val toCurrency: String,
        val amount: Double,
    )

}
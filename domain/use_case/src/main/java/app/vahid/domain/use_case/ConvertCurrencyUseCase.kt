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
                originCurrencyId = parameter.originCurrency,
                destinationCurrencyId = parameter.destinationCurrency)
        )
            .map { response ->
                val baseCurrencyAmount =
                    exchangeCalculatorUseCase(
                        ExchangeCalculatorUseCase.Request(
                            originCurrencyRate = response.originCurrencyRate,
                            destinationCurrencyRate = response.baseCurrencyRate,
                            amount = parameter.amount
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


    data class PurchaseRequest(
        val originCurrency: String,
        val destinationCurrency: String,
        val amount: Double,
    )

}
package app.vahid.domain.use_case

import app.vahid.common.usecase_common.UseCase
import javax.inject.Inject

class ExchangeCalculatorUseCase @Inject constructor() :
    UseCase<ExchangeCalculatorUseCase.Request, Double>() {
    override fun execute(parameter: Request): Double {
        return (parameter.destinationCurrencyRate / parameter.originCurrencyRate) * parameter.amount
    }

    data class Request(
        val originCurrencyRate: Double,
        val destinationCurrencyRate: Double,
        val amount: Double,
    )

}
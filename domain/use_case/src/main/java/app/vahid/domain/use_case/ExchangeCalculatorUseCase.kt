package app.vahid.domain.use_case

import app.vahid.common.usecase_common.UseCase
import java.math.BigDecimal
import javax.inject.Inject

class ExchangeCalculatorUseCase @Inject constructor() :
    UseCase<ExchangeCalculatorUseCase.Request, BigDecimal>() {
    override fun execute(parameter: Request): BigDecimal {
        return (parameter.destinationCurrencyRate / parameter.originCurrencyRate).toBigDecimal() * parameter.amount
    }

    data class Request(
        val originCurrencyRate: Double,
        val destinationCurrencyRate: Double,
        val amount: BigDecimal,
    )

}
package app.vahid.domain.use_case

import app.vahid.common.core.WrappedResult
import app.vahid.common.core.flatMap
import app.vahid.common.usecase_common.FlowUseCase
import app.vahid.domain.gateway.repository.RateExchangerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PurchaseCurrencyUseCase @Inject constructor(
    private val rateExchangerRepository: RateExchangerRepository,
) : FlowUseCase<PurchaseCurrencyUseCase.Request, WrappedResult<Unit>>() {
    override fun execute(parameter: Request): Flow<WrappedResult<Unit>> {
        return rateExchangerRepository.purchaseCurrency(
            parameter.amount,
            parameter.originCurrency,
            parameter.destinationCurrency
        ).map {
            it.flatMap {
                rateExchangerRepository.addPurchase(
                    originCurrency = parameter.originCurrency,
                    destinationCurrency = parameter.destinationCurrency,
                    amount = parameter.amount
                )
            }
        }
    }

    data class Request(
        val originCurrency: String,
        val destinationCurrency: String,
        val amount: Double,
    )

}
package app.vahid.domain.use_case

import app.vahid.common.core.WrappedResult
import app.vahid.common.usecase_common.FlowUseCase
import app.vahid.domain.gateway.repository.RateExchangerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PurchaseCurrencyUseCase @Inject constructor(private val rateExchangerRepository: RateExchangerRepository) :
    FlowUseCase<PurchaseCurrencyUseCase.PurchaseRequest, WrappedResult<Boolean>>() {
    override fun execute(parameter: PurchaseRequest): Flow<WrappedResult<Boolean>> {
        return rateExchangerRepository.purchaseCurrency(
            parameter.amount,
            parameter.currencyId,
            parameter.baseCurrencyId
        )
    }

    data class PurchaseRequest(
        val amount: Double,
        val currencyId: String,
        val baseCurrencyId: String,
    )

}
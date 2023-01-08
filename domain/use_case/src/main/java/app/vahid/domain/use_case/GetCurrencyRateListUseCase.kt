package app.vahid.domain.use_case

import app.vahid.common.usecase_common.FlowUseCase
import app.vahid.domain.gateway.model.CurrencyRate
import app.vahid.domain.gateway.repository.RateExchangerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrencyRateListUseCase @Inject constructor(private val rateExchangerRepository: RateExchangerRepository) :
    FlowUseCase<Unit, List<CurrencyRate>>() {
    override fun execute(parameter: Unit): Flow<List<CurrencyRate>> {
        return rateExchangerRepository.getCurrencyRateList()
    }
}
package app.vahid.domain.use_case

import app.vahid.common.usecase_common.FlowUseCase
import app.vahid.domain.gateway.repository.RateExchangerRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

@OptIn(FlowPreview::class)
class GetBaseCurrencyRateUseCase @Inject constructor(
    private val rateExchangerRepository: RateExchangerRepository,
) :
    FlowUseCase<Unit, Double>() {
    override fun execute(parameter: Unit): Flow<Double> {
        return rateExchangerRepository.getBaseCurrency()
            .flatMapConcat {
                rateExchangerRepository.getCurrencyRate(it)
            }
    }

}
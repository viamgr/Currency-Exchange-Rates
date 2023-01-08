package app.vahid.domain.use_case

import app.vahid.common.usecase_common.FlowUseCase
import app.vahid.domain.gateway.model.Balance
import app.vahid.domain.gateway.repository.RateExchangerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyBalanceListUseCase @Inject constructor(
    private val rateExchangerRepository: RateExchangerRepository,
) :
    FlowUseCase<Unit, List<Balance>>() {
    override fun execute(parameter: Unit): Flow<List<Balance>> {
        return rateExchangerRepository.getBalanceList()
    }

}
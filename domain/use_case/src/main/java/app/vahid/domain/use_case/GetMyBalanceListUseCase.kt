package app.vahid.domain.use_case

import app.vahid.common.core.IoDispatcher
import app.vahid.common.usecase_common.FlowUseCase
import app.vahid.domain.gateway.model.Balance
import app.vahid.domain.gateway.repository.CurrencyExchangeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyBalanceListUseCase @Inject constructor(
    private val currencyExchangeRepository: CurrencyExchangeRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : FlowUseCase<Unit, List<Balance>>(ioDispatcher) {
    override fun execute(parameter: Unit): Flow<List<Balance>> {
        return currencyExchangeRepository.getBalanceList()
    }

}
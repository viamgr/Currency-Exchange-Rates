package app.vahid.domain.use_case

import app.vahid.common.core.IoDispatcher
import app.vahid.common.usecase_common.FlowUseCase
import app.vahid.domain.gateway.repository.CurrencyExchangeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

@OptIn(FlowPreview::class)
class GetBaseCurrencyRateUseCase @Inject constructor(
    private val currencyExchangeRepository: CurrencyExchangeRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) :
    FlowUseCase<Unit, Double>(ioDispatcher) {
    override fun execute(parameter: Unit): Flow<Double> {
        return currencyExchangeRepository.getBaseCurrency()
            .filterNotNull()
            .flatMapConcat {
                currencyExchangeRepository.getCurrencyRate(it)
            }
    }

}
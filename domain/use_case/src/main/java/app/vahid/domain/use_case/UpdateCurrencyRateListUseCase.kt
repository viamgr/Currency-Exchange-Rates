package app.vahid.domain.use_case

import app.vahid.common.core.IoDispatcher
import app.vahid.common.core.WrappedResult
import app.vahid.common.usecase_common.FlowUseCase
import app.vahid.domain.gateway.repository.CurrencyExchangeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val INTERVAL_DELAY = 500000L

class UpdateCurrencyRateListUseCase @Inject constructor(
    private val currencyExchangeRepository: CurrencyExchangeRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : FlowUseCase<Unit, WrappedResult<Unit>>() {
    override fun execute(parameter: Unit) = flow<WrappedResult<Unit>> {
        while (currentCoroutineContext().isActive) {
            withContext(ioDispatcher) {
                currencyExchangeRepository.updateCurrencyRateList()
                delay(INTERVAL_DELAY)
            }
        }
    }
}
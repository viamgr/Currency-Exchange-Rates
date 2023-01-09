package app.vahid.domain.use_case

import app.vahid.common.core.WrappedResult
import app.vahid.common.usecase_common.FlowUseCase
import app.vahid.domain.gateway.repository.CurrencyExchangeRepository
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import javax.inject.Inject

private const val INTERVAL_DELAY = 5000L

class UpdateCurrencyRateListUseCase @Inject constructor(
    private val currencyExchangeRepository: CurrencyExchangeRepository,
) : FlowUseCase<Unit, WrappedResult<Unit>>() {
    override fun execute(parameter: Unit) = flow {
        while (currentCoroutineContext().isActive) {
            emit(currencyExchangeRepository.updateCurrencyRateList())
            delay(INTERVAL_DELAY)
        }
    }
}
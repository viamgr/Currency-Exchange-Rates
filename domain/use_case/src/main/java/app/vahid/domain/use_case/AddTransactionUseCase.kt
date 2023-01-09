package app.vahid.domain.use_case

import app.vahid.common.core.IoDispatcher
import app.vahid.common.core.WrappedResult
import app.vahid.common.usecase_common.SuspendUseCase
import app.vahid.domain.gateway.model.Transaction
import app.vahid.domain.gateway.repository.CurrencyExchangeRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val currencyExchangeRepository: CurrencyExchangeRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
) : SuspendUseCase<Transaction, WrappedResult<Unit>>(ioDispatcher) {
    override suspend fun execute(parameter: Transaction): WrappedResult<Unit> {
        return currencyExchangeRepository.addTransaction(parameter)
    }
}
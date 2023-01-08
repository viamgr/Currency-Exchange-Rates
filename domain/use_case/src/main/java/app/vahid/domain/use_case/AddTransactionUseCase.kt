package app.vahid.domain.use_case

import app.vahid.common.core.WrappedResult
import app.vahid.common.usecase_common.UseCase
import app.vahid.domain.gateway.model.Transaction
import app.vahid.domain.gateway.repository.RateExchangerRepository
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val rateExchangerRepository: RateExchangerRepository,
) : UseCase<Transaction, WrappedResult<Unit>>() {
    override fun execute(parameter: Transaction): WrappedResult<Unit> {
        return rateExchangerRepository.addTransaction(parameter)
    }
}
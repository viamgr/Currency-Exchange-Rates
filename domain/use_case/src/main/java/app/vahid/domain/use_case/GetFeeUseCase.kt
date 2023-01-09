package app.vahid.domain.use_case

import app.vahid.common.usecase_common.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

private const val MIN_TRANSACTION_FEE_FREE = 5
private const val FEE_AMOUNT = 0.007

class GetFeeUseCase @Inject constructor(
    private val getTransactionCountUseCase: GetTransactionCountUseCase,
) : FlowUseCase<Unit, Double>() {
    override fun execute(parameter: Unit): Flow<Double> {
        Timber.d("getTransactionCount 000")
        return getTransactionCountUseCase(Unit).map {
            Timber.d("getTransactionCount 1111")

            if (it >= MIN_TRANSACTION_FEE_FREE + 1)
                FEE_AMOUNT
            else 0.0
        }
    }
}
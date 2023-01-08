package app.vahid.domain.use_case

import app.vahid.common.usecase_common.FlowUseCase
import app.vahid.domain.gateway.repository.RateExchangerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

class GetTransactionRatesUseCase @Inject constructor(
    private val rateExchangerRepository: RateExchangerRepository,
    private val getBaseCurrencyRateUseCase: GetBaseCurrencyRateUseCase,
) :
    FlowUseCase<GetTransactionRatesUseCase.Request, GetTransactionRatesUseCase.Response>() {
    override fun execute(parameter: Request): Flow<Response> {

        return getBaseCurrencyRateUseCase(Unit)
            .flatMapConcat { baseCurrencyRate: Double ->
                combine(
                    rateExchangerRepository.getCurrencyRate(parameter.fromCurrencyId),
                    rateExchangerRepository.getCurrencyRate(parameter.toCurrencyId)
                ) { fromCurrencyRate: Double, toCurrencyRate: Double ->
                    Response(fromCurrencyRate = fromCurrencyRate,
                        toCurrencyRate = toCurrencyRate,
                        baseCurrencyRate = baseCurrencyRate)
                }
            }
    }


    data class Request(
        val fromCurrencyId: String,
        val toCurrencyId: String,
    )

    data class Response(
        val fromCurrencyRate: Double,
        val toCurrencyRate: Double,
        val baseCurrencyRate: Double,
    )

}
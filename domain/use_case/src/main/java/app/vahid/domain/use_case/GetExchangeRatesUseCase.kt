package app.vahid.domain.use_case

import app.vahid.common.usecase_common.FlowUseCase
import app.vahid.domain.gateway.repository.CurrencyExchangeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

class GetExchangeRatesUseCase @Inject constructor(
    private val currencyExchangeRepository: CurrencyExchangeRepository,
    private val getBaseCurrencyRateUseCase: GetBaseCurrencyRateUseCase,
) :
    FlowUseCase<GetExchangeRatesUseCase.Request, GetExchangeRatesUseCase.Response>() {
    override fun execute(parameter: Request): Flow<Response> {

        return getBaseCurrencyRateUseCase(Unit)
            .flatMapConcat { baseCurrencyRate: Double ->
                combine(
                    currencyExchangeRepository.getCurrencyRate(parameter.originCurrencyId),
                    currencyExchangeRepository.getCurrencyRate(parameter.destinationCurrencyId)
                ) { originCurrencyRate: Double, destinationCurrencyRate: Double ->
                    Response(originCurrencyRate = originCurrencyRate,
                        destinationCurrencyRate = destinationCurrencyRate,
                        baseCurrencyRate = baseCurrencyRate)
                }
            }
    }

    data class Request(
        val originCurrencyId: String,
        val destinationCurrencyId: String,
    )

    data class Response(
        val originCurrencyRate: Double,
        val destinationCurrencyRate: Double,
        val baseCurrencyRate: Double,
    )

}
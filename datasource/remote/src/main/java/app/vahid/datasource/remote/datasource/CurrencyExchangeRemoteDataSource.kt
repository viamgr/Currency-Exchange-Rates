package app.vahid.datasource.remote.datasource

import app.vahid.common.core.WrappedResult
import app.vahid.common.core.map
import app.vahid.datasource.remote.api.CurrencyExchangeApi
import app.vahid.repository.datasource.CurrencyExchangeRemoteDataSource
import app.vahid.repository.model.CurrencyRateEntity
import javax.inject.Inject

internal class CurrencyExchangeRemoteDataSourceImpl @Inject constructor(
    private val currencyExchangeApi: CurrencyExchangeApi,
) :
    CurrencyExchangeRemoteDataSource {
    override fun exchangeCurrency(
        amount: Double,
        currencyId: String,
        baseCurrencyId: String,
    ): WrappedResult<Unit> {
        return WrappedResult.success(Unit)
    }

    override fun getCurrencyRateList(): WrappedResult<List<CurrencyRateEntity>> {
        return currencyExchangeApi.getRates()
            .map { response ->
                response.rates.value.map {
                    CurrencyRateEntity(it.currencyId, it.rate)
                }
            }
    }
}
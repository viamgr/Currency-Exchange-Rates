package app.vahid.datasource.remote.datasource

import app.vahid.common.core.WrappedResult
import app.vahid.common.core.map
import app.vahid.datasource.remote.api.CurrencyExchangeApi
import app.vahid.repository.datasource.CurrencyExchangeRemoteDataSource
import app.vahid.repository.model.CurrencyRateEntity
import app.vahid.repository.model.CurrencyRateListEntity
import java.math.BigDecimal
import javax.inject.Inject

internal class CurrencyExchangeRemoteDataSourceImpl @Inject constructor(
    private val currencyExchangeApi: CurrencyExchangeApi,
) :
    CurrencyExchangeRemoteDataSource {
    override suspend fun exchangeCurrency(
        amount: BigDecimal,
        currencyId: String,
        baseCurrencyId: String,
    ): WrappedResult<Unit> {
        return WrappedResult.success(Unit)
    }

    override suspend fun getCurrencyRateList(): WrappedResult<CurrencyRateListEntity> {
        return currencyExchangeApi.getRates()
            .map { response ->
                CurrencyRateListEntity(
                    base = response.base,
                    date = response.date,
                    rates = response.rates.map {
                        CurrencyRateEntity(it.currencyId, it.rate)
                    }
                )
            }
    }
}
package app.vahid.repository.datasource

import app.vahid.common.core.WrappedResult
import app.vahid.repository.model.CurrencyRateListEntity

interface CurrencyExchangeRemoteDataSource {

    fun exchangeCurrency(
        amount: Double,
        currencyId: String,
        baseCurrencyId: String,
    ): WrappedResult<Unit>

    fun getCurrencyRateList(): WrappedResult<CurrencyRateListEntity>
}
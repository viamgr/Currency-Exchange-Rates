package app.vahid.repository.datasource

import app.vahid.common.core.WrappedResult
import app.vahid.domain.gateway.model.CurrencyRate

interface ExchangeCurrencyRemoteDataSource {

    fun exchangeCurrency(
        amount: Double,
        currencyId: String,
        baseCurrencyId: String,
    ): WrappedResult<Unit>

    fun getCurrencyRateList(): WrappedResult<List<CurrencyRate>>
}
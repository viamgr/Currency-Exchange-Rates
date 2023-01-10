package app.vahid.repository.datasource

import app.vahid.common.core.WrappedResult
import app.vahid.repository.model.CurrencyRateListEntity
import java.math.BigDecimal

interface CurrencyExchangeRemoteDataSource {

    suspend fun exchangeCurrency(
        amount: BigDecimal,
        currencyId: String,
        baseCurrencyId: String,
    ): WrappedResult<Unit>

    suspend fun getCurrencyRateList(): WrappedResult<CurrencyRateListEntity>
}
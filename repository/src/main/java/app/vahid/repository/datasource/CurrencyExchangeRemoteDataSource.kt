package app.vahid.repository.datasource

import app.vahid.common.core.WrappedResult
import app.vahid.repository.model.CurrencyRateListEntity
import java.math.BigDecimal

interface CurrencyExchangeRemoteDataSource {

    fun exchangeCurrency(
        amount: BigDecimal,
        currencyId: String,
        baseCurrencyId: String,
    ): WrappedResult<Unit>

    fun getCurrencyRateList(): WrappedResult<CurrencyRateListEntity>
}
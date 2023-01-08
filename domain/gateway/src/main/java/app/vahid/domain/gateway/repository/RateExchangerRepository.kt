package app.vahid.domain.gateway.repository

import app.vahid.common.core.WrappedResult
import app.vahid.common.core.enums.ExchangeType
import app.vahid.domain.gateway.model.CurrencyRate
import app.vahid.domain.gateway.model.Transaction
import kotlinx.coroutines.flow.Flow

interface RateExchangerRepository {

    fun getBaseCurrency(): Flow<String>

    fun getCurrencyRateList(): Flow<List<CurrencyRate>>

    fun getCurrencyRate(currencyId: String): Flow<Double>

    fun getMyBalanceList(): Flow<List<Transaction>>

    fun exchangeCurrency(
        exchangeType: ExchangeType,
        amount: Double,
        currencyId: String,
        baseCurrencyId: String,
    ): Flow<WrappedResult<Unit>>


    fun addTransaction(transaction: Transaction): WrappedResult<Unit>

}
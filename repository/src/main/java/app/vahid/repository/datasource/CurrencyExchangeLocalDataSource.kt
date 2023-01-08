package app.vahid.repository.datasource

import app.vahid.common.core.WrappedResult
import app.vahid.domain.gateway.model.Transaction
import app.vahid.repository.model.BalanceEntity
import app.vahid.repository.model.CurrencyRateEntity
import kotlinx.coroutines.flow.Flow

interface CurrencyExchangeLocalDataSource {

    fun getBaseCurrency(): Flow<String>

    fun getCurrencyRateList(): Flow<List<CurrencyRateEntity>>

    fun getCurrencyRate(currencyId: String): Flow<Double>

    fun getBalanceList(): Flow<List<BalanceEntity>>

    suspend fun addTransaction(transaction: Transaction): WrappedResult<Unit>

    suspend fun addCurrencyRateList(list: List<CurrencyRateEntity>)
}
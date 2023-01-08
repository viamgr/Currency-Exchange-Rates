package app.vahid.domain.gateway.repository

import app.vahid.common.core.WrappedResult
import app.vahid.domain.gateway.model.Balance
import app.vahid.domain.gateway.model.CurrencyRate
import kotlinx.coroutines.flow.Flow

interface RateExchangerRepository {

    fun getCurrencyRate(): Flow<List<CurrencyRate>>

    fun getBalances(): Flow<List<Balance>>

    fun purchaseCurrency(
        amount: Double,
        currencyId: String,
        baseCurrencyId: String,
    ): Flow<WrappedResult<Boolean>>

    fun sellCurrency(
        amount: Double,
        currencyId: String,
        baseCurrencyId: String,
    ): Flow<WrappedResult<Boolean>>

}
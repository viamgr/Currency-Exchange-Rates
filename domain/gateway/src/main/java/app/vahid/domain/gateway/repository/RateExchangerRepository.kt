package app.vahid.domain.gateway.repository

import app.vahid.common.core.WrappedResult
import app.vahid.domain.gateway.model.Balance
import app.vahid.domain.gateway.model.CurrencyRate
import kotlinx.coroutines.flow.Flow

interface RateExchangerRepository {

    fun getBaseCurrency(): Flow<String>

    fun getCurrencyRateList(): Flow<List<CurrencyRate>>

    fun getCurrencyRate(currencyId: String): Flow<Double>

    fun getMyBalanceList(): Flow<List<Balance>>

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

    fun addPurchase(
        originCurrency: String,
        destinationCurrency: String,
        amount: Double,
    ): WrappedResult<Unit>

}
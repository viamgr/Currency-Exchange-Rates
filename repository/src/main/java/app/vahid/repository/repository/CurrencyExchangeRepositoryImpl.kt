package app.vahid.repository.repository

import app.vahid.common.core.WrappedResult
import app.vahid.common.core.map
import app.vahid.domain.gateway.model.Balance
import app.vahid.domain.gateway.model.CurrencyRate
import app.vahid.domain.gateway.model.Transaction
import app.vahid.domain.gateway.repository.CurrencyExchangeRepository
import app.vahid.repository.datasource.CurrencyExchangeLocalDataSource
import app.vahid.repository.datasource.CurrencyExchangeRemoteDataSource
import app.vahid.repository.mapper.BalanceMapper
import app.vahid.repository.mapper.CurrencyRateMapper
import app.vahid.repository.mapper.TransactionMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class CurrencyExchangeRepositoryImpl @Inject constructor(
    private val currencyExchangeLocalDataSource: CurrencyExchangeLocalDataSource,
    private val currencyExchangeRemoteDataSource: CurrencyExchangeRemoteDataSource,
    private val balanceMapper: BalanceMapper,
    private val currencyRateMapper: CurrencyRateMapper,
    private val transactionMapper: TransactionMapper,
) : CurrencyExchangeRepository {

    override fun getBaseCurrency(): Flow<String> {
        return currencyExchangeLocalDataSource.getBaseCurrency()
    }

    override fun getCurrencyRateList(): Flow<List<CurrencyRate>> {
        return currencyExchangeLocalDataSource.getCurrencyRateList()
            .map { rateEntities ->
                rateEntities.map {
                    currencyRateMapper(it)
                }
            }
    }

    override suspend fun updateCurrencyRateList(): WrappedResult<Unit> {
        return currencyExchangeRemoteDataSource.getCurrencyRateList()
            .map { currencyRates ->
                currencyExchangeLocalDataSource.addCurrencyRateList(
                    currencyRates.rates
                )
                currencyExchangeLocalDataSource.setBaseCurrency(currencyRates.base)
            }
    }

    override fun getTransactionCount(): Flow<Int> {
        return currencyExchangeLocalDataSource.getTransactionCount()
    }

    override suspend fun addCurrencyRateList(list: List<CurrencyRate>) {
        return currencyExchangeLocalDataSource
            .addCurrencyRateList(list = list.map {
                currencyRateMapper(it)
            })
    }

    override fun getCurrencyRate(currencyId: String): Flow<Double> {
        return currencyExchangeLocalDataSource.getCurrencyRate(currencyId)
    }

    override fun getBalanceList(): Flow<List<Balance>> {
        return currencyExchangeLocalDataSource
            .getBalanceList()
            .map { balanceEntities ->
                balanceEntities.map {
                    balanceMapper(it)
                }
            }
    }

    override fun exchangeCurrency(
        amount: Double,
        currencyId: String,
        baseCurrencyId: String,
    ): WrappedResult<Unit> {
        return currencyExchangeRemoteDataSource
            .exchangeCurrency(
                amount = amount,
                currencyId = currencyId,
                baseCurrencyId = baseCurrencyId
            )
    }

    override suspend fun addTransaction(transaction: Transaction): WrappedResult<Unit> {
        return currencyExchangeLocalDataSource
            .addTransaction(
                transaction = transactionMapper(transaction)
            )
    }

}
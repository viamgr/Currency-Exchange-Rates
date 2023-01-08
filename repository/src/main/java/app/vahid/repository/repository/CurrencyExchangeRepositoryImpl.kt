package app.vahid.repository.repository

import app.vahid.common.core.WrappedResult
import app.vahid.common.core.map
import app.vahid.domain.gateway.model.Balance
import app.vahid.domain.gateway.model.CurrencyRate
import app.vahid.domain.gateway.model.Transaction
import app.vahid.domain.gateway.repository.CurrencyExchangeRepository
import app.vahid.repository.datasource.ExchangeCurrencyLocalDataSource
import app.vahid.repository.datasource.ExchangeCurrencyRemoteDataSource
import app.vahid.repository.mapper.BalanceMapper
import app.vahid.repository.mapper.CurrencyRateMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class CurrencyExchangeRepositoryImpl @Inject constructor(
    private val exchangeCurrencyLocalDataSource: ExchangeCurrencyLocalDataSource,
    private val exchangeCurrencyRemoteDataSource: ExchangeCurrencyRemoteDataSource,
    private val balanceMapper: BalanceMapper,
    private val currencyRateMapper: CurrencyRateMapper,
) : CurrencyExchangeRepository {

    override fun getBaseCurrency(): Flow<String> {
        return exchangeCurrencyLocalDataSource.getBaseCurrency()
    }

    override fun getCurrencyRateList(): Flow<List<CurrencyRate>> {
        return exchangeCurrencyLocalDataSource.getCurrencyRateList()
            .map { rateEntities ->
                rateEntities.map {
                    currencyRateMapper(it)
                }
            }
    }

    override suspend fun updateCurrencyRateList(): WrappedResult<Unit> {
        return exchangeCurrencyRemoteDataSource.getCurrencyRateList()
            .map { currencyRates ->
                exchangeCurrencyLocalDataSource.addCurrencyRateList(
                    currencyRates.map {
                        currencyRateMapper(it)
                    }
                )
            }
    }

    override suspend fun addCurrencyRateList(list: List<CurrencyRate>) {
        return exchangeCurrencyLocalDataSource
            .addCurrencyRateList(list = list.map {
                currencyRateMapper(it)
            })
    }

    override fun getCurrencyRate(currencyId: String): Flow<Double> {
        return exchangeCurrencyLocalDataSource.getCurrencyRate(currencyId)
    }

    override fun getBalanceList(): Flow<List<Balance>> {
        return exchangeCurrencyLocalDataSource
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
        return exchangeCurrencyRemoteDataSource
            .exchangeCurrency(
                amount = amount,
                currencyId = currencyId,
                baseCurrencyId = baseCurrencyId
            )
    }

    override suspend fun addTransaction(transaction: Transaction): WrappedResult<Unit> {
        return exchangeCurrencyLocalDataSource.addTransaction(transaction = transaction)
    }

}
package app.vahid.datasource.cache.database.datasource

import app.vahid.common.core.WrappedResult
import app.vahid.common.core.toResult
import app.vahid.datasource.cache.database.AppDatabase
import app.vahid.datasource.cache.database.dao.SettingKeys
import app.vahid.datasource.cache.database.entity.CachedCurrencyRate
import app.vahid.datasource.cache.database.entity.CachedSetting
import app.vahid.datasource.cache.database.entity.CachedTransaction
import app.vahid.repository.datasource.CurrencyExchangeLocalDataSource
import app.vahid.repository.model.BalanceEntity
import app.vahid.repository.model.CurrencyRateEntity
import app.vahid.repository.model.TransactionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import timber.log.Timber
import java.math.BigDecimal
import javax.inject.Inject

internal class CurrencyExchangeLocalDataSourceImpl @Inject constructor(
    private val appDatabase: AppDatabase,
) : CurrencyExchangeLocalDataSource {
    override fun getBaseCurrency(): Flow<String> {
        return appDatabase.settingDao().getSetting(SettingKeys.BaseCurrency.name)
    }

    override suspend fun setBaseCurrency(baseCurrency: String) {
        return appDatabase.settingDao()
            .insert(
                CachedSetting(SettingKeys.BaseCurrency.name, baseCurrency)
            )
    }

    override fun getTransactionCount(): Flow<Int> {
        Timber.d("getTransactionCount")

        return appDatabase
            .transactionDao()
            .getTransactionCount().onEach {
                Timber.d("getTransactionCount $it")
            }.onEmpty {
                Timber.d("getTransactionCount  onEmpty ")
                emit(0)
            }
    }

    override fun getCurrencyRateList(): Flow<List<CurrencyRateEntity>> {
        return appDatabase.currencyRateDao()
            .getCurrencyRateList()
            .filterNotNull()
            .map { rates ->
                rates.map { CurrencyRateEntity(currencyId = it.id, rate = it.rate) }
            }
    }

    override fun getCurrencyRate(currencyId: String): Flow<Double> {
        return appDatabase
            .currencyRateDao()
            .getCurrencyRate(currencyId)
            .filterNotNull()
    }

    override fun getBalanceList(): Flow<List<BalanceEntity>> {
        return appDatabase
            .transactionDao()
            .getTransactionList()
            .filterNotNull()
            .map { balances ->

                val outcome = balances
                    .groupBy { it.originCurrency }
                    .mapValues { entry ->
                        entry.value.sumOf { it.originAmount + (it.originAmount * it.fee.toBigDecimal()) }
                    }

                val income = balances
                    .groupBy { it.destinationCurrency }
                    .mapValues { entry ->
                        entry.value.sumOf {
                            it.destinationAmount - (it.destinationAmount * it.fee.toBigDecimal())
                        }
                    }

                (outcome.keys + income.keys).map {
                    val amount = income.getOrDefault(it, BigDecimal.ZERO) - outcome.getOrDefault(it,
                        BigDecimal.ZERO)
                    BalanceEntity(
                        currencyId = it,
                        amount = amount
                    )
                }
            }
            .filterNotNull()

    }

    override suspend fun addTransaction(transaction: TransactionEntity): WrappedResult<Unit> {
        return appDatabase.transactionDao()
            .insert(
                CachedTransaction(
                    originCurrency = transaction.originCurrency,
                    destinationCurrency = transaction.destinationCurrency,
                    originAmount = transaction.originAmount,
                    destinationAmount = transaction.destinationAmount,
                    fee = transaction.fee
                )
            ).toResult()
    }

    override suspend fun addCurrencyRateList(list: List<CurrencyRateEntity>) {
        appDatabase.currencyRateDao()
            .insert(*list
                .map { CachedCurrencyRate(id = it.currencyId, rate = it.rate) }
                .toTypedArray()
            )
    }

}
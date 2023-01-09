package app.vahid.datasource.cache.database.dao

import androidx.room.Dao
import androidx.room.Query
import app.vahid.datasource.cache.database.entity.CachedCurrencyRate
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyRateDao : BaseDao<CachedCurrencyRate> {

    @Query("SELECT rate from currency_rate WHERE id=:currencyId")
    fun getCurrencyRate(currencyId: String): Flow<Double>

    @Query("SELECT * from currency_rate")
    fun getCurrencyRateList(): Flow<List<CachedCurrencyRate>>

}
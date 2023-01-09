package app.vahid.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.vahid.datasource.cache.database.dao.CurrencyRateDao
import app.vahid.datasource.cache.database.dao.SettingDao
import app.vahid.datasource.cache.database.dao.TransactionDao
import app.vahid.datasource.cache.database.entity.CachedCurrencyRate
import app.vahid.datasource.cache.database.entity.CachedSetting
import app.vahid.datasource.cache.database.entity.CachedTransaction
import app.vahid.datasource.cache.database.type_converters.BigDecimalTypeConverter

@TypeConverters(
    BigDecimalTypeConverter::class,
)
@Database(
    entities = [
        CachedCurrencyRate::class,
        CachedTransaction::class,
        CachedSetting::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyRateDao(): CurrencyRateDao
    abstract fun transactionDao(): TransactionDao
    abstract fun settingDao(): SettingDao
}
package app.vahid.datasource.cache.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_rate")
data class CachedCurrencyRate(
    @PrimaryKey var id: String,
    var rate: Double,
)
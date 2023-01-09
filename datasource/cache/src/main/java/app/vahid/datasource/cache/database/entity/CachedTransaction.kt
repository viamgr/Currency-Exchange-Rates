package app.vahid.datasource.cache.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "transaction",
    indices = [
        Index(value = ["originCurrency"]),
        Index(value = ["destinationCurrency"]),
        Index(value = ["originAmount"]),
        Index(value = ["destinationAmount"])
    ])

data class CachedTransaction(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val originCurrency: String,
    val destinationCurrency: String,
    val originAmount: Double,
    val destinationAmount: Double,
)
package app.vahid.datasource.cache.database.model

data class CachedBalance(
    val currencyId: String,
    val originAmount: Double,
    val destinationAmount: Double,
)
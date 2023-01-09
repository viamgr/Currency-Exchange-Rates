package app.vahid.repository.model

import java.math.BigDecimal

data class TransactionEntity(
    val originCurrency: String,
    val destinationCurrency: String,
    val originAmount: BigDecimal,
    val destinationAmount: BigDecimal,
    val fee: Double,
)
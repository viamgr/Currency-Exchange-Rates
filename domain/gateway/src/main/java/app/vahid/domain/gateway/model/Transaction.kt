package app.vahid.domain.gateway.model

import java.math.BigDecimal

data class Transaction(
    val originCurrency: String,
    val destinationCurrency: String,
    val originAmount: BigDecimal,
    val destinationAmount: BigDecimal,
    val fee: Double,
)
package app.vahid.domain.gateway.model

import app.vahid.common.core.enums.ExchangeType

data class Transaction(
    val exchangeType: ExchangeType,
    val originCurrency: String,
    val destinationCurrency: String,
    val amount: Double,
)
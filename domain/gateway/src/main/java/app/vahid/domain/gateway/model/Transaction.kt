package app.vahid.domain.gateway.model


data class Transaction(
    val originCurrency: String,
    val destinationCurrency: String,
    val amount: Double,
)
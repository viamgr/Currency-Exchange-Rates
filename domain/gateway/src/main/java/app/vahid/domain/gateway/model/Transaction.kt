package app.vahid.domain.gateway.model


data class Transaction(
    val originCurrency: String,
    val destinationCurrency: String,
    val originAmount: Double,
    val destinationAmount: Double,
    val fee: Double,
)
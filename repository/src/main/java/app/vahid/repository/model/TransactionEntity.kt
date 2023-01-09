package app.vahid.repository.model

data class TransactionEntity(
    val originCurrency: String,
    val destinationCurrency: String,
    val originAmount: Double,
    val destinationAmount: Double,
    val fee: Double,
)
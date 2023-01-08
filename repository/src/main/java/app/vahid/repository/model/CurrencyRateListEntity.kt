package app.vahid.repository.model

data class CurrencyRateListEntity(
    val base: String,
    val date: String,
    val rates: List<CurrencyRateEntity>,
)
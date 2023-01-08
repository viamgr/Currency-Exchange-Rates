package app.vahid.datasource.remote.response


import app.vahid.datasource.remote.types.CurrencyExchangeRateFormat
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RateResponse(
    @SerialName("base")
    val base: String,
    @SerialName("date")
    val date: String,
    @SerialName("rates")
    val rates: CurrencyExchangeRateFormat,
)
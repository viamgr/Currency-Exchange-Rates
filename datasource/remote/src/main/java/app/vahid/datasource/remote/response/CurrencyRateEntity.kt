package app.vahid.datasource.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyRateResponse(val currencyId: String, val rate: Double)
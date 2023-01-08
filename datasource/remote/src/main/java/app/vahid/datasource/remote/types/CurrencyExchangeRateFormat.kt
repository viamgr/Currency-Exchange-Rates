package app.vahid.datasource.remote.types

import app.vahid.repository.model.CurrencyRateEntity
import kotlinx.serialization.Serializable

@Serializable(with = CurrencyExchangeRateFormatSerializer::class)
class CurrencyExchangeRateFormat(val value: List<CurrencyRateEntity>)
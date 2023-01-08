package app.vahid.datasource.remote.types

import app.vahid.repository.model.CurrencyRateEntity
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object CurrencyExchangeRateFormatSerializer : KSerializer<CurrencyExchangeRateFormat> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("CurrencyExchangeRateFormat", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: CurrencyExchangeRateFormat) {
        encoder.encodeString(Json.encodeToString(value.value))
    }

    override fun deserialize(decoder: Decoder): CurrencyExchangeRateFormat {
        return Json.parseToJsonElement(decoder.decodeString()).jsonObject.map {
            CurrencyRateEntity(currencyId = it.key, rate = it.value.jsonPrimitive.double)
        }.let {
            CurrencyExchangeRateFormat(it)
        }

    }
}
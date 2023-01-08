package app.vahid.datasource.remote.types

import app.vahid.datasource.remote.response.CurrencyRateResponse
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object CurrencyExchangeRateFormatSerializer :
    JsonTransformingSerializer<List<CurrencyRateResponse>>(ListSerializer(CurrencyRateResponse.serializer())) {

    override fun transformDeserialize(element: JsonElement): JsonElement {
        val list: List<JsonObject> = element.jsonObject.map {
            JsonObject(mapOf("currencyId" to JsonPrimitive(it.key),
                "rate" to JsonPrimitive(it.value.jsonPrimitive.double)))
        }
        return JsonArray(list)
    }

}
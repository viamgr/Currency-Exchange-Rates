package app.vahid.datasource.remote.api

import app.vahid.common.core.WrappedResult
import app.vahid.datasource.remote.response.RateResponse
import retrofit2.http.GET

internal interface CurrencyExchangeApi {

    @GET("/tasks/api/currency-exchange-rates")
    fun getRates(): WrappedResult<RateResponse>

}
package app.vahid.datasource.remote.base

import app.vahid.common.core.errors.ApiError
import retrofit2.Response
import javax.inject.Inject

internal class ApiErrorMapper @Inject constructor() {
    fun <T> parse(response: Response<T>) =
        response.errorBody()?.string()?.let {
            ApiError(it)
        }
}
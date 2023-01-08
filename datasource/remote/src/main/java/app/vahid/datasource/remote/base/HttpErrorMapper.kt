package app.vahid.datasource.remote.base

import app.vahid.common.core.errors.ForbiddenError
import app.vahid.common.core.errors.ItemNotFound
import retrofit2.Response
import javax.inject.Inject

internal class HttpErrorMapper @Inject constructor() {
    fun <T> parse(response: Response<T>) =
        when (response.code()) {
            403 -> ForbiddenError
            404 -> ItemNotFound
            else -> null
        }
}
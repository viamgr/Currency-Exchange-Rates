package app.vahid.common.presentation.error_handling

import app.vahid.common.core.WrappedResult
import app.vahid.common.core.cacheMap
import app.vahid.common.core.errors.ApiError
import app.vahid.common.core.errors.BaseError
import app.vahid.common.core.errors.ForbiddenError
import app.vahid.common.core.errors.HttpError
import app.vahid.common.core.errors.ItemNotFound
import app.vahid.common.core.errors.NoBody
import app.vahid.common.core.errors.NoInternet
import app.vahid.common.core.errors.Timeout
import app.vahid.common.core.errors.UnknownHost
import app.vahid.common.core.errors.Unreachable
import app.vahid.common.presentation.R

fun <T> WrappedResult<T>.cacheErrors(): WrappedResult<T> =
    cacheMap { baseError: BaseError, throwable ->
        baseError.uiErrorType.let {
            WrappedResult.failure(it, throwable)
        }
    }

private val BaseError.uiErrorType: UiErrorType
    get() {
        return when (this) {
            NoInternet -> IntResError(R.string.remote_error_no_internet_description)
            is ItemNotFound -> IntResError(R.string.remote_error_item_not_found)
            UnknownHost, Timeout, is ApiError, ForbiddenError, is HttpError, NoBody, Unreachable -> IntResError(
                R.string.remote_error_server_description)
        }
    }
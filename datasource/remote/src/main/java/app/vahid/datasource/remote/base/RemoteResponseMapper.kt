package app.vahid.datasource.remote.base

import app.vahid.common.core.WrappedResult
import app.vahid.common.core.errors.HttpError
import app.vahid.common.core.errors.NoBody
import app.vahid.common.core.errors.NoInternet
import app.vahid.common.core.errors.RemoteError
import app.vahid.common.core.errors.Timeout
import app.vahid.common.core.errors.UnknownHost
import app.vahid.common.core.errors.Unreachable
import app.vahid.common.core.toResult
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class RemoteResponseMapper @Inject constructor(
    private val apiErrorMapper: ApiErrorMapper,
    private val httpErrorMapper: HttpErrorMapper,
) {

    private fun <T> wrapResponse(response: Response<T>) =
        if (response.isSuccessful) {
            parseResponseBody(response)
        } else {
            mapFailureResponse(response, parseRemoteErrors(response))
        }

    private fun <T> mapFailureResponse(
        response: Response<T>,
        remoteError: RemoteError,
    ): WrappedResult<T> = HttpException(response).toResult(remoteError)

    private fun <T> parseRemoteErrors(response: Response<T>) =
        httpErrorMapper.parse(response) ?: apiErrorMapper.parse(response)
        ?: HttpError(response.code(), response.message())


    @Suppress("UNCHECKED_CAST")
    private fun <T> parseResponseBody(
        response: Response<T>,
    ): WrappedResult<T> {
        val body = response.body()
        return body?.let {
            WrappedResult.success(it)
        } ?: WrappedResult.failure(NoBody, IllegalStateException())
    }

    fun <T, R> map(executeBlock: () -> Response<T>): R {
        return innerMap(executeBlock) as R
    }

    private fun <T> innerMap(
        executeBlock: () -> Response<T>,
    ): WrappedResult<T> {
        return try {
            wrapResponse(executeBlock())
        } catch (hostException: UnknownHostException) {
            hostException.toResult(UnknownHost)
        } catch (timeoutException: SocketTimeoutException) {
            timeoutException.toResult(Timeout)
        } catch (ioException: java.io.IOException) {
            ioException.toResult(NoInternet)
        } catch (exception: Exception) {
            exception.toResult(Unreachable)
        }

    }
}
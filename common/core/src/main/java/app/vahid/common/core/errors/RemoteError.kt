package app.vahid.common.core.errors

sealed interface RemoteError : BaseError
sealed interface NetworkError : RemoteError
sealed interface ServerError : RemoteError

object Timeout : NetworkError
object UnknownHost : NetworkError
object NoInternet : NetworkError

object NoBody : ServerError
object ForbiddenError : ServerError

data class HttpError(val code: Int, val message: String) : ServerError
data class ApiError(val error: String) : ServerError

object ItemNotFound : ServerError
object Unreachable : ServerError
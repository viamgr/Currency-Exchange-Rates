package app.vahid.common.core

import app.vahid.common.core.WrappedResult.Companion.data
import app.vahid.common.core.WrappedResult.Companion.errorTypeOrNull
import app.vahid.common.core.WrappedResult.Companion.failure
import app.vahid.common.core.WrappedResult.Companion.throwableOrNull
import timber.log.Timber

sealed class WrappedResult<T>() {
    val isSuccess: Boolean get() = this !is Failure<*, *>
    val isFailure: Boolean get() = this is Failure<*, *>

    companion object {
        fun <T> success(value: T): WrappedResult<T> =
            Success(value)

        fun <T, E> failure(errorType: E, throwable: Throwable): WrappedResult<T> {
            return Failure(errorType, throwable)
        }

        fun <T, E> WrappedResult<T>.errorTypeOrNull(): E? {
            @Suppress("UNCHECKED_CAST")
            return if (this is Failure<*, *> && errorType != null) {
                return errorType as E
            } else null
        }

        fun <T> WrappedResult<T>.throwableOrNull(): Throwable? {
            return if (this is Failure<*, *>) {
                return throwable
            } else null
        }

        fun <T> WrappedResult<T>.data(): T? {
            return if (isSuccess) (this as Success<T>).value else null
        }

    }

    internal class Failure<T, E>(
        val errorType: E,
        val throwable: Throwable,
    ) : WrappedResult<T>()


    internal class Success<T>(val value: T) : WrappedResult<T>()

}


fun <T, E> WrappedResult<T>.requireErrorType(): E {
    return errorTypeOrNull<T, E>()!!
}

fun <T> WrappedResult<T>.requireThrowable(): Throwable {
    return throwableOrNull()!!
}

internal fun WrappedResult<*>.throwOnFailure() {
    if (this is WrappedResult.Failure<*, *>) throw throwable
}


fun <T> WrappedResult<T>.requireData(): T {
    throwOnFailure()
    return this.data()!!
}


inline fun <R, T, reified E> WrappedResult<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (errorType: E, throwable: Throwable) -> R,
): R {
    return if (this.isFailure) {
        val errorTypeOrNull: E? = errorTypeOrNull<T, E>()
        val throwable = throwableOrNull()!!

        val genericType = errorTypeOrNull?.takeIf {
            it is E
        }
        genericType?.let {
            onFailure(errorTypeOrNull, throwable)
        }
            ?: throw Exception("${E::class.java} should be super class of ${errorTypeOrNull!!::class.java} ")
    } else {
        onSuccess(requireData())
    }
}


inline fun <T, reified E> WrappedResult<T>.cacheMap(
    onFailure: (errorType: E, throwable: Throwable) -> WrappedResult<T>,
): WrappedResult<T> {
    val failure = this.isFailure
    return if (failure) {
        val errorTypeOrNull: E? = errorTypeOrNull<T, E>()
        val throwable = throwableOrNull()!!
        val genericType = errorTypeOrNull?.takeIf {
            it is E
        }
        return genericType?.let {
            onFailure(errorTypeOrNull, throwable).also {
                Timber.e(throwable)
            }
        } ?: this
    } else {
        this
    }
}

inline fun <T, R> WrappedResult<T>.map(
    onSuccess: (value: T) -> R,
): WrappedResult<R> {
    val failure = this.isFailure
    return if (failure) {
        val errorTypeOrNull = errorTypeOrNull<T, Any?>()
        val throwable = throwableOrNull()!!
        failure(errorTypeOrNull, throwable)
    } else {
        WrappedResult.success(onSuccess(requireData()))
    }
}

inline fun <T, R> WrappedResult<List<T>>.mapList(
    onSuccess: (value: T) -> R,
): WrappedResult<List<R>> = map {
    it.map(onSuccess)
}

inline fun <T> WrappedResult<T>.onSuccess(action: (value: T) -> Unit): WrappedResult<T> {
    @Suppress("UNCHECKED_CAST")
    if (isSuccess) action(requireData())
    return this
}

public inline fun <T, E> WrappedResult<T>.onFailure(
    onFailure: (errorType: E, throwable: Throwable) -> T,
): WrappedResult<T> {
    throwableOrNull()?.let { onFailure(requireErrorType(), it) }
    return this
}

fun <R> R.toResult(): WrappedResult<R> {
    return WrappedResult.success(this)
}

fun <R, E> Throwable.toResult(errorType: E): WrappedResult<R> {
    return WrappedResult.failure<R, E>(errorType, this)
}

suspend fun <T, R> WrappedResult<T>.flatMap(fn: suspend (T) -> WrappedResult<R>): WrappedResult<R> =
    when (this) {
        is WrappedResult.Failure<*, *> -> failure<R, Exception>(
            requireErrorType(),
            requireThrowable()
        )
        is WrappedResult.Success -> fn(requireData())
    }


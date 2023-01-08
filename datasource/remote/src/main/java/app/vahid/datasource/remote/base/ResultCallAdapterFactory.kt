package app.vahid.datasource.remote.base

import app.vahid.common.core.WrappedResult
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject

internal class ResultCallAdapterFactory @Inject constructor(
    private val remoteResponseMapper: RemoteResponseMapper,
    private val resultCallAdapter: ResultCallAdapter.AdapterFactory,
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {

        if (getRawType(returnType) != WrappedResult::class.java) return null
        check(returnType is ParameterizedType) { "Return type must be a parameterized type." }
        //Check return type is ParameterizedType = Wrapped result type.

        val actualType = getParameterUpperBound(0, returnType)

        return resultCallAdapter.adapt(actualType, remoteResponseMapper)
    }
}
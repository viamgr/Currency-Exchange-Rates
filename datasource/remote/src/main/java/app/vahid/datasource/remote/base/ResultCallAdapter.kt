package app.vahid.datasource.remote.base

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class ResultCallAdapter<R, T : Any> @AssistedInject constructor(
    @Assisted private val remoteResponseMapper: RemoteResponseMapper,
    @Assisted private val successType: Type,
) : CallAdapter<R, T> {

    @AssistedFactory
    interface AdapterFactory {
        fun adapt(successType: Type, mapper: RemoteResponseMapper): ResultCallAdapter<Any, Any>
    }

    override fun responseType(): Type = successType

    override fun adapt(call: Call<R>): T {
        return remoteResponseMapper.map {
            call.execute()
        }
    }
}
package app.vahid.common.usecase_common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<P, R>(private val dispatcher: CoroutineDispatcher) {

    operator fun invoke(parameter: P): Flow<R> {
        return execute(parameter)
            .flowOn(dispatcher)

    }

    @Throws(RuntimeException::class)
    protected abstract fun execute(parameter: P): Flow<R>
}
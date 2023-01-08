package app.vahid.common.usecase_common

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<P, R>() {

    operator fun invoke(parameter: P): Flow<R> {
        return execute(parameter)
    }

    @Throws(RuntimeException::class)
    protected abstract fun execute(parameter: P): Flow<R>
}
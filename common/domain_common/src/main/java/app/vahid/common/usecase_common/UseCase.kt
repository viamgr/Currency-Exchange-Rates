package app.vahid.common.usecase_common

abstract class UseCase<P, R> {

    operator fun invoke(parameter: P): R {
        return execute(parameter)
    }

    @Throws(RuntimeException::class)
    protected abstract fun execute(parameter: P): R
}
package app.vahid.common.test_shared

import io.kotest.core.spec.style.scopes.RootScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
internal object TestDispatchers {

    val UnconfinedTestDispatcher = UnconfinedTestDispatcher()

    val StandardTestDispatcher = StandardTestDispatcher()

}

@OptIn(ExperimentalCoroutinesApi::class)
val RootScope.dispatcher: TestDispatcher
    get() = TestDispatchers.UnconfinedTestDispatcher

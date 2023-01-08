package app.vahid.common.presentation

import app.cash.turbine.test
import app.vahid.common.presentation.fake.FakeCounterState
import app.vahid.common.presentation.fake.FakeEffect
import app.vahid.common.presentation.fake.FakeEffect1
import app.vahid.common.presentation.fake.FakeEffect2
import app.vahid.common.presentation.fake.FakeIntent
import app.vahid.common.presentation.fake.IntentOne
import app.vahid.common.presentation.fake.IntentTwo
import app.vahid.common.presentation.pattern.Event
import app.vahid.common.presentation.pattern.Reducer
import app.vahid.common.presentation.pattern.SideEffect
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestScope
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import org.orbitmvi.orbit.container
import kotlin.coroutines.CoroutineContext

@OptIn(DelicateCoroutinesApi::class)
class OrbitUtilsBehaviorSpec : BehaviorSpec() {

    init {

        Given("An orbit container host") {

            val initialState = FakeCounterState(0)
            val realOrbitContainer = container<FakeCounterState, SideEffect>(initialState)

            val mockContainer =
                mockk<ReducerContainerHost<FakeIntent, FakeCounterState, FakeEffect, SideEffect, Event>>()

            every { mockContainer.container } returns realOrbitContainer

            And("be it placed in a multi-thread environment") {

                val initialValue = mockContainer.container.stateFlow.value.count
                val effectsOfIntent1 = flowOf(FakeEffect1)
                val effectsOfIntent2 = flowOf(FakeEffect2)
                val effectValue1 = 1
                val effectValue2 = 1

                val testIntentRepeat1 = 1000
                val testIntentRepeat2 = 1000

                val expectedFinalResult =
                    (testIntentRepeat1 * effectValue1) + (testIntentRepeat2 * effectValue2)

                val fakeReducer = object : Reducer<FakeCounterState, FakeEffect> {
                    override fun reduce(
                        state: FakeCounterState,
                        effect: FakeEffect,
                    ): FakeCounterState {
                        return when (effect) {
                            FakeEffect1 -> state.copy(count = state.count + effectValue1)
                            FakeEffect2 -> state.copy(count = state.count + effectValue2)
                        }
                    }
                }

                every { mockContainer.reducer } returns fakeReducer
                coEvery { mockContainer.handleIntent(IntentOne) } returns effectsOfIntent1
                coEvery { mockContainer.handleIntent(IntentTwo) } returns effectsOfIntent2

                mockContainer.container.stateFlow.test {
                    When("multiple intents are dispatched at the same time on two different threads") {

                        Then("the first emitted state should be equal to that initial value") {
                            awaitItem().count shouldBe initialValue
                        }

                        Then("state flow should be sequentially collected in the same as the order of the reduced events that are emitted") {
                            joinAll(
                                runInThread(mockContainer,
                                    IntentOne,
                                    testIntentRepeat1,
                                    newFixedThreadPoolContext(10, "Test1")),
                                runInThread(mockContainer,
                                    IntentTwo,
                                    testIntentRepeat2,
                                    newFixedThreadPoolContext(10, "Test2")),
                            )

                            repeat(testIntentRepeat1 + testIntentRepeat2) {
                                awaitItem().count shouldBe it + 1
                            }

                            cancelAndIgnoreRemainingEvents()
                        }

                        Then("the final result should be set correctly") {
                            mockContainer.container.stateFlow.value.count shouldBe expectedFinalResult
                        }
                    }
                }
            }

            And("be it placed in a single-thread environment") {

                val effectsOfIntent1 = flowOf(FakeEffect1, FakeEffect2)
                val effectsOfIntent2 = flowOf(FakeEffect2, FakeEffect1)
                val effectValue1 = -20
                val effectValue2 = 50
                val initialValue = mockContainer.container.stateFlow.value.count

                val expectedFirstIntentReducedValue = initialValue + effectValue1 + effectValue2
                val expectedSecondIntentReducedValue =
                    expectedFirstIntentReducedValue + effectValue1 + effectValue2

                val fakeReducer = object : Reducer<FakeCounterState, FakeEffect> {
                    override fun reduce(
                        state: FakeCounterState,
                        effect: FakeEffect,
                    ): FakeCounterState {
                        return when (effect) {
                            FakeEffect1 -> state.copy(count = state.count + effectValue1)
                            FakeEffect2 -> state.copy(count = state.count + effectValue2)
                        }
                    }
                }

                every { mockContainer.reducer } returns fakeReducer
                coEvery { mockContainer.handleIntent(IntentOne) } returns effectsOfIntent1
                coEvery { mockContainer.handleIntent(IntentTwo) } returns effectsOfIntent2

                mockContainer.container.stateFlow.test {

                    When("an intent get dispatched immediately after another one") {

                        mockContainer.dispatchIntent(IntentOne)
                        mockContainer.dispatchIntent(IntentTwo)

                        Then("the first emitted state should be equal to that initial value") {
                            awaitItem().count shouldBe initialValue
                        }

                        Then("the second emitted state should be equal to the first reduced value") {
                            awaitItem().count shouldBe expectedFirstIntentReducedValue
                        }

                        Then("the third emitted state should be equal to the first reduced value") {
                            awaitItem().count shouldBe expectedSecondIntentReducedValue
                        }

                        And("ignore remaining events") {
                            cancelAndIgnoreRemainingEvents()
                        }

                        Then("the final result should be set correctly") {
                            mockContainer.container.stateFlow.value.count shouldBe expectedSecondIntentReducedValue
                        }
                    }
                }
            }
        }
    }

    private fun TestScope.runInThread(
        testSubject: ReducerContainerHost<FakeIntent, FakeCounterState, FakeEffect, SideEffect, Event>,
        intent: FakeIntent,
        repeat: Int,
        executorCoroutineDispatcher: CoroutineContext,
    ) = launch(context = executorCoroutineDispatcher) {
        repeat(repeat) {
            testSubject.dispatchIntent(intent)
        }
    }
}

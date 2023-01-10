package app.vahid.feature.currency_exchange.presentation.entry_list

import app.cash.turbine.test
import app.vahid.common.core.WrappedResult
import app.vahid.common.presentation.dispatchIntent
import app.vahid.domain.gateway.model.CurrencyRate
import app.vahid.domain.use_case.ExchangeCurrencyUseCase
import app.vahid.domain.use_case.GetCurrencyRateListUseCase
import app.vahid.domain.use_case.GetCurrencyRatioUseCase
import app.vahid.domain.use_case.GetFeeUseCase
import app.vahid.domain.use_case.GetMyBalanceListUseCase
import app.vahid.domain.use_case.UpdateCurrencyRateListUseCase
import app.vahid.feature.currency_exchange.presentation.exchanger.ExchangerViewModel
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerIntent
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerReducer
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerState
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import org.orbitmvi.orbit.test

class ExchangerViewModelBehaviorSpec : BehaviorSpec() {

    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerTest

    init {
        Given("mocked exchanger view model dependencies") {

            val exchangeCurrencyUseCase = mockk<ExchangeCurrencyUseCase>()
            val getCurrencyRatioUseCase = mockk<GetCurrencyRatioUseCase>()
            val getMyBalanceListUseCase = mockk<GetMyBalanceListUseCase>()
            val getCurrencyRateListUseCase = mockk<GetCurrencyRateListUseCase>()
            val updateCurrencyRateListUseCase = mockk<UpdateCurrencyRateListUseCase>()
            val reducer = ExchangerReducer()
            val getFeeUseCase = mockk<GetFeeUseCase>()

            When("initiate the exchanger view model with empty data") {

                val initialSate = ExchangerState()
                coEvery { getCurrencyRatioUseCase(any()) } returns Unit
                every { getCurrencyRatioUseCase.flow } returns emptyFlow()
                coEvery { getMyBalanceListUseCase(Unit) } returns emptyFlow()
                every { getCurrencyRateListUseCase(Unit) } returns flowOf(emptyList())
                coEvery { updateCurrencyRateListUseCase(Unit) } returns flowOf(WrappedResult.success(
                    Unit))
                coEvery { getFeeUseCase(Unit) } returns flowOf(0.0)


                val viewModel = ExchangerViewModel(
                    exchangeCurrencyUseCase = exchangeCurrencyUseCase,
                    getCurrencyRatioUseCase = getCurrencyRatioUseCase,
                    getMyBalanceListUseCase = getMyBalanceListUseCase,
                    getCurrencyRateListUseCase = getCurrencyRateListUseCase,
                    updateCurrencyRateListUseCase = updateCurrencyRateListUseCase,
                    reducer = reducer,
                    getFeeUseCase = getFeeUseCase,
                )

                Then("Initial state should be set correctly") {
                    viewModel.container.stateFlow.test {

                        awaitItem() shouldBe initialSate
                    }
                }
            }

            When("initiate the exchanger view model with a fake currency rate list") {

                val currencyRateList = listOf(
                    CurrencyRate(
                        currencyId = "EUR",
                        rate = 1000.0
                    )
                )
                val balanceList = currencyRateList.map { it.currencyId }
                val initialSate = ExchangerState()

                coEvery { getCurrencyRatioUseCase(any()) } returns Unit
                every { getCurrencyRatioUseCase.flow } returns emptyFlow()
                coEvery { getMyBalanceListUseCase(Unit) } returns emptyFlow()
                every { getCurrencyRateListUseCase(Unit) } returns flowOf(currencyRateList)
                coEvery { updateCurrencyRateListUseCase(Unit) } returns flowOf(WrappedResult.success(
                    Unit))
                coEvery { getFeeUseCase(Unit) } returns flowOf(0.0)


                val viewModel = ExchangerViewModel(
                    exchangeCurrencyUseCase = exchangeCurrencyUseCase,
                    getCurrencyRatioUseCase = getCurrencyRatioUseCase,
                    getMyBalanceListUseCase = getMyBalanceListUseCase,
                    getCurrencyRateListUseCase = getCurrencyRateListUseCase,
                    updateCurrencyRateListUseCase = updateCurrencyRateListUseCase,
                    reducer = reducer,
                    getFeeUseCase = getFeeUseCase
                )

                Then("destinationRateList should be set correctly") {
                    viewModel.container.stateFlow.test {
                        awaitItem() shouldBe initialSate.copy(
                            destinationRateList = balanceList,
                            selectedDestinationCurrency = "EUR",
                        )
                        cancelAndConsumeRemainingEvents()
                    }
                }

            }


            When("dispatch an OnOriginCurrencyUpdated intent") {

                val initialSate = ExchangerState()
                coEvery { getCurrencyRatioUseCase(any()) } returns Unit
                every { getCurrencyRatioUseCase.flow } returns emptyFlow()
                coEvery { getMyBalanceListUseCase(Unit) } returns emptyFlow()
                every { getCurrencyRateListUseCase(Unit) } returns flowOf(emptyList())
                coEvery { updateCurrencyRateListUseCase(Unit) } returns flowOf(WrappedResult.success(
                    Unit))
                coEvery { getFeeUseCase(Unit) } returns flowOf(0.0)

                val viewModel = ExchangerViewModel(
                    exchangeCurrencyUseCase = exchangeCurrencyUseCase,
                    getCurrencyRatioUseCase = getCurrencyRatioUseCase,
                    getMyBalanceListUseCase = getMyBalanceListUseCase,
                    getCurrencyRateListUseCase = getCurrencyRateListUseCase,
                    updateCurrencyRateListUseCase = updateCurrencyRateListUseCase,
                    reducer = reducer,
                    getFeeUseCase = getFeeUseCase
                )

                val testSubject = viewModel.test()
                testSubject.testIntent {
                    viewModel.dispatchIntent(ExchangerIntent.OnOriginCurrencyUpdated("EUR"))
                }

                testSubject.assert(initialSate) {
                    states(
                        { initialSate },
                        { copy(selectedOriginCurrency = "EUR") },
                    )
                }
            }

            When("dispatch an OnDestinationCurrencyUpdated intent") {

                val initialSate = ExchangerState()

                coEvery { getCurrencyRatioUseCase(any()) } returns Unit
                every { getCurrencyRatioUseCase.flow } returns emptyFlow()

                coEvery { getMyBalanceListUseCase(Unit) } returns emptyFlow()
                every { getCurrencyRateListUseCase(Unit) } returns flowOf(emptyList())
                coEvery { updateCurrencyRateListUseCase(Unit) } returns flowOf(WrappedResult.success(
                    Unit))
                coEvery { getFeeUseCase(Unit) } returns flowOf(0.0)

                val viewModel = ExchangerViewModel(
                    exchangeCurrencyUseCase = exchangeCurrencyUseCase,
                    getCurrencyRatioUseCase = getCurrencyRatioUseCase,
                    getMyBalanceListUseCase = getMyBalanceListUseCase,
                    getCurrencyRateListUseCase = getCurrencyRateListUseCase,
                    updateCurrencyRateListUseCase = updateCurrencyRateListUseCase,
                    reducer = reducer,
                    getFeeUseCase = getFeeUseCase
                )

                val testSubject = viewModel.test()
                testSubject.testIntent {
                    viewModel.dispatchIntent(ExchangerIntent.OnDestinationCurrencyUpdated("EUR"))
                }

                testSubject.assert(initialSate) {
                    states(
                        { initialSate },
                        { copy(selectedDestinationCurrency = "EUR") },
                    )
                }
            }
        }

    }

}
package app.vahid.feature.currency_exchange.presentation.entry_list

import app.cash.turbine.test
import app.vahid.common.core.WrappedResult
import app.vahid.domain.gateway.model.CurrencyRate
import app.vahid.domain.use_case.ExchangeCurrencyUseCase
import app.vahid.domain.use_case.GetCurrencyRateListUseCase
import app.vahid.domain.use_case.GetCurrencyRatioUseCase
import app.vahid.domain.use_case.GetMyBalanceListUseCase
import app.vahid.domain.use_case.UpdateCurrencyRateListUseCase
import app.vahid.feature.currency_exchange.presentation.exchanger.ExchangerViewModel
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

class ExchangerViewModelBehaviorSpec : BehaviorSpec() {

    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerTest

    init {
        Given("mock exchanger view model instance") {
            val exchangeCurrencyUseCase = mockk<ExchangeCurrencyUseCase>()
            val getCurrencyRatioUseCase = mockk<GetCurrencyRatioUseCase>()
            val getMyBalanceListUseCase = mockk<GetMyBalanceListUseCase>()
            val getCurrencyRateListUseCase = mockk<GetCurrencyRateListUseCase>()
            val updateCurrencyRateListUseCase = mockk<UpdateCurrencyRateListUseCase>()
            val reducer = ExchangerReducer()

            When("initiate the exchanger view model") {

                val initialSate = ExchangerState()
                coEvery { getCurrencyRatioUseCase(any()) } returns emptyFlow()
                coEvery { getMyBalanceListUseCase(Unit) } returns emptyFlow()
                every { getCurrencyRateListUseCase(Unit) } returns flowOf(emptyList())
                coEvery { updateCurrencyRateListUseCase(Unit) } returns WrappedResult.success(Unit)

                val viewModel = ExchangerViewModel(
                    exchangeCurrencyUseCase = exchangeCurrencyUseCase,
                    getCurrencyRatioUseCase = getCurrencyRatioUseCase,
                    getMyBalanceListUseCase = getMyBalanceListUseCase,
                    getCurrencyRateListUseCase = getCurrencyRateListUseCase,
                    updateCurrencyRateListUseCase = updateCurrencyRateListUseCase,
                    reducer = reducer,
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

                coEvery { getCurrencyRatioUseCase(any()) } returns emptyFlow()
                coEvery { getMyBalanceListUseCase(Unit) } returns emptyFlow()
                every { getCurrencyRateListUseCase(Unit) } returns flowOf(currencyRateList)
                coEvery { updateCurrencyRateListUseCase(Unit) } returns WrappedResult.success(Unit)

                val viewModel = ExchangerViewModel(
                    exchangeCurrencyUseCase = exchangeCurrencyUseCase,
                    getCurrencyRatioUseCase = getCurrencyRatioUseCase,
                    getMyBalanceListUseCase = getMyBalanceListUseCase,
                    getCurrencyRateListUseCase = getCurrencyRateListUseCase,
                    updateCurrencyRateListUseCase = updateCurrencyRateListUseCase,
                    reducer = reducer,
                )

                Then("destinationRateList should be set correctly") {
                    viewModel.container.stateFlow.test {

                        awaitItem() shouldBe initialSate
                        awaitItem() shouldBe initialSate.copy(
                            destinationRateList = balanceList,
                        )
                        cancelAndConsumeRemainingEvents()
                    }
                }

            }

        }

    }

}
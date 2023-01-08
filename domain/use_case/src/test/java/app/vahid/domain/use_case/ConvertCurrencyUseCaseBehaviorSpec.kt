package app.vahid.domain.use_case

import app.vahid.domain.gateway.repository.RateExchangerRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class ConvertCurrencyUseCaseBehaviorSpec() : BehaviorSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerTest

    init {

        Given("A ConvertCurrencyUseCase instance") {

            val exchangeCalculatorUseCase = ExchangeCalculatorUseCase()

            val rateExchangerRepository = mockk<RateExchangerRepository>()

            val getBaseCurrencyRateUseCase = GetBaseCurrencyRateUseCase(
                rateExchangerRepository = rateExchangerRepository)

            val getTransactionRatesUseCase = GetTransactionRatesUseCase(
                rateExchangerRepository = rateExchangerRepository,
                getBaseCurrencyRateUseCase = getBaseCurrencyRateUseCase
            )

            val convertCurrencyUseCase = ConvertCurrencyUseCase(
                exchangeCalculatorUseCase = exchangeCalculatorUseCase,
                getTransactionRatesUseCase = getTransactionRatesUseCase
            )

            When("The baseCurrency is EUR") {

                var request: ConvertCurrencyUseCase.PurchaseRequest =
                    ConvertCurrencyUseCase.PurchaseRequest("", "", 0.0)

                every { rateExchangerRepository.getBaseCurrency() } returns flowOf("EUR")

                And("FromCurrency is EUR too") {

                    every { rateExchangerRepository.getCurrencyRate("EUR") } returns flowOf(
                        1.0).also {
                        request = request.copy(fromCurrency = "EUR")
                    }

                    And("toCurrency is USD") {

                        every { rateExchangerRepository.getCurrencyRate("USD") } returns flowOf(
                            1.129031).also {
                            request = request.copy(toCurrency = "USD")
                        }

                        And("amount is 1.0 EUR") {
                            request = request.copy(amount = 1.0)

                            val result = convertCurrencyUseCase(request).first()
                            Then("exchanged value should be 1.129031 USD") {
                                result shouldBe 1.129031
                            }
                        }
                        And("amount is 2 EUR") {
                            request = request.copy(amount = 2.258062)

                            val result = convertCurrencyUseCase(request).first()
                            Then("exchanged value should be 2.258062 USD") {
                                result shouldBe 2.5494219979219994
                            }
                        }

                    }

                }


            }


        }
    }
}
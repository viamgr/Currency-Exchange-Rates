package app.vahid.domain.use_case

import app.cash.turbine.test
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import java.math.BigDecimal

class ConvertCurrencyUseCaseBehaviorSpec : BehaviorSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerTest

    private val exchangeCalculatorUseCase: ExchangeCalculatorUseCase = mockk()
    private val getTransactionRatesUseCase: GetExchangeRatesUseCase = mockk()

    private val originCurrencyRate = 1.0
    private val destinationCurrencyRate = 1.5
    private val baseCurrencyRate = 2.0
    private val originCurrencyId = "EUR"
    private val destinationCurrencyId = "USD"
    private val inputAmount = 10.0.toBigDecimal()

    init {

        Given("A GetCurrencyRatioUseCase instance") {

            val originCurrencyToBaseCurrencyExchangedAmount = mockk<BigDecimal>(relaxed = true)
            val baseCurrencyToDestinationCurrencyExchangedAmount = mockk<BigDecimal>(relaxed = true)

            val convertCurrencyUseCase = GetCurrencyRatioUseCase(
                exchangeCalculatorUseCase = exchangeCalculatorUseCase,
                getExchangeRatesUseCase = getTransactionRatesUseCase,
                ioDispatcher = UnconfinedTestDispatcher(),
            )

            val getTransactionRatesUseCaseRequest = getTransactionRatesUseCaseFakeResponse()

            val exchangeCalculatorUseCaseRequestOriginCurrencyToBaseCurrency =
                fakeCalculatorResponse(
                    originCurrencyRate = originCurrencyRate,
                    destinationCurrencyRate = baseCurrencyRate,
                    amount = inputAmount,
                    exchangedAmount = originCurrencyToBaseCurrencyExchangedAmount)

            val exchangeCalculatorUseCaseRequestBaseCurrencyToDestinationCurrency =
                fakeCalculatorResponse(
                    originCurrencyRate = baseCurrencyRate,
                    destinationCurrencyRate = destinationCurrencyRate,
                    amount = originCurrencyToBaseCurrencyExchangedAmount,
                    exchangedAmount = baseCurrencyToDestinationCurrencyExchangedAmount)

            val actualRequest: GetCurrencyRatioUseCase.Request =
                GetCurrencyRatioUseCase.Request(
                    originCurrency = originCurrencyId,
                    destinationCurrency = destinationCurrencyId,
                    originAmount = inputAmount
                )

            And("convert currency $actualRequest") {

                convertCurrencyUseCase.flow.test {
                    convertCurrencyUseCase(actualRequest)

                    val actualResponse = awaitItem()

                    Then("verify getTransactionRatesUseCase call $actualRequest") {
                        verify(exactly = 1) {
                            getTransactionRatesUseCase.invoke(getTransactionRatesUseCaseRequest)
                        }
                    }

                    Then("verify exchangeCalculatorUseCase sequence call $actualRequest") {
                        coVerifySequence {
                            exchangeCalculatorUseCase.invoke(
                                exchangeCalculatorUseCaseRequestOriginCurrencyToBaseCurrency)
                            exchangeCalculatorUseCase.invoke(
                                exchangeCalculatorUseCaseRequestBaseCurrencyToDestinationCurrency)
                        }
                    }

                    Then("exchanged value should be as intended $actualRequest") {
                        actualResponse shouldBe baseCurrencyToDestinationCurrencyExchangedAmount
                    }
                }


            }
        }
    }


    private fun getTransactionRatesUseCaseFakeResponse(): GetExchangeRatesUseCase.Request {
        val getTransactionRatesUseCaseResponse = GetExchangeRatesUseCase.Response(
            originCurrencyRate = originCurrencyRate,
            destinationCurrencyRate = destinationCurrencyRate,
            baseCurrencyRate = baseCurrencyRate
        )

        return GetExchangeRatesUseCase.Request(
            originCurrencyId = originCurrencyId,
            destinationCurrencyId = destinationCurrencyId
        ).also {
            every {
                getTransactionRatesUseCase.invoke(it)
            } returns flowOf(getTransactionRatesUseCaseResponse)
        }
    }

    private fun fakeCalculatorResponse(
        originCurrencyRate: Double,
        destinationCurrencyRate: Double,
        amount: BigDecimal,
        exchangedAmount: BigDecimal,
    ): ExchangeCalculatorUseCase.Request {
        return ExchangeCalculatorUseCase.Request(
            originCurrencyRate = originCurrencyRate,
            destinationCurrencyRate = destinationCurrencyRate,
            amount = amount
        )
            .also {
                every {
                    exchangeCalculatorUseCase.invoke(it)
                } returns exchangedAmount
            }

    }

}
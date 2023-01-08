package app.vahid.domain.use_case

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class ConvertCurrencyUseCaseBehaviorSpec : BehaviorSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerTest

    private val exchangeCalculatorUseCase: ExchangeCalculatorUseCase = mockk()
    private val getTransactionRatesUseCase: GetTransactionRatesUseCase = mockk()

    private val originCurrencyRate = 1.0
    private val destinationCurrencyRate = 1.5
    private val baseCurrencyRate = 2.0
    private val originCurrencyId = "EUR"
    private val destinationCurrencyId = "USD"
    private val inputAmount = 10.0

    init {

        Given("A GetCurrencyRatioUseCase instance") {

            val originCurrencyToBaseCurrencyExchangedAmount = mockk<Double>(relaxed = true)
            val baseCurrencyToDestinationCurrencyExchangedAmount = mockk<Double>(relaxed = true)

            val convertCurrencyUseCase = GetCurrencyRatioUseCase(
                exchangeCalculatorUseCase = exchangeCalculatorUseCase,
                getTransactionRatesUseCase = getTransactionRatesUseCase
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
                    amount = inputAmount
                )

            And("convert currency $actualRequest") {

                val actualResponse = convertCurrencyUseCase(actualRequest).first()

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


    private fun getTransactionRatesUseCaseFakeResponse(): GetTransactionRatesUseCase.Request {
        val getTransactionRatesUseCaseResponse = GetTransactionRatesUseCase.Response(
            originCurrencyRate = originCurrencyRate,
            destinationCurrencyRate = destinationCurrencyRate,
            baseCurrencyRate = baseCurrencyRate
        )

        return GetTransactionRatesUseCase.Request(
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
        amount: Double,
        exchangedAmount: Double,
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
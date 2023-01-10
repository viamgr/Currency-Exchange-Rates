package app.vahid.feature.currency_exchange

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import app.vahid.base_ui.theme.AppMaterialTheme
import app.vahid.domain.gateway.model.Balance
import app.vahid.domain.gateway.model.CurrencyRate
import app.vahid.domain.use_case.ExchangeCurrencyUseCase
import app.vahid.domain.use_case.GetCurrencyRateListUseCase
import app.vahid.domain.use_case.GetCurrencyRatioUseCase
import app.vahid.domain.use_case.GetFeeUseCase
import app.vahid.domain.use_case.GetMyBalanceListUseCase
import app.vahid.domain.use_case.UpdateCurrencyRateListUseCase
import app.vahid.feature.currency_exchange.presentation.exchanger.ExchangerViewModel
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerReducer
import app.vahid.feature.currency_exchange.ui.components.template.ExchangerScreen
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class ExchangerScreenTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun ensureAllItemsBeingDisplayed() {

        val exchangeCurrencyUseCase = mockk<ExchangeCurrencyUseCase>()
        val getCurrencyRatioUseCase = mockk<GetCurrencyRatioUseCase>()
        val getMyBalanceListUseCase = mockk<GetMyBalanceListUseCase>()
        val getCurrencyRateListUseCase = mockk<GetCurrencyRateListUseCase>()
        val updateCurrencyRateListUseCase = mockk<UpdateCurrencyRateListUseCase>(relaxed = true)
        val getFeeUseCase = mockk<GetFeeUseCase>(relaxed = true)

        val balanceList = (0..2).map {
            Balance(currencyId = it.toString(), amount = it.toBigDecimal())
        }

        val currencyList = (0..10).map {
            CurrencyRate(currencyId = "currencyId $it", rate = it.toDouble())
        }

        every { getMyBalanceListUseCase.invoke(Unit) } returns flowOf(balanceList)
        every { getCurrencyRateListUseCase.invoke(Unit) } returns flowOf(currencyList)
        every { getCurrencyRatioUseCase.invoke(any()) } returns Unit
        every { getCurrencyRatioUseCase.flow } returns flowOf(10.toBigDecimal())


        val viewModel = ExchangerViewModel(
            reducer = ExchangerReducer(),
            exchangeCurrencyUseCase = exchangeCurrencyUseCase,
            getCurrencyRatioUseCase = getCurrencyRatioUseCase,
            getMyBalanceListUseCase = getMyBalanceListUseCase,
            getCurrencyRateListUseCase = getCurrencyRateListUseCase,
            updateCurrencyRateListUseCase = updateCurrencyRateListUseCase,
            getFeeUseCase = getFeeUseCase,
        )

        composeTestRule.setContent {
            AppMaterialTheme {
                ExchangerScreen(viewModel = viewModel) {}
            }
        }

        composeTestRule
            .onNodeWithTag("ExchangerScreenItems")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("ErrorScreen")
            .assertDoesNotExist()

        repeat(balanceList.size) {
            composeTestRule
                .onNodeWithTag("BalanceItem_$it")
                .assertIsDisplayed()
        }
    }

}
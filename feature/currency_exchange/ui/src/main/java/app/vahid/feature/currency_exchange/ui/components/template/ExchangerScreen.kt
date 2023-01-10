@file:OptIn(ExperimentalMaterial3Api::class)

package app.vahid.feature.currency_exchange.ui.components.template

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import app.vahid.base_ui.common.components.organism.DropDownItem
import app.vahid.base_ui.common.components.organism.ErrorScreen
import app.vahid.base_ui.common.utils.formatPrice
import app.vahid.base_ui.theme.Theme
import app.vahid.common.presentation.dispatchIntent
import app.vahid.common.presentation.error_handling.UiErrorType
import app.vahid.domain.gateway.model.Balance
import app.vahid.feature.currency_exchange.presentation.exchanger.ExchangerViewModel
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerIntent
import app.vahid.feature.currency_exchange.presentation.exchanger.pattern.ExchangerSideEffect
import app.vahid.feature.currency_exchange.ui.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.math.BigDecimal
import java.util.Locale


@Composable
fun ExchangerScreen(
    viewModel: ExchangerViewModel = hiltViewModel(),
    onNavigateSuccessDialog: (String) -> Unit,
) {

    val context = LocalContext.current

    viewModel.collectSideEffect {
        when (it) {
            is ExchangerSideEffect.OnCurrencyConverted -> onNavigateSuccessDialog(
                context.getString(
                    R.string.message_currency_exchange_success,
                    it.originAmount.toString(),
                    it.originCurrency,
                    it.destinationAmount.toString(),
                    it.destinationCurrency,
                    it.originCurrency,
                    it.fee.toString(),
                )
            )
        }
    }

    ExchangerScreen(
        viewModel = viewModel,
        onSubmitClicked = {
            viewModel.dispatchIntent(ExchangerIntent.OnSubmitClicked)
        },
        onOriginAmountChanged = { value ->
            viewModel.dispatchIntent(ExchangerIntent.OnOriginValueUpdated(value))
        },
        onDestinationCurrencyChanged = {
            viewModel.dispatchIntent(ExchangerIntent.OnDestinationCurrencyUpdated(it))
        },
        onOriginCurrencyChanged = {
            viewModel.dispatchIntent(ExchangerIntent.OnOriginCurrencyUpdated(it))
        }
    )
}


@Composable
fun ExchangerScreen(
    viewModel: ExchangerViewModel,
    onSubmitClicked: () -> Unit,
    onOriginAmountChanged: (value: String) -> Unit,
    onDestinationCurrencyChanged: (String) -> Unit,
    onOriginCurrencyChanged: (String) -> Unit,
) {
    val state by viewModel.collectAsState()

    with(state) {
        ExchangerScreen(
            errorType = errorType,
            isLoading = isLoading,
            balanceList = balanceList,
            destinationRateList = destinationRateList,
            originRateList = originRateList,
            originAmount = originAmount,
            selectedOriginCurrency = selectedOriginCurrency,
            selectedDestinationCurrency = selectedDestinationCurrency,
            destinationAmount = destinationAmount,
            isSubmitButtonEnabled = isSubmitButtonEnabled,
            onSubmitClicked = onSubmitClicked,
            onOriginAmountChanged = onOriginAmountChanged,
            onDestinationCurrencyChanged = onDestinationCurrencyChanged,
            onOriginCurrencyChanged = onOriginCurrencyChanged,
        )

    }
}

@Composable
fun ExchangerScreen(
    errorType: UiErrorType? = null,
    isLoading: Boolean = false,
    balanceList: List<Balance> = emptyList(),
    destinationRateList: List<String> = emptyList(),
    originRateList: List<String> = emptyList(),
    originAmount: BigDecimal = BigDecimal.ZERO,
    selectedOriginCurrency: String = "",
    selectedDestinationCurrency: String = "",
    destinationAmount: BigDecimal = BigDecimal.ZERO,
    isSubmitButtonEnabled: Boolean,
    onSubmitClicked: () -> Unit,
    onOriginAmountChanged: (value: String) -> Unit,
    onDestinationCurrencyChanged: (String) -> Unit,
    onOriginCurrencyChanged: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
    ) {

        item {
            ToolbarUi()
        }


        errorType?.let {
            item {
                ErrorScreen(it)
            }
        }


        item {
            HeaderLabel(stringResource(R.string.my_balances).uppercase(Locale.getDefault()))
        }

        item {
            BalancesListUi(balanceList, onClick = onOriginCurrencyChanged)
        }

        item {
            HeaderLabel(stringResource(R.string.currency_exchange).uppercase(Locale.getDefault()))
        }

        item {

            ExchangerActionsUi(
                originRateList = originRateList,
                destinationRateList = destinationRateList,
                selectedDestinationCurrency = selectedDestinationCurrency,
                selectedOriginCurrency = selectedOriginCurrency,
                originAmount = originAmount,
                destinationAmount = destinationAmount,
                onOriginAmountChanged = onOriginAmountChanged,
                onOriginCurrencyChanged = onOriginCurrencyChanged,
                onDestinationCurrencyChanged = onDestinationCurrencyChanged
            )
        }

        item {
            SubmitButton(isSubmitButtonEnabled && !isLoading, onSubmitClicked)
        }

    }
}

@Composable
private fun HeaderLabel(label: String) {
    Text(
        text = label,
        style = Theme.typography.labelMedium,
        modifier = Modifier.padding(
            vertical = Theme.dimensions.spaceExtraLarge,
            horizontal = Theme.dimensions.spaceLarge
        )
    )
}

@Composable
fun SubmitButton(isSubmitButtonEnabled: Boolean, onSubmitClicked: () -> Unit) {

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Theme.dimensions.spaceExtraLarge),
        onClick = onSubmitClicked,
        colors = ButtonDefaults.buttonColors(
            containerColor = Theme.colorScheme.buttonBg,
            contentColor = Theme.colorScheme.labelMedium,
        ),
        enabled = isSubmitButtonEnabled) {
        Text(
            text = stringResource(R.string.submit).uppercase(Locale.getDefault()),
            style = Theme.typography.button
        )
    }
}

@Composable
fun ExchangerActionsUi(
    originRateList: List<String>,
    destinationRateList: List<String>,
    selectedDestinationCurrency: String,
    selectedOriginCurrency: String,
    originAmount: BigDecimal = BigDecimal.ZERO,
    destinationAmount: BigDecimal = BigDecimal.ZERO,
    onOriginAmountChanged: (value: String) -> Unit,
    onOriginCurrencyChanged: (String) -> Unit,
    onDestinationCurrencyChanged: (String) -> Unit,
) {
    Column {

        ExchangerActionRow(
            amount = originAmount.toString(),
            currency = selectedOriginCurrency,
            itemList = originRateList,
            label = stringResource(R.string.sell),
            iconTint = Theme.colorScheme.receive,
            onCurrencyChanged = onOriginCurrencyChanged,
            iconRotation = 90F,
            onOriginAmountChanged = onOriginAmountChanged,
            readOnly = false,
            exchangeTypeTextColor = Theme.colorScheme.primary,
        )

        ExchangerActionRow(
            amount = "+ ${destinationAmount.formatPrice()}",
            currency = selectedDestinationCurrency,
            itemList = destinationRateList,
            label = stringResource(R.string.receive),
            iconTint = Theme.colorScheme.sell,
            onCurrencyChanged = onDestinationCurrencyChanged,
            iconRotation = 270F,
            readOnly = true,
            exchangeTypeTextColor = Theme.colorScheme.receive
        )
    }
}

@Composable
private fun ExchangerActionRow(
    amount: String,
    currency: String,
    itemList: List<String>,
    label: String,
    iconTint: Color,
    onCurrencyChanged: (String) -> Unit,
    iconRotation: Float,
    onOriginAmountChanged: (value: String) -> Unit = {},
    readOnly: Boolean,
    exchangeTypeTextColor: Color,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = CenterVertically
    ) {
        Image(
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .align(CenterVertically)
                .height(Theme.dimensions.heightMedium)
                .padding(start = Theme.dimensions.spaceExtraLarge)
                .rotate(iconRotation),
            painter = painterResource(id = R.drawable.ic_baseline_arrow_circle_left_24),
            colorFilter = ColorFilter.tint(iconTint),
            contentDescription = label
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1F)
                .padding(start = Theme.dimensions.spaceMedium)
                .wrapContentHeight()) {

            Row(verticalAlignment = CenterVertically,
                modifier = Modifier.padding(end = Theme.dimensions.spaceMedium)
            ) {
                Text(
                    text = label,
                    modifier = Modifier.padding(Theme.dimensions.spaceMedium),
                    style = Theme.typography.bodySmall
                )

                EditText(
                    value = amount,
                    readOnly = readOnly,
                    modifier = Modifier.weight(1F),
                    onValueChange = onOriginAmountChanged,
                    textColor = exchangeTypeTextColor
                )

                Text(text = currency, style = Theme.typography.bodySmall)

                DropDownItem(
                    items = itemList,
                    onItemSelected = {
                        onCurrencyChanged(itemList[it])
                    },
                    itemsContent = {
                        Text(
                            text = it,
                            style = Theme.typography.bodySmall,
                        )
                    },
                    key = { _, item ->
                        item
                    }
                )

                Image(
                    modifier = Modifier
                        .rotate(90F)
                        .padding(Theme.dimensions.spaceMedium)
                        .size(Theme.dimensions.dropDownArrowSize),
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_ios_24),
                    contentDescription = label
                )
            }

            Divider()
        }

    }
}

@Composable
fun BalancesListUi(balanceList: List<Balance>, onClick: (String) -> Unit) {
    LazyRow(Modifier.padding(
        horizontal = Theme.dimensions.spaceLarge
    )) {
        items(items = balanceList, key = { it.currencyId }) {
            Row(modifier = Modifier
                .clickable {
                    onClick(it.currencyId)
                }
                .padding(horizontal = Theme.dimensions.spaceSuperExtraLarge,
                    vertical = Theme.dimensions.spaceMedium)) {

                Text(text = it.amount.formatPrice(), style = Theme.typography.bodyLarge)

                Text(text = it.currencyId, style = Theme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = Theme.dimensions.spaceMedium))

            }
        }
    }
}

@Composable
private fun EditText(
    value: String,
    readOnly: Boolean = false,
    modifier: Modifier = Modifier,
    onValueChange: (value: String) -> Unit = {},
    textColor: Color,
) {
    val focusRequester = remember { FocusRequester() }

    var text by remember(key1 = value) {
        mutableStateOf(TextFieldValue(
            text = value,
            selection = if (value == "0") {
                TextRange(start = 0, end = 1)
            } else {
                TextRange(start = value.length, end = value.length)
            }
        ))
    }

    OutlinedTextField(
        maxLines = 2,
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { },
        value = text,
        textStyle = Theme.typography.bodySmall.copy(
            textAlign = TextAlign.End,
            color = textColor
        ),
        readOnly = readOnly,
        onValueChange = {
            if (it.text.length < 12) {
                text = it
                onValueChange(it.text)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = Theme.colorScheme.secondary,
            errorCursorColor = Theme.colorScheme.secondary,
            focusedBorderColor = Theme.colorScheme.primaryBg,
            disabledBorderColor = Theme.colorScheme.primaryBg,
            unfocusedBorderColor = Theme.colorScheme.primaryBg,
            errorBorderColor = Theme.colorScheme.primaryBg,
        ),

        )
    LaunchedEffect(Unit) {
        if (!readOnly)
            focusRequester.requestFocus()
    }

}

@Composable
fun ToolbarUi() {

    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize()
        .background(Theme.colorScheme.toolbarBg)
    ) {

        Text(
            text = stringResource(R.string.currency_converter),
            style = Theme.typography.labelSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Theme.dimensions.spaceLarge), textAlign = TextAlign.Center)

    }

}

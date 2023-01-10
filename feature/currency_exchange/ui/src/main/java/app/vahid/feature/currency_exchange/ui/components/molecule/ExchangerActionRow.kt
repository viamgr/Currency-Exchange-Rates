package app.vahid.feature.currency_exchange.ui.components.molecule

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import app.vahid.base_ui.common.components.atom.AppTextField
import app.vahid.base_ui.common.components.organism.DropDownItem
import app.vahid.base_ui.theme.Theme
import app.vahid.feature.currency_exchange.ui.R

@Composable
fun ExchangerActionRow(
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
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .align(Alignment.CenterVertically)
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

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = Theme.dimensions.spaceMedium)
            ) {
                Text(
                    text = label,
                    modifier = Modifier.padding(Theme.dimensions.spaceMedium),
                    style = Theme.typography.bodySmall
                )

                AppTextField(
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
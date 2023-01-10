package app.vahid.feature.currency_exchange.ui.components.organism

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.vahid.base_ui.theme.Theme
import app.vahid.domain.gateway.model.Balance
import app.vahid.feature.currency_exchange.ui.components.molecule.BalanceItem

@Composable
fun BalancesListUi(balanceList: List<Balance>, onClick: (String) -> Unit) {
    LazyRow(Modifier.padding(
        horizontal = Theme.dimensions.spaceLarge
    )) {
        items(items = balanceList, key = { it.currencyId }) {
            BalanceItem(onClick, it)
        }
    }
}
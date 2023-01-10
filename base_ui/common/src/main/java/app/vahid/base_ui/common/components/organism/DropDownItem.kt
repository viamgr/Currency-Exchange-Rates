package app.vahid.base_ui.common.components.organism

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.vahid.base_ui.theme.Theme

@Composable
fun <T> DropDownItem(
    items: List<T>,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    key: ((index: Int, item: T) -> Any)? = null,
    labelContent: @Composable () -> Unit = {},
    itemsContent: @Composable (item: T) -> Unit,
) {

    Box(modifier = modifier.wrapContentSize(Alignment.TopStart)) {
        var expanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier.clickable {
                expanded = true
            }
        ) {
            labelContent()
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Box(modifier = Modifier
                .width(Theme.dimensions.dropDownWidth)
                .height(height = Theme.dimensions.dropDownHeight)) {

                LazyColumn {
                    itemsIndexed(items = items,
                        key = key,
                        itemContent = { index, item ->
                            DropdownMenuItem(
                                text = {
                                    itemsContent(item)
                                },
                                onClick = {
                                    expanded = false
                                    onItemSelected(index)
                                })

                            if (index != items.size - 1)
                                Divider()
                        })
                }
            }

        }
    }
}
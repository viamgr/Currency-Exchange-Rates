package app.vahid.base_ui.common.components.organism

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
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

@Composable
fun <T> DropDownItem(
    items: List<T>,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    title: @Composable (title: T) -> Unit,
    content: @Composable () -> Unit,
) {

    Box(modifier = modifier.wrapContentSize(Alignment.TopStart)) {
        var expanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier.clickable {
                expanded = true
            }
        ) {
            content()
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEachIndexed { index, title ->
                DropdownMenuItem(
                    text = {
                        title(title)
                    },
                    onClick = {
                        expanded = false
                        onItemSelected(index)
                    })

                if (index != items.size - 1)
                    Divider()
            }
        }
    }
}
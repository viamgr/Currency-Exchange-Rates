package app.vahid.base_ui.common.components.atom

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.vahid.base_ui.theme.Theme

@Composable
fun HeaderLabel(label: String) {
    Text(
        text = label,
        style = Theme.typography.labelMedium,
        modifier = Modifier.padding(
            vertical = Theme.dimensions.spaceExtraLarge,
            horizontal = Theme.dimensions.spaceLarge
        )
    )
}
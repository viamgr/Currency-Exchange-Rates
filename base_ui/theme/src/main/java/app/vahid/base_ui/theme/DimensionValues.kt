package app.vahid.base_ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class DimensionValues(
    val smallDividerHeight: Dp = 1.dp,
    val paddingLarge: Dp = 32.dp,
    val heightLarge: Dp = 128.dp,
    val spaceLarge: Dp = 8.dp,
    val spaceMedium: Dp = 4.dp,
    val spaceSmall: Dp = 2.dp,
)
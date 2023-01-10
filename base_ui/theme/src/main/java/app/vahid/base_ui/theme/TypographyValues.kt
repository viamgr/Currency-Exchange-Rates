package app.vahid.base_ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle

@Immutable
data class TypographyValues(
    val labelSmall: TextStyle,
    val labelMedium: TextStyle,
    val confirm: TextStyle,
    val secondaryText: TextStyle,
    val bodySmall: TextStyle,
    val bodyMediumReceive: TextStyle,
    val bodyMedium: TextStyle,
    val bodyLarge: TextStyle,
    val button: TextStyle,
)
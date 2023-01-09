package app.vahid.base_ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ColorValues(
    val primary: Color = Color(0xFF232529),
    val secondary: Color = Color(0xFF0589CA),
    val primaryBg: Color = Color(0XFFFFFFFF),
    val toolbarText: Color = Color(0xFFFFFFFF),
    val toolbarBg: Color = secondary,
    val buttonBg: Color = secondary,
    val labelMedium: Color = Color(0XFFc8ced3),
    val receive: Color = Color(0XFF00B241),
    val sell: Color = Color(0XFFFF3A45),
)
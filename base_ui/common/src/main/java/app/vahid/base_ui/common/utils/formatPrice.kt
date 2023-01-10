package app.vahid.base_ui.common.utils

import java.math.BigDecimal
import java.text.DecimalFormat

fun BigDecimal.formatPrice(): String {
    return DecimalFormat("0.##").format(this)
}
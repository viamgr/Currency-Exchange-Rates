package app.vahid.domain.gateway.model

import java.math.BigDecimal


data class Balance(
    val currencyId: String,
    val amount: BigDecimal,
)
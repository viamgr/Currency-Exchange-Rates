package app.vahid.repository.model

import java.math.BigDecimal

data class BalanceEntity(
    val currencyId: String,
    val amount: BigDecimal,
)
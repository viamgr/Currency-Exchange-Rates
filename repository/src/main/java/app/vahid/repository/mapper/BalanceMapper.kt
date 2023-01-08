package app.vahid.repository.mapper

import app.vahid.domain.gateway.model.Balance
import app.vahid.repository.model.BalanceEntity
import javax.inject.Inject

class BalanceMapper @Inject constructor() {
    operator fun invoke(type: BalanceEntity): Balance = with(type) {
        Balance(
            currencyId = currencyId,
            amount = amount
        )
    }
}
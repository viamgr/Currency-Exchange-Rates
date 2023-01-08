package app.vahid.repository.mapper

import app.vahid.domain.gateway.model.CurrencyRate
import app.vahid.repository.model.CurrencyRateEntity
import javax.inject.Inject

class CurrencyRateMapper @Inject constructor() {
    operator fun invoke(type: CurrencyRateEntity): CurrencyRate = with(type) {
        CurrencyRate(
            currencyId = currencyId,
            rate = rate
        )
    }

    operator fun invoke(type: CurrencyRate): CurrencyRateEntity = with(type) {
        CurrencyRateEntity(
            currencyId = currencyId,
            rate = rate
        )
    }
}
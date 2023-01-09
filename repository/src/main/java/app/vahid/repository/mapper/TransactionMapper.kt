package app.vahid.repository.mapper

import app.vahid.domain.gateway.model.Transaction
import app.vahid.repository.model.TransactionEntity
import javax.inject.Inject

class TransactionMapper @Inject constructor() {
    operator fun invoke(type: TransactionEntity): Transaction = with(type) {
        Transaction(
            originCurrency = originCurrency,
            destinationCurrency = destinationCurrency,
            originAmount = originAmount,
            destinationAmount = destinationAmount,
            fee = fee
        )
    }

    operator fun invoke(type: Transaction): TransactionEntity = with(type) {
        TransactionEntity(
            originCurrency = originCurrency,
            destinationCurrency = destinationCurrency,
            originAmount = originAmount,
            destinationAmount = destinationAmount,
            fee = fee,
        )
    }
}
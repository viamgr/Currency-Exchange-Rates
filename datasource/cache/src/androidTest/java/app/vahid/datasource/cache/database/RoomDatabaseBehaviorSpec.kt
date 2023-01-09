package app.vahid.datasource.cache.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import app.vahid.datasource.cache.database.datasource.CurrencyExchangeLocalDataSourceImpl
import app.vahid.datasource.cache.database.entity.CachedTransaction
import app.vahid.repository.model.TransactionEntity
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class RoomDatabaseBehaviorSpec {

    private val database = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        AppDatabase::class.java
    ).allowMainThreadQueries().build()


    @Before
    fun before() = runBlocking {


    }

    @After
    fun after() {
        database.close()

    }

    @Test
    fun ensureInsertIntoTransactionWorksProperly() = runTest {

        val balances = database.transactionDao().getTransactionList()

        database.transactionDao().insert(
            CachedTransaction(
                originCurrency = "EUR",
                destinationCurrency = "EUR",
                originAmount = 0.0,
                destinationAmount = 1000.0,
                fee = 0.1
            )
        )

        database.transactionDao().insert(
            CachedTransaction(
                originCurrency = "EUR",
                destinationCurrency = "USD",
                originAmount = 100.0,
                destinationAmount = 120.0,
                fee = 0.1
            )
        )

        database.transactionDao().insert(
            CachedTransaction(
                originCurrency = "USD",
                destinationCurrency = "EUR",
                originAmount = 120.0,
                destinationAmount = 100.0,
                fee = 0.1
            )
        )

        balances.test {
            val awaitItem = awaitItem()
            assertEquals(awaitItem.size, 3)
        }

    }

    @Test
    fun ensureInsertIntoExchangeDataSourceWorksProperly() = runTest {
        val currencyExchangeLocalDataSourceImpl = CurrencyExchangeLocalDataSourceImpl(database)

        currencyExchangeLocalDataSourceImpl.addTransaction(
            TransactionEntity(
                originCurrency = "EUR",
                destinationCurrency = "EUR",
                originAmount = 0.0,
                destinationAmount = 1000.0,
                fee = 0.0
            )
        )

        currencyExchangeLocalDataSourceImpl.addTransaction(
            TransactionEntity(
                originCurrency = "EUR",
                destinationCurrency = "USD",
                originAmount = 100.0,
                destinationAmount = 120.0,
                fee = 0.1
            )
        )

        currencyExchangeLocalDataSourceImpl.addTransaction(
            TransactionEntity(
                originCurrency = "USD",
                destinationCurrency = "EUR",
                originAmount = 108.0,
                destinationAmount = 90.0,
                fee = 0.0
            )
        )

        val balanceList = currencyExchangeLocalDataSourceImpl.getBalanceList()

        balanceList.test {
            val awaitItem = awaitItem()
            assertEquals(2, awaitItem.size)
            assertEquals(980.0, awaitItem.first { it.currencyId == "EUR" }.amount)
            assertEquals(0.0, awaitItem.first { it.currencyId == "USD" }.amount)
        }
    }

}

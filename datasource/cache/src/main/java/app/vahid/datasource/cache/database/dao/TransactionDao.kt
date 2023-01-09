package app.vahid.datasource.cache.database.dao

import androidx.room.Dao
import androidx.room.Query
import app.vahid.datasource.cache.database.entity.CachedTransaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao : BaseDao<CachedTransaction> {

    @Query("SELECT * FROM `transaction`")
    fun getTransactionList(): Flow<List<CachedTransaction>>


    @Query("SELECT count(*) as count FROM `transaction`")
    fun getTransactionCount(): Flow<Int>
}
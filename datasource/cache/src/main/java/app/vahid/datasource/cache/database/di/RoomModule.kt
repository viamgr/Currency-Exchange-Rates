@file:OptIn(DelicateCoroutinesApi::class)

package app.vahid.datasource.cache.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import app.vahid.common.core.IoDispatcher
import app.vahid.datasource.cache.database.AppDatabase
import app.vahid.datasource.cache.database.entity.CachedTransaction
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    lateinit var database: AppDatabase

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
    ): AppDatabase {
        database = Room.databaseBuilder(context, AppDatabase::class.java, "app-db")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    GlobalScope.launch(coroutineDispatcher) {
                        database.transactionDao().insert(
                            CachedTransaction(
                                originCurrency = "EUR",
                                destinationCurrency = "EUR",
                                originAmount = BigDecimal.ZERO,
                                destinationAmount = BigDecimal.valueOf(1000.0),
                                fee = 0.0,
                            ))
                    }
                }

            })
            .build()
        return database
    }

}
package app.vahid.datasource.cache.database.di

import app.vahid.datasource.cache.database.datasource.CurrencyExchangeLocalDataSourceImpl
import app.vahid.repository.datasource.CurrencyExchangeLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    internal abstract fun bindCurrencyExchangeLocalDataSource(impl: CurrencyExchangeLocalDataSourceImpl): CurrencyExchangeLocalDataSource

}
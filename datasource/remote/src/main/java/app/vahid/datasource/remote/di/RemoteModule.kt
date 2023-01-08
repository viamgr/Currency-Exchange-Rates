package app.vahid.datasource.remote.di

import app.vahid.datasource.remote.datasource.CurrencyExchangeRemoteDataSourceImpl
import app.vahid.repository.datasource.CurrencyExchangeRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {

    @Binds
    @Singleton
    internal abstract fun bindsCurrencyExchangeRemoteDataSource(impl: CurrencyExchangeRemoteDataSourceImpl)
            : CurrencyExchangeRemoteDataSource
}
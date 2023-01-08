package app.vahid.repository.di

import app.vahid.domain.gateway.repository.CurrencyExchangeRepository
import app.vahid.repository.repository.CurrencyExchangeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    internal abstract fun bindCurrencyExchangeRepository(impl: CurrencyExchangeRepositoryImpl):
            CurrencyExchangeRepository

}
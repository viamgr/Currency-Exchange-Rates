package app.vahid.datasource.remote.di

import app.vahid.datasource.remote.api.CurrencyExchangeApi
import app.vahid.datasource.remote.base.ResultCallAdapterFactory
import app.vahid.datasource.remote.base.SecureKey
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    @ExperimentalSerializationApi
    internal fun provideSerializableFactory(): Converter.Factory =
        json.asConverterFactory("application/json".toMediaType())


    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }


    @Provides
    @Singleton
    internal fun provideRetrofit(
        client: OkHttpClient,
        resultCallAdapterFactory: ResultCallAdapterFactory,
        converterFactory: Converter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(SecureKey.getUrl())
            .client(client)
            .addCallAdapterFactory(resultCallAdapterFactory)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideCurrencyExchangeApi(retrofit: Retrofit): CurrencyExchangeApi {
        return retrofit.create(CurrencyExchangeApi::class.java)
    }
}
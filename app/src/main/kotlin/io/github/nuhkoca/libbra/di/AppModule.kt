/*
 * Copyright (C) 2020. Nuh Koca. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.nuhkoca.libbra.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.github.nuhkoca.libbra.BuildConfig
import io.github.nuhkoca.libbra.data.datasource.CurrencyRemoteDataSource
import io.github.nuhkoca.libbra.data.datasource.DataSource
import io.github.nuhkoca.libbra.data.mapper.CurrencyDomainMapper
import io.github.nuhkoca.libbra.data.model.domain.CurrencyResponse
import io.github.nuhkoca.libbra.data.model.raw.CurrencyResponseRaw
import io.github.nuhkoca.libbra.data.model.view.CurrencyResponseViewItem
import io.github.nuhkoca.libbra.data.service.CurrencyService
import io.github.nuhkoca.libbra.data.verifier.RevolutHostnameVerifier
import io.github.nuhkoca.libbra.domain.mapper.CurrencyViewItemMapper
import io.github.nuhkoca.libbra.domain.repository.CurrencyRepository
import io.github.nuhkoca.libbra.domain.repository.Repository
import io.github.nuhkoca.libbra.domain.usecase.CurrencyParams
import io.github.nuhkoca.libbra.domain.usecase.CurrencyUseCase
import io.github.nuhkoca.libbra.domain.usecase.UseCase
import io.github.nuhkoca.libbra.util.coroutines.DefaultDispatcherProvider
import io.github.nuhkoca.libbra.util.coroutines.DispatcherProvider
import io.github.nuhkoca.libbra.util.ext.errorInterceptor
import io.github.nuhkoca.libbra.util.mapper.Mapper
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    @Singleton
    internal abstract fun bindDispatcherProvider(
        defaultDispatcherProvider: DefaultDispatcherProvider
    ): DispatcherProvider

    @Binds
    @Remote
    @Singleton
    internal abstract fun bindCurrencyDataSource(
        currencyRemoteDataSource: CurrencyRemoteDataSource
    ): DataSource

    @Binds
    @Singleton
    internal abstract fun bindCurrencyDomainMapper(
        currencyDomainMapper: CurrencyDomainMapper
    ): Mapper<CurrencyResponseRaw, CurrencyResponse>

    @Binds
    @Singleton
    internal abstract fun bindCurrencyViewItemMapper(
        currencyViewItemMapper: CurrencyViewItemMapper
    ): Mapper<CurrencyResponse, CurrencyResponseViewItem>

    @Binds
    @Singleton
    internal abstract fun bindCurrencyRepository(
        currencyRepository: CurrencyRepository
    ): Repository

    @Binds
    @Singleton
    internal abstract fun bindCurrencyUseCase(
        currencyUseCase: CurrencyUseCase
    ): UseCase.FlowUseCase<CurrencyParams, CurrencyResponseViewItem>

    @Module
    internal companion object {

        private const val MEDIA_TYPE_DEFAULT = "application/json"
        private const val TIMEOUT_IN_MS = 10000L

        @Provides
        @Singleton
        internal fun provideCurrencyService(retrofit: Retrofit): CurrencyService = retrofit.create()

        @Provides
        @Singleton
        @UnstableDefault
        internal fun provideRetrofit(@InternalApi httpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder().apply {
                baseUrl(BuildConfig.BASE_URL)
                addConverterFactory(Json.asConverterFactory(MEDIA_TYPE_DEFAULT.toMediaType()))
                client(httpClient)
            }.build()
        }

        @Provides
        @Singleton
        @InternalApi
        @UnstableDefault
        internal fun provideOkHttpClient(
            @InternalApi loggingInterceptor: HttpLoggingInterceptor
        ): OkHttpClient {
            return OkHttpClient.Builder().apply {
                hostnameVerifier(RevolutHostnameVerifier)
                connectTimeout(TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
                readTimeout(TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
                writeTimeout(TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
                addInterceptor(loggingInterceptor)
                addInterceptor(errorInterceptor())
            }.build()
        }

        @Provides
        @Singleton
        @InternalApi
        internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        }
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
private annotation class InternalApi

@Qualifier
@MustBeDocumented
annotation class Remote

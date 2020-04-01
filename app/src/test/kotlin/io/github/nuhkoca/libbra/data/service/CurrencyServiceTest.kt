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
package io.github.nuhkoca.libbra.data.service

import androidx.test.filters.LargeTest
import com.google.common.truth.Truth.assertThat
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.nuhkoca.libbra.data.enums.Rate
import io.github.nuhkoca.libbra.data.model.raw.CurrencyResponseRaw
import io.github.nuhkoca.libbra.data.verifier.RevolutHostnameVerifier
import io.github.nuhkoca.libbra.shared.MEDIA_TYPE_DEFAULT
import io.github.nuhkoca.libbra.shared.dispatcher.ErrorDispatcher
import io.github.nuhkoca.libbra.shared.dispatcher.SuccessDispatcher
import io.github.nuhkoca.libbra.shared.dispatcher.TimeoutDispatcher
import io.github.nuhkoca.libbra.util.ext.ErrorInterceptor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit

/**
 * A test class for [CurrencyService]
 */
@RunWith(MockitoJUnitRunner::class)
@LargeTest
class CurrencyServiceTest {

    /*
     -----------------------
    |    Private members    |
     -----------------------
    */
    private val mockWebServer = MockWebServer()
    private lateinit var currencyService: CurrencyService

    @Before
    @UnstableDefault
    fun setUp() {
        mockWebServer.start()

        val client = OkHttpClient.Builder().apply {
            hostnameVerifier(RevolutHostnameVerifier)
            connectTimeout(TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
            readTimeout(TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
            writeTimeout(TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
            addInterceptor(HttpLoggingInterceptor())
            addInterceptor(ErrorInterceptor())
        }.build()

        val retrofit = Retrofit.Builder().apply {
            client(client)
            baseUrl(mockWebServer.url("/").toString())
            addConverterFactory(Json.asConverterFactory(MEDIA_TYPE_DEFAULT.toMediaType()))
        }.build()

        currencyService = retrofit.create()

        mockWebServer.dispatcher = SuccessDispatcher(CURRENCY_SUCCESS_RESPONSE_FILE_NAME)
    }

    /*
     * We are unable to manipulate Retrofit's dispatcher so that we have to use runBlocking
     */
    @Test
    @ExperimentalCoroutinesApi
    fun `list of currencies should be fetched`() = runBlocking {
        // Given
        val base = Rate.EUR

        // When
        val response = currencyService.getCurrencyList(base)

        // Then
        assertThat(response).isNotNull()
        assertThat(response.baseCurrency).isEqualTo(base.name)
        assertThat(response.rates).isNotNull()
        assertThat(response.rates).hasSize(31)
    }

    /*
     * We are unable to manipulate Retrofit's dispatcher so that we have to use runBlocking
     */
    @Test
    fun `currency service should throw an error`() = runBlocking {
        mockWebServer.dispatcher = ErrorDispatcher

        // Given
        val base = Rate.AUD

        var response: CurrencyResponseRaw? = null

        // When
        try {
            response = currencyService.getCurrencyList(base)
        } catch (e: Exception) {
            assertThat(e.message).contains("Unexpected JSON token at")
        }

        // Then
        assertThat(response).isNull()
        assertThat(response?.rates).isNull()
    }

    /*
     * We are unable to manipulate Retrofit's dispatcher so that we have to use runBlocking
     */
    @Test
    fun `request should be timed out`() = runBlocking {
        mockWebServer.dispatcher = TimeoutDispatcher

        // Given
        val base = Rate.DKK

        var response: CurrencyResponseRaw? = null

        // When
        try {
            response = currencyService.getCurrencyList(base)
        } catch (e: Exception) {
            assertThat(e.message).isEqualTo("timeout")
        }

        // Then
        assertThat(response).isNull()
        assertThat(response?.rates).isNull()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private companion object {
        // For test purpose <Real one is 10sec>
        private const val TIMEOUT_IN_MS = 1000L
        private const val CURRENCY_SUCCESS_RESPONSE_FILE_NAME = "currency_success_response.json"
    }
}

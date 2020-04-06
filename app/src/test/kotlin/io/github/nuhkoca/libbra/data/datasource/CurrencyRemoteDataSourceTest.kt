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
package io.github.nuhkoca.libbra.data.datasource

import BaseTestClass
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import io.github.nuhkoca.libbra.data.Result
import io.github.nuhkoca.libbra.data.enums.Rate
import io.github.nuhkoca.libbra.data.model.domain.CurrencyResponse
import io.github.nuhkoca.libbra.data.model.raw.CurrencyResponseRaw
import io.github.nuhkoca.libbra.data.service.CurrencyService
import io.github.nuhkoca.libbra.data.shared.rule.CoroutinesTestRule
import io.github.nuhkoca.libbra.shared.assertion.test
import io.github.nuhkoca.libbra.shared.ext.runBlockingTest
import io.github.nuhkoca.libbra.util.coroutines.AsyncManager
import io.github.nuhkoca.libbra.util.mapper.Mapper
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * A test rule for [CurrencyRemoteDataSource]
 */
@RunWith(MockitoJUnitRunner::class)
@MediumTest
class CurrencyRemoteDataSourceTest : BaseTestClass() {

    /*
     ------------
    |    Rules   |
     ------------
    */
    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    /*
     -------------
    |    Mocks    |
     -------------
    */
    @MockK
    private lateinit var currencyService: CurrencyService

    @MockK
    private lateinit var mapper: Mapper<CurrencyResponseRaw, CurrencyResponse>

    @RelaxedMockK
    private lateinit var currencyResponseRaw: CurrencyResponseRaw

    @RelaxedMockK
    private lateinit var currencyResponse: CurrencyResponse

    /*
     -----------------------
    |    Private members    |
     -----------------------
    */
    private lateinit var dataSource: DataSource
    private val currencySlot = slot<Rate>()

    @ExperimentalCoroutinesApi
    override fun setUp() {
        super.setUp()

        coEvery { currencyService.getCurrencyList(capture(currencySlot)) } returns currencyResponseRaw
        coEvery { mapper.map(any()) } returns currencyResponse

        every { currencyResponse.baseCurrency } returns "CNY"

        dataSource = CurrencyRemoteDataSource(
            currencyService,
            mapper,
            coroutinesTestRule.testDispatcherProvider
        )
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `data source should return data`() = coroutinesTestRule.runBlockingTest {
        // Given
        val base = Rate.CNY
        val continuation = AsyncManager.Continuation.RESUME

        // When
        val flow = dataSource.getCurrencyList(base, continuation)

        // Then
        flow.test {
            expectItem().run {
                assertThat(this).isNotNull()
                assertThat(this).isInstanceOf(Result.Success::class.java)
                this as Result.Success
                assertThat(data.baseCurrency).isEqualTo(currencySlot.captured.name)
                assertThat(data.rates).isNotNull()
            }
            cancel()
            expectNoMoreEvents()
        }

        /*
         * Verify order of calls
         * e.g. first currencyService should be called and then mapper
         */
        coVerifyOrder {
            currencyService.getCurrencyList(any())
            mapper.map(any())
        }

        confirmVerified(currencyService)
        confirmVerified(mapper)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `network process should not happen in case of pause`() =
        coroutinesTestRule.runBlockingTest {
            // Given
            val base = Rate.CNY
            val continuation = AsyncManager.Continuation.PAUSE

            // When
            val flow = dataSource.getCurrencyList(base, continuation)

            // Then
            flow.test {
                expectComplete()
            }
        }
}

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
package io.github.nuhkoca.libbra.util.coroutines

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import io.github.nuhkoca.libbra.data.Result
import io.github.nuhkoca.libbra.data.enums.Rate
import io.github.nuhkoca.libbra.data.failure.Failure
import io.github.nuhkoca.libbra.data.model.domain.CurrencyResponse
import io.github.nuhkoca.libbra.data.shared.rule.CoroutinesTestRule
import io.github.nuhkoca.libbra.shared.assertion.test
import io.github.nuhkoca.libbra.shared.ext.runBlockingTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import io.github.nuhkoca.libbra.data.model.domain.Rate as DomainRate

/**
 * A test class for [DefaultAsyncManager]
 */
@RunWith(MockitoJUnitRunner::class)
@SmallTest
class DefaultAsyncManagerTest {

    /*
     ------------
    |    Rules   |
     ------------
    */
    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    /*
     -----------------------
    |    Private members    |
     -----------------------
    */
    private lateinit var asyncManager: AsyncManager

    @Before
    @ExperimentalCoroutinesApi
    fun setUp() {
        asyncManager = DefaultAsyncManager(coroutinesTestRule.testDispatcherProvider)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `async manager should return data successfully`() = coroutinesTestRule.runBlockingTest {
        // Given
        val mockedResponse = CurrencyResponse(
            baseCurrency = "EUR",
            rates = listOf(DomainRate(Rate.GBP, 1.1f), DomainRate(Rate.RUB, 4.1f))
        )

        // When
        val flow =
            asyncManager.handleAsyncWithTryCatch { mockedResponse }

        // Then
        flow.test {
            expectItem().run {
                assertThat(this).isInstanceOf(Result.Success::class.java)
                this as Result.Success
                assertThat(data).isNotNull()
                assertThat(data.baseCurrency).isEqualTo("EUR")
                assertThat(data.rates).hasSize(2)
                assertThat(data.rates).containsExactly(
                    DomainRate(Rate.GBP, 1.1f), DomainRate(Rate.RUB, 4.1f)
                ).inOrder()
            }
            cancel()
            expectNoMoreEvents()
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `async manager should handle exception properly`() = coroutinesTestRule.runBlockingTest {
        // Given
        val exception = Failure.ServerFailure("Couldn't connect the server.")

        // When
        val flow = asyncManager.handleAsyncWithTryCatch { throw exception }

        // Then
        flow.test {
            expectItem().run {
                assertThat(this).isInstanceOf(Result.Error::class.java)
                this as Result.Error
                assertThat(failure).isInstanceOf(Failure.ServerFailure::class.java)
                assertThat(failure.message).isEqualTo("Couldn't connect the server.")
            }
            cancelAndIgnoreRemainingEvents()
        }
    }
}

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
package io.github.nuhkoca.libbra.domain.repository

import BaseTestClass
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth
import io.github.nuhkoca.libbra.data.Result
import io.github.nuhkoca.libbra.data.datasource.DataSource
import io.github.nuhkoca.libbra.data.enums.Rate
import io.github.nuhkoca.libbra.data.model.domain.CurrencyResponse
import io.github.nuhkoca.libbra.data.shared.rule.CoroutinesTestRule
import io.github.nuhkoca.libbra.shared.assertion.test
import io.github.nuhkoca.libbra.shared.ext.runBlockingTest
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * A test class for [CurrencyRepository]
 */
@RunWith(MockitoJUnitRunner::class)
@MediumTest
class CurrencyRepositoryTest : BaseTestClass() {

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
    private lateinit var dataSource: DataSource

    @RelaxedMockK
    private lateinit var currencyResponse: CurrencyResponse

    /*
     -----------------------
    |    Private members    |
     -----------------------
    */
    private lateinit var repository: Repository
    private val currencySlot = slot<Rate>()

    override fun setUp() {
        super.setUp()

        every { dataSource.getCurrencyList(capture(currencySlot)) } answers {
            flowOf(Result.Success(currencyResponse))
        }

        every { currencyResponse.baseCurrency } returns "THB"

        repository = CurrencyRepository(dataSource)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `repository should return data`() = coroutinesTestRule.runBlockingTest {
        // Given
        val base = Rate.THB

        // When
        val flow = repository.getCurrencyList(base)

        // Then
        flow.test {
            expectItem().run {
                Truth.assertThat(this).isNotNull()
                Truth.assertThat(this).isInstanceOf(Result.Success::class.java)
                this as Result.Success
                Truth.assertThat(data.baseCurrency).isEqualTo(currencySlot.captured.name)
                Truth.assertThat(data.rates).isNotNull()
            }
            expectComplete()
        }

        verify(exactly = 1) { dataSource.getCurrencyList(any()) }
        confirmVerified(dataSource)
    }
}

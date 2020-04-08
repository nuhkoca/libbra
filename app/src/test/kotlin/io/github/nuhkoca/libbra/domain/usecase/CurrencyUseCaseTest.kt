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
package io.github.nuhkoca.libbra.domain.usecase

import BaseTestClass
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import io.github.nuhkoca.libbra.data.Result
import io.github.nuhkoca.libbra.data.enums.Rate
import io.github.nuhkoca.libbra.data.model.domain.CurrencyResponse
import io.github.nuhkoca.libbra.data.model.view.CurrencyResponseViewItem
import io.github.nuhkoca.libbra.data.shared.rule.CoroutinesTestRule
import io.github.nuhkoca.libbra.domain.repository.Repository
import io.github.nuhkoca.libbra.shared.assertion.test
import io.github.nuhkoca.libbra.shared.ext.runBlockingTest
import io.github.nuhkoca.libbra.util.mapper.Mapper
import io.mockk.coEvery
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * A test class for [CurrencyUseCase]
 */
@RunWith(MockitoJUnitRunner::class)
@MediumTest
class CurrencyUseCaseTest : BaseTestClass() {

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
    private lateinit var repository: Repository

    @MockK
    private lateinit var mapper: Mapper<CurrencyResponse, CurrencyResponseViewItem>

    @RelaxedMockK
    private lateinit var currencyResponse: CurrencyResponse

    /*
     -----------------------
    |    Private members    |
     -----------------------
    */
    private lateinit var useCase: UseCase.FlowUseCase<CurrencyParams, CurrencyResponseViewItem>
    private val currencySlot = slot<Rate>()

    override fun setUp() {
        super.setUp()

        every { repository.getCurrencyList(capture(currencySlot)) } answers {
            flowOf(Result.Success(currencyResponse))
        }

        coEvery { mapper.map(any()) } returns CurrencyResponseViewItem(
            baseCurrency = "HRK",
            rates = listOf(mockk(), mockk())
        )

        every { currencyResponse.baseCurrency } returns "HRK"

        useCase = CurrencyUseCase(repository, mapper)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `use case should return data properly`() = coroutinesTestRule.runBlockingTest {
        // Given
        val base = Rate.HRK

        // Then
        val flow = useCase.execute(CurrencyParams(base))

        // Then
        flow.test {
            expectItem().run {
                assertThat(this).isNotNull()
                assertThat(this).isInstanceOf(Result.Success::class.java)
                this as Result.Success
                assertThat(data.baseCurrency).isEqualTo(base.name)
                assertThat(data.rates).isNotNull()
            }
            expectComplete()
        }

        verify(exactly = 1) { repository.getCurrencyList(any()) }
        confirmVerified(repository)
    }
}

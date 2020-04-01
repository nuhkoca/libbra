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
package io.github.nuhkoca.libbra.ui.currency

import BaseTestClass
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.filters.LargeTest
import com.google.common.truth.Truth.assertThat
import io.github.nuhkoca.libbra.data.Result
import io.github.nuhkoca.libbra.data.enums.Rate
import io.github.nuhkoca.libbra.data.failure.Failure
import io.github.nuhkoca.libbra.data.model.domain.CurrencyResponse
import io.github.nuhkoca.libbra.data.model.view.CurrencyResponseViewItem
import io.github.nuhkoca.libbra.data.model.view.RateViewItem
import io.github.nuhkoca.libbra.data.shared.rule.CoroutinesTestRule
import io.github.nuhkoca.libbra.domain.usecase.CurrencyParams
import io.github.nuhkoca.libbra.domain.usecase.UseCase
import io.github.nuhkoca.libbra.util.mapper.Mapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@LargeTest
class CurrencyViewModelTest : BaseTestClass() {

    /*
     ------------
    |    Rules   |
     ------------
    */
    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    /*
     -------------
    |    Mocks    |
     -------------
    */
    @MockK
    private lateinit var useCase: UseCase.FlowUseCase<CurrencyParams, CurrencyResponse>

    @MockK
    private lateinit var mapper: Mapper<CurrencyResponse, CurrencyResponseViewItem>

    @RelaxedMockK
    private lateinit var currencyResponse: CurrencyResponse

    @RelaxedMockK
    private lateinit var observer: Observer<CurrencyViewModel.CurrencyViewState>

    /*
     -------------------------
    |    Private variables    |
     -------------------------
    */
    private lateinit var currencyViewModel: CurrencyViewModel

    @ExperimentalCoroutinesApi
    override fun setUp() {
        super.setUp()

        currencyViewModel =
            CurrencyViewModel(
                useCase,
                mapper,
                coroutinesTestRule.testDispatcherProvider
            )
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `currency view state should be filled after data has been fetched`() {
        every { useCase.execute(any()) } answers {
            flowOf(Result.Success(currencyResponse))
        }

        coEvery { mapper.map(any()) } returns CurrencyResponseViewItem(
            baseCurrency = "EUR",
            rates = listOf(
                RateViewItem(
                    id = 0,
                    abbreviation = "EUR",
                    longName = "Euro",
                    amount = 1f,
                    icon = 2131230839
                ),
                RateViewItem(
                    id = 1,
                    abbreviation = "GBP",
                    longName = "Pound",
                    amount = 0.8f,
                    icon = 2131230840
                )
            )
        )

        currencyViewModel.currencyLiveData.observeForever(observer)

        currencyViewModel.setBaseCurrency(Rate.EUR)

        val value = currencyViewModel.currencyLiveData.value

        assertThat(value?.isLoading).isFalse()
        assertThat(value?.hasError).isFalse()
        assertThat(value?.errorMessage).isNull()
        assertThat(value?.data).isNotNull()
        assertThat(value?.data?.baseCurrency).isAtLeast("EUR")
        assertThat(value?.data?.rates).hasSize(2)

        verify { useCase.execute(any()) }
        coVerify { mapper.map(any()) }

        confirmVerified(useCase)
        confirmVerified(mapper)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `currency view state should be filled after the exception occurred`() {
        every { useCase.execute(any()) } answers {
            flowOf(Result.Error(Failure.ServerFailure("Couldn't connect the server.")))
        }

        currencyViewModel.currencyLiveData.observeForever(observer)

        currencyViewModel.setBaseCurrency(Rate.EUR)

        val value = currencyViewModel.currencyLiveData.value

        assertThat(value?.isLoading).isFalse()
        assertThat(value?.hasError).isTrue()
        assertThat(value?.errorMessage).isNotNull()
        assertThat(value?.errorMessage).isEqualTo("Couldn't connect the server.")
        assertThat(value?.data).isNull()
        assertThat(value?.data?.rates).isNull()

        verify { useCase.execute(any()) }

        confirmVerified(useCase)
    }
}

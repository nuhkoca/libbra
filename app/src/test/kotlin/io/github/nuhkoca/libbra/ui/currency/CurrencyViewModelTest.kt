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
import io.github.nuhkoca.libbra.data.model.view.CurrencyResponseViewItem
import io.github.nuhkoca.libbra.data.shared.rule.CoroutinesTestRule
import io.github.nuhkoca.libbra.domain.usecase.CurrencyParams
import io.github.nuhkoca.libbra.domain.usecase.UseCase
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
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
    private lateinit var useCase: UseCase.FlowUseCase<CurrencyParams, CurrencyResponseViewItem>

    @RelaxedMockK
    private lateinit var observer: Observer<CurrencyViewModel.CurrencyViewState>

    /*
     -------------------------
    |    Private variables    |
     -------------------------
    */
    private lateinit var currencyViewModel: CurrencyViewModel
    private val currencySlot = slot<Rate>()

    @ExperimentalCoroutinesApi
    override fun setUp() {
        super.setUp()

        currencyViewModel = CurrencyViewModel(useCase)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `currency view state should be filled after data has been fetched`() {
        every { useCase.execute(any()) } answers {
            flowOf(
                Result.Success(
                    CurrencyResponseViewItem(
                        baseCurrency = "EUR",
                        rates = listOf(mockk(), mockk())
                    )
                )
            )
        }

        currencyViewModel.currencyLiveData.observeForever(observer)

        currencyViewModel.setContinuation(true)
        currencyViewModel.setBaseCurrency(Rate.EUR)

        val value = currencyViewModel.currencyLiveData.value

        assertThat(value?.isLoading).isFalse()
        assertThat(value?.hasError).isFalse()
        assertThat(value?.errorMessage).isNull()
        assertThat(value?.data).isNotNull()
        assertThat(value?.data?.baseCurrency).isEqualTo("EUR")
        assertThat(value?.data?.rates).hasSize(2)

        verify(atLeast = 1) { useCase.execute(any()) }

        confirmVerified(useCase)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `currency view state should be filled after the exception occurred`() {
        every { useCase.execute(any()) } answers {
            flowOf(Result.Error(Failure.ServerFailure("Couldn't connect the server.")))
        }

        currencyViewModel.currencyLiveData.observeForever(observer)

        currencyViewModel.setContinuation(true)
        currencyViewModel.setBaseCurrency(Rate.EUR)

        val value = currencyViewModel.currencyLiveData.value

        assertThat(value?.isLoading).isFalse()
        assertThat(value?.hasError).isTrue()
        assertThat(value?.errorMessage).isNotNull()
        assertThat(value?.errorMessage).isEqualTo("Couldn't connect the server.")
        assertThat(value?.data).isNull()
        assertThat(value?.data?.rates).isNull()

        verify(atLeast = 1) { useCase.execute(any()) }

        confirmVerified(useCase)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `network process should not happen in case of pause`() {
        every { useCase.execute(any()) } returns emptyFlow()

        currencyViewModel.currencyLiveData.observeForever(observer)

        currencyViewModel.setContinuation(false)
        currencyViewModel.setBaseCurrency(Rate.EUR)

        val value = currencyViewModel.currencyLiveData.value

        assertThat(value).isNull()

        verify(atLeast = 1) { useCase.execute(any()) }

        confirmVerified(useCase)
    }
}

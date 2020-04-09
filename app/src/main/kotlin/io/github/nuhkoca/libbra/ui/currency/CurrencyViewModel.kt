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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import io.github.nuhkoca.libbra.data.Result
import io.github.nuhkoca.libbra.data.enums.Rate
import io.github.nuhkoca.libbra.data.model.view.CurrencyResponseViewItem
import io.github.nuhkoca.libbra.data.succeeded
import io.github.nuhkoca.libbra.domain.usecase.CurrencyParams
import io.github.nuhkoca.libbra.domain.usecase.UseCase
import io.github.nuhkoca.libbra.ui.di.MainScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@MainScope
class CurrencyViewModel @Inject constructor(
    private val currencyUseCase: @JvmSuppressWildcards UseCase.FlowUseCase<CurrencyParams, CurrencyResponseViewItem>
) : ViewModel() {

    private var job = Job()

    private var lastKnownCurrency: Rate = Rate.EUR

    private val baseCurrencyLiveData = MutableLiveData(Rate.EUR)

    private val _currencyLiveData = MutableLiveData<CurrencyViewState>()

    val currencyLiveData: LiveData<CurrencyViewState> =
        baseCurrencyLiveData.switchMap { rate -> getCurrencyList(rate) }

    /**
     * Specifies the continuation state for network call.
     *
     * @param isContinue if true flow is resumed otherwise paused
     */
    fun setContinuation(isContinue: Boolean) {
        if (isContinue) {
            job = Job()
            refresh()
        } else {
            if (job.isCancelled.not()) {
                job.cancel()
            }
        }
    }

    /**
     * Proceeds with the last known currency in case e.g. network cut off.
     */
    fun refresh() {
        baseCurrencyLiveData.value = lastKnownCurrency
    }

    /**
     * Sets base currency to fetch list of currencies.
     *
     * @param base represents the base currency
     */
    fun setBaseCurrency(base: Rate) {
        baseCurrencyLiveData.value = base
        lastKnownCurrency = base
    }

    /**
     * Fetches the list of currencies based on [base].
     *
     * @param base represents the base currency
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getCurrencyList(base: Rate = Rate.EUR): LiveData<CurrencyViewState> {
        return currencyUseCase.execute(CurrencyParams(base))
            .map { result ->
                return@map if (result.succeeded) {
                    result as Result.Success
                    currentViewState.copy(data = result.data, isLoading = false)
                } else {
                    result as Result.Error
                    currentViewState.copy(
                        isLoading = false,
                        hasError = true,
                        errorMessage = result.failure.message
                    )
                }
            }.asLiveData(job)
            .distinctUntilChanged()
    }

    private inline val currentViewState: CurrencyViewState
        get() = _currencyLiveData.value ?: CurrencyViewState()

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    /**
     * A data class which represents UI State.
     *
     * @property data The data to be injected into
     * @property isLoading The loading state
     * @property hasError The flag indicates error state
     * @property errorMessage The error message
     */
    data class CurrencyViewState(
        val data: CurrencyResponseViewItem? = null,
        val isLoading: Boolean = true,
        val hasError: Boolean = false,
        val errorMessage: String? = null
    )
}

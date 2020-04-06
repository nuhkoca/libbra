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
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import io.github.nuhkoca.libbra.data.Result
import io.github.nuhkoca.libbra.data.enums.Rate
import io.github.nuhkoca.libbra.data.model.domain.CurrencyResponse
import io.github.nuhkoca.libbra.data.model.view.CurrencyResponseViewItem
import io.github.nuhkoca.libbra.data.succeeded
import io.github.nuhkoca.libbra.domain.usecase.CurrencyParams
import io.github.nuhkoca.libbra.domain.usecase.UseCase
import io.github.nuhkoca.libbra.ui.di.MainScope
import io.github.nuhkoca.libbra.util.coroutines.AsyncManager.Continuation
import io.github.nuhkoca.libbra.util.coroutines.DispatcherProvider
import io.github.nuhkoca.libbra.util.mapper.Mapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@MainScope
class CurrencyViewModel @Inject constructor(
    private val currencyUseCase: @JvmSuppressWildcards UseCase.FlowUseCase<CurrencyParams, CurrencyResponse>,
    private val mapper: @JvmSuppressWildcards Mapper<CurrencyResponse, CurrencyResponseViewItem>,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val baseCurrencyLiveData = MutableLiveData<Rate>()
    private val continuationLiveData = MutableLiveData<Continuation>()

    private val _currencyLiveData = MutableLiveData<CurrencyViewState>()

    val currencyLiveData: LiveData<CurrencyViewState> =
        Transformations.switchMap(baseCurrencyLiveData) { rate ->
            Transformations.switchMap(continuationLiveData) { continuation ->
                getCurrencyList(rate, continuation)
            }
        }

    init {
        setContinuation(true)
        setBaseCurrency(Rate.EUR)
    }

    fun setContinuation(isContinue: Boolean) = apply {
        continuationLiveData.value = if (isContinue) Continuation.RESUME else Continuation.PAUSE
    }

    fun setBaseCurrency(base: Rate) = apply { baseCurrencyLiveData.value = base }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getCurrencyList(
        base: Rate = Rate.EUR,
        continuation: Continuation = Continuation.RESUME
    ): LiveData<CurrencyViewState> {
        return currencyUseCase.execute(CurrencyParams(base, continuation))
            .mapLatest { result ->
                return@mapLatest if (result.succeeded) {
                    result as Result.Success
                    val viewItem = mapper.map(result.data)
                    currentViewState.copy(data = viewItem, isLoading = false)
                } else {
                    result as Result.Error
                    currentViewState.copy(
                        isLoading = false,
                        hasError = true,
                        errorMessage = result.failure.message
                    )
                }
            }.asLiveData(dispatcherProvider.io + viewModelScope.coroutineContext)
    }

    private inline val currentViewState: CurrencyViewState
        get() = _currencyLiveData.value ?: CurrencyViewState()

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

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

import io.github.nuhkoca.libbra.data.Result
import io.github.nuhkoca.libbra.data.enums.Rate
import io.github.nuhkoca.libbra.data.model.domain.CurrencyResponse
import io.github.nuhkoca.libbra.data.model.raw.CurrencyResponseRaw
import io.github.nuhkoca.libbra.data.service.CurrencyService
import io.github.nuhkoca.libbra.util.coroutines.AsyncManager
import io.github.nuhkoca.libbra.util.coroutines.AsyncManager.Continuation
import io.github.nuhkoca.libbra.util.coroutines.AsyncManager.Continuation.RESUME
import io.github.nuhkoca.libbra.util.coroutines.DefaultAsyncManager
import io.github.nuhkoca.libbra.util.coroutines.DispatcherProvider
import io.github.nuhkoca.libbra.util.mapper.Mapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A [DataSource] implementation to fetch list of currencies remotely.
 *
 * @param currencyService The service to hit the endpoint
 * @param mapper The domain mapper to map raw data to domain
 * @param dispatcherProvider The [DispatcherProvider] to run calls under a specific context
 */
@Singleton
class CurrencyRemoteDataSource @Inject constructor(
    private val currencyService: CurrencyService,
    private val mapper: @JvmSuppressWildcards Mapper<CurrencyResponseRaw, CurrencyResponse>,
    private val dispatcherProvider: DispatcherProvider
) : DataSource, AsyncManager by DefaultAsyncManager(dispatcherProvider) {

    /**
     * Fetches list of currencies and returns in [Flow] builder
     *
     * @param continuation indicates flow state. If [RESUME] flow is resumed otherwise paused.
     * @param base The base currency to fetch list
     *
     * @return [CurrencyResponse] within [Flow] builder
     */
    @ExperimentalCoroutinesApi
    override fun getCurrencyList(
        base: Rate,
        continuation: Continuation
    ): Flow<Result<CurrencyResponse>> {
        return handleAsyncWithTryCatch(continuation) {
            val response = currencyService.getCurrencyList(base)
            mapper.map(response)
        }
    }
}

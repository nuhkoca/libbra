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
package io.github.nuhkoca.libbra.data.mapper

import io.github.nuhkoca.libbra.data.model.domain.CurrencyResponse
import io.github.nuhkoca.libbra.data.model.domain.Rate
import io.github.nuhkoca.libbra.data.model.raw.CurrencyResponseRaw
import io.github.nuhkoca.libbra.util.coroutines.DispatcherProvider
import io.github.nuhkoca.libbra.util.mapper.Mapper
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A [Mapper] implementation to map [CurrencyResponseRaw] to [CurrencyResponse] type.
 *
 * @param dispatcherProvider The [DispatcherProvider] to run calls under a specific context
 */
@Singleton
class CurrencyDomainMapper @Inject constructor(
    private val dispatcherProvider: DispatcherProvider
) : Mapper<CurrencyResponseRaw, CurrencyResponse> {

    /**
     * A suspend function that maps [CurrencyResponseRaw] to [CurrencyResponse] type.
     *
     * @param item The [CurrencyResponseRaw]
     *
     * @return [CurrencyResponse]
     */
    override suspend fun map(item: CurrencyResponseRaw) = withContext(dispatcherProvider.default) {
        val rates = mutableListOf<Rate>()

        item.rates.forEach { currency ->
            val rate = Rate(currency.key, currency.value)
            rates.add(rate)
        }

        CurrencyResponse(item.baseCurrency, rates)
    }
}

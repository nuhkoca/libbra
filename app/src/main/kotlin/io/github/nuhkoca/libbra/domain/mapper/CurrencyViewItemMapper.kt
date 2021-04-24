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
package io.github.nuhkoca.libbra.domain.mapper

import io.github.nuhkoca.libbra.data.enums.Rate
import io.github.nuhkoca.libbra.data.model.domain.CurrencyResponse
import io.github.nuhkoca.libbra.data.model.view.CurrencyResponseViewItem
import io.github.nuhkoca.libbra.data.model.view.RateViewItem
import io.github.nuhkoca.libbra.util.coroutines.DispatcherProvider
import io.github.nuhkoca.libbra.util.mapper.Mapper
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A [Mapper] implementation to map [CurrencyResponse] to [CurrencyResponseViewItem] type.
 *
 * @param dispatcherProvider The [DispatcherProvider] to run calls under a specific context
 */
@Singleton
class CurrencyViewItemMapper @Inject constructor(
    private val dispatcherProvider: DispatcherProvider
) : Mapper<CurrencyResponse, CurrencyResponseViewItem> {

    /**
     * A suspend function that maps [CurrencyResponse] to [CurrencyResponseViewItem] type.
     *
     * @param item The [CurrencyResponse]
     *
     * @return [CurrencyResponseViewItem]
     */
    override suspend fun map(item: CurrencyResponse) = withContext(dispatcherProvider.default) {
        val rates = mutableListOf<RateViewItem>()

        rates with item.baseCurrency

        item.rates.forEachIndexed { index, currency ->
            val rateViewItem = RateViewItem(
                id = index + 1,
                abbreviation = currency.rate.name,
                longName = currency.rate.longName,
                amount = currency.amount,
                icon = currency.rate.resId
            )
            rates.add(index = index + 1, element = rateViewItem)
        }

        CurrencyResponseViewItem(baseCurrency = item.baseCurrency, rates = rates)
    }
}

/**
 * An infix fun that adds responder to currency list to show it at top.
 *
 * @param baseCurrency represents responder
 */
private infix fun MutableList<RateViewItem>.with(baseCurrency: String) {
    // Add base currency to currency list to show it at top.
    val rate = Rate.valueOf(baseCurrency)
    val responder = RateViewItem(
        id = 0, // First id should always be 0
        abbreviation = rate.name,
        longName = rate.longName,
        amount = 1.0f, // Because base currency amount is always 1
        icon = rate.resId
    )
    add(index = 0, element = responder)
}

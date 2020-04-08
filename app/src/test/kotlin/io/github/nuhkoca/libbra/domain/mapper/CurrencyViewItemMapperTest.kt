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

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import io.github.nuhkoca.libbra.data.enums.Rate
import io.github.nuhkoca.libbra.data.model.domain.CurrencyResponse
import io.github.nuhkoca.libbra.data.model.view.CurrencyResponseViewItem
import io.github.nuhkoca.libbra.data.model.view.RateViewItem
import io.github.nuhkoca.libbra.data.shared.rule.CoroutinesTestRule
import io.github.nuhkoca.libbra.shared.ext.runBlockingTest
import io.github.nuhkoca.libbra.util.mapper.Mapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import io.github.nuhkoca.libbra.data.model.domain.Rate as DomainRate

/**
 * A test class for [CurrencyViewItemMapper]
 */
@RunWith(MockitoJUnitRunner::class)
@SmallTest
class CurrencyViewItemMapperTest {

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
    private lateinit var mapper: Mapper<CurrencyResponse, CurrencyResponseViewItem>

    @Before
    @ExperimentalCoroutinesApi
    fun setUp() {
        mapper = CurrencyViewItemMapper(coroutinesTestRule.testDispatcherProvider)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `mapper should map domain data to view item type properly`() =
        coroutinesTestRule.runBlockingTest {
            // Given
            val currencyResponse = CurrencyResponse(
                baseCurrency = "EUR",
                rates = listOf(DomainRate(Rate.BGN, 3.9f), DomainRate(Rate.CHF, 2.1f))
            )

            // When
            val response = mapper.map(currencyResponse)

            // Then
            assertThat(response).isNotNull()
            assertThat(response.baseCurrency).isEqualTo("EUR")
            assertThat(response.rates).isNotNull()
            assertThat(response.rates).hasSize(3)
            assertThat(response.rates).containsExactlyElementsIn(
                listOf(
                    RateViewItem(
                        id = 0,
                        abbreviation = "EUR",
                        longName = "Euro",
                        amount = 1.0f,
                        icon = 2131230839
                    ),
                    RateViewItem(
                        id = 1,
                        abbreviation = "BGN",
                        longName = "Bulgarian Lev",
                        amount = 3.9f,
                        icon = 2131230829
                    ),
                    RateViewItem(
                        id = 2,
                        abbreviation = "CHF",
                        longName = "Swiss Franc",
                        amount = 2.1f,
                        icon = 2131230833
                    )
                )
            ).inOrder()
        }
}

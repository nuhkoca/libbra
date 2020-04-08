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

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import io.github.nuhkoca.libbra.data.enums.Rate
import io.github.nuhkoca.libbra.data.model.domain.CurrencyResponse
import io.github.nuhkoca.libbra.data.model.raw.CurrencyResponseRaw
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
 * A test class for [CurrencyDomainMapper]
 */
@RunWith(MockitoJUnitRunner::class)
@SmallTest
class CurrencyDomainMapperTest {

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
    private lateinit var mapper: Mapper<CurrencyResponseRaw, CurrencyResponse>

    @Before
    @ExperimentalCoroutinesApi
    fun setUp() {
        mapper = CurrencyDomainMapper(coroutinesTestRule.testDispatcherProvider)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `mapper should map raw data to domain type properly`() =
        coroutinesTestRule.runBlockingTest {
            // Given
            val currencyResponseRaw = CurrencyResponseRaw(
                baseCurrency = "EUR",
                rates = mapOf(Rate.CZK to 1.2f, Rate.AUD to 3.5f)
            )

            // When
            val response = mapper.map(currencyResponseRaw)

            // Then
            assertThat(response).isNotNull()
            assertThat(response.baseCurrency).isEqualTo("EUR")
            assertThat(response.rates).isNotNull()
            assertThat(response.rates).hasSize(2)
            assertThat(response.rates).containsExactlyElementsIn(
                listOf(DomainRate(Rate.CZK, 1.2f), DomainRate(Rate.AUD, 3.5f))
            ).inOrder()
        }
}

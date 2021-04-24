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
package io.github.nuhkoca.libbra.data.serializers

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import io.github.nuhkoca.libbra.data.enums.Rate
import kotlinx.serialization.json.Json
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * A test class for [RateSerializer]
 */
@RunWith(MockitoJUnitRunner::class)
@SmallTest
class RateSerializerTest {

    /*
     -----------------------
    |    Private members    |
     -----------------------
    */
    private val serializer = RateSerializer

    @Test
    fun `serializer should serialize the given enum object properly`() {
        // Given
        val rate = Rate.BRL

        // When
        val rateAsString = Json.encodeToString(serializer, rate)

        // Then
        assertThat(rateAsString).isNotEmpty()
        assertThat(rateAsString).isEqualTo("\"${rate.name}\"")
    }

    @Test
    fun `serializer should parse the given string to corresponding enum type`() {
        // Given
        val rateAsString = "CZK"

        // When
        val rate = Json { isLenient = true }.decodeFromString(serializer, rateAsString)

        // Then
        assertThat(rate).isNotNull()
        assertThat(rate).isInstanceOf(Rate::class.java)
        assertThat(rate).isEqualTo(Rate.CZK)
    }

    @Test
    fun `serializer should map undefined item to UNKNOWN type`() {
        // Given
        val rateAsString = "UND"

        // When
        val rate = Json { isLenient = true }.decodeFromString(serializer, rateAsString)

        // Then
        assertThat(rate).isNotNull()
        assertThat(rate).isInstanceOf(Rate::class.java)
        assertThat(rate).isEqualTo(Rate.UNKNOWN)
    }
}

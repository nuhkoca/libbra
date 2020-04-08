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
package io.github.nuhkoca.libbra.util.formatter

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * A test class for [CurrencyFormatter]
 */
@RunWith(MockitoJUnitRunner::class)
class CurrencyFormatterTest {

    private val formatter: Formatter = CurrencyFormatter()

    @Test
    fun `formatter should format given text properly`() {
        // Given
        val text = "1235"

        // When
        val result = formatter.formatText(text)

        // Then
        assertThat(result).isNotEmpty()
        assertThat(result).isEqualTo("1,235")
    }

    @Test
    fun `formatter should format given number properly`() {
        // Given
        val number = 12345678

        // When
        val result = formatter.formatText(number)

        // Then
        assertThat(result).isNotEmpty()
        assertThat(result).isEqualTo("12,345,678")
    }

    @Test
    fun `formatter should parse given text properly`() {
        // Given
        val text = "12345"

        // When
        val result = formatter.parseText(text)

        // Then
        assertThat(result).isEqualTo(12345)
    }
}

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
package io.github.nuhkoca.libbra.data.enums

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

/**
 * A parameterized test class for [Rate]
 */
@SmallTest
class RateTest {

    /**
     * A parameterized test function that iterates and performs test on all rates.
     *
     * @param rate represents any [Rate]
     */
    @ParameterizedTest
    @EnumSource(Rate::class)
    fun `any rate length should be at least 3`(rate: Rate) {
        assertThat(rate.name).isNotNull()
        // We know all rates 3-digit length except UNKNOWN
        assertThat(rate.name.length).isAtLeast(3)
    }
}

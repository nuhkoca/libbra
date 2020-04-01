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
package io.github.nuhkoca.libbra.data.verifier

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import io.github.nuhkoca.libbra.shared.BASE_URL
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * A test class for [RevolutHostnameVerifier]
 */
@RunWith(MockitoJUnitRunner::class)
@SmallTest
class RevolutHostnameVerifierTest {

    /*
     -----------------------
    |    Private members    |
     -----------------------
    */
    private val revolutHostNameVerifierTest: RevolutHostnameVerifier
        get() = RevolutHostnameVerifier

    @Test
    fun `verifier should verify the base url`() {
        // Given
        val hostname = BASE_URL

        // When
        val result = revolutHostNameVerifierTest.verify(hostname, null)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `verifier should not allow any network traffic except base url`() {
        // Given
        val hostname = null

        // When
        val result = revolutHostNameVerifierTest.verify(hostname, null)

        // Then
        assertThat(result).isFalse()
    }
}

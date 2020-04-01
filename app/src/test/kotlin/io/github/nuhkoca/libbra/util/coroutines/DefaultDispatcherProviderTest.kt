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
package io.github.nuhkoca.libbra.util.coroutines

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineDispatcher
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

/**
 * A parameterized test for [DefaultDispatcherProvider]
 */
@SmallTest
class DefaultDispatcherProviderTest {

    /**
     * A parameterized test function that iterates and performs test on all [CoroutineDispatcher].
     *
     * @param dispatcher represents any [CoroutineDispatcher]
     */
    @ParameterizedTest
    @ArgumentsSource(CustomArgumentsProvider::class)
    fun `any dispatcher should not be null`(dispatcher: CoroutineDispatcher) {
        assertThat(dispatcher).isNotNull()
    }
}

/**
 * A custom [ArgumentsProvider] to have any [CoroutineDispatcher] parameterized in tests.
 */
private class CustomArgumentsProvider : ArgumentsProvider {

    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()

    /**
     * Provides a stream of arguments to be passed to a [ParameterizedTest] method.
     *
     * @param extensionContext the current extension context
     *
     * @return a stream of arguments
     */
    @Throws(Exception::class)
    override fun provideArguments(extensionContext: ExtensionContext?): Stream<out Arguments?>? {
        return Stream.of(
            dispatcherProvider.main,
            dispatcherProvider.io,
            dispatcherProvider.default,
            dispatcherProvider.unconfined
        ).map { dispatcher -> Arguments.of(dispatcher) }
    }
}

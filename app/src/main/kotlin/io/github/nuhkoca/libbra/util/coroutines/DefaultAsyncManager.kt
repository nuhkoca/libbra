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

import io.github.nuhkoca.libbra.data.Result
import io.github.nuhkoca.libbra.data.failure.Failure
import io.github.nuhkoca.libbra.data.failure.Failure.UnhandledFailure
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn

/**
 * The default implementation of [AsyncManager]. This class is a generic handler and will cover any
 * asynchronous calls accordingly. This class should be provided by the class delegation in Kotlin.
 *
 * @param dispatcher The [DispatcherProvider] to run calls under a specific context
 */
internal class DefaultAsyncManager(private val dispatcher: DispatcherProvider) : AsyncManager {

    /**
     * Handles any asynchronous cal and waits for its result. This wrapper also catches errors and
     * delivers to upper layer.
     *
     * @param body a suspend body
     *
     * @return [T] with [Result] wrapper.
     */
    @ExperimentalCoroutinesApi
    override fun <T> handleAsyncWithTryCatch(body: suspend () -> T): Flow<Result<T>> {
        return channelFlow<Result<T>> {
            while (!isClosedForSend) {
                delay(DELAY_IN_MS)
                send(Result.Success(body()))
            }
        }.catch { e ->
            when (e) {
                is Failure -> emit(Result.Error(e))
                else -> emit(Result.Error(UnhandledFailure(e.message.toString())))
            }
        }.flowOn(dispatcher.io)
    }

    private companion object {
        private const val DELAY_IN_MS = 1000L
    }
}

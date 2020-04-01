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
import kotlinx.coroutines.flow.Flow

/**
 * A main interface to manage asynchronous calls and catch error accordingly. [DefaultAsyncManager]
 * implements this interface and this interface shouldn't be implemented directly by any repository.
 * Instead, [DefaultAsyncManager] reference should be specified according to the class delegation.
 */
@FunctionalInterface
interface AsyncManager {

    /**
     * Handles any asynchronous call and waits for its result. This wrapper also catches errors and
     * delivers to upper layer.
     *
     * @param body The suspend body to be called
     *
     * @return [T] within [Flow] builder.
     */
    fun <T> handleAsyncWithTryCatch(
        body: suspend () -> T
    ): Flow<Result<T>>
}

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
package io.github.nuhkoca.libbra.domain.usecase

import io.github.nuhkoca.libbra.data.Result
import kotlinx.coroutines.flow.Flow

/**
 * An intermediate interface between repository and UI layers.
 */
interface UseCase {

    /**
     * An intermediate interface to execute Coroutines calls.
     *
     * @param P The [Params]
     * @param T The data class
     */
    @FunctionalInterface
    interface FlowUseCase<in P, out T> : UseCase where P : Params {

        /**
         * Executes the call with the given parameters.
         *
         * @param params The [Params]
         *
         * @return result within [Flow] builder
         */
        fun execute(params: P): Flow<Result<T>>
    }
}

/**
 * An abstract class to create parameters in order to hit the service. Any kind of param class
 * should be derived from this.
 */
abstract class Params

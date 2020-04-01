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
package io.github.nuhkoca.libbra.data.failure

import io.github.nuhkoca.libbra.data.failure.Failure.CancellationFailure
import io.github.nuhkoca.libbra.data.failure.Failure.SerializationFailure
import io.github.nuhkoca.libbra.data.failure.Failure.ServerFailure
import io.github.nuhkoca.libbra.data.failure.Failure.UnhandledFailure
import okhttp3.OkHttpClient
import java.io.IOException

/**
 * Failure class to hold any kind of errors from server. [ServerFailure] represents error response
 * e.g. parameters are incorrect, no result. [SerializationFailure] represents exceptional cases
 * e.g. parse exception, serialization exception. [CancellationFailure] represents cancellation
 * exception from Coroutines. [UnhandledFailure] represents unknown errors.
 *
 * @param message the error message
 *
 * @throws IOException as [OkHttpClient] is only able to consume [IOException]
 * @see [Corresponding exception issue](https://github.com/square/retrofit/issues/3110)
 *
 * @author Nuh Koca
 * @since 2020-03-05
 */
sealed class Failure(override val message: String) : IOException(message) {

    /**
     * Represents the error response itself.
     *
     * @param message the error message
     */
    data class ServerFailure(override val message: String) : Failure(message)

    /**
     * Represents other type of errors.
     *
     * @param message the error message
     */
    data class SerializationFailure(override val message: String) : Failure(message = message)

    /**
     * Represents cancellation errors from coroutines.
     *
     * @param message the error message
     */
    data class CancellationFailure(override val message: String) : Failure(message = message)

    /**
     * Represents unhandled errors by error handling mechanism.
     *
     * @param message the error message
     */
    data class UnhandledFailure(override val message: String) : Failure(message = message)
}

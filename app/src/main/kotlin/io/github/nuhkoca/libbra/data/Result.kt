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
package io.github.nuhkoca.libbra.data

import io.github.nuhkoca.libbra.data.failure.Failure

/**
 * A generic class that holds a value with its data status.
 * @param <T>
 */
sealed class Result<out R> {

    /**
     * Represents success data operation.
     *
     * @param data the data
     *
     * @return the data with [Result] wrapper.
     */
    data class Success<out T>(val data: T) : Result<T>()

    /**
     * Represents error operation.
     *
     * @param failure the [Failure]
     *
     * @return [Nothing] with [Result] wrapper.
     */
    data class Error(val failure: Failure) : Result<Nothing>() {
        override fun toString() = "Failure $failure"
    }
}

/**
 * `true` if [Result] is of type [Result.Success] & holds non-null [Result.Success.data].
 */
internal val Result<*>.succeeded
    get() = this is Result.Success && data != null

/**
 * Returns the result of [onSuccess] for encapsulated value if this instance represents success
 * or the result of [onFailure] function for encapsulated exception if it is failure.
 */
fun <R, T> Result<T>.fold(onSuccess: (T) -> R, onFailure: (Failure) -> R): R {
    return if (succeeded) {
        onSuccess((this as Result.Success).data)
    } else {
        onFailure((this as Result.Error).failure)
    }
}

/**
 * Returns the suspend result of [onSuccess] for encapsulated value if this instance represents
 * success or the suspend result of [onFailure] function for encapsulated failure if it is failure.
 */
suspend fun <R, T> Result<T>.foldSuspend(
    onSuccess: suspend (T) -> R,
    onFailure: suspend (Failure) -> R
): R {
    return if (succeeded) {
        onSuccess((this as Result.Success).data)
    } else {
        onFailure((this as Result.Error).failure)
    }
}

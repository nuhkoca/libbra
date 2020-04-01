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
@file:Suppress("unused")
@file:JvmName("InterceptorsKt")

package io.github.nuhkoca.libbra.util.ext

import io.github.nuhkoca.libbra.data.failure.ErrorResponse
import io.github.nuhkoca.libbra.data.failure.Failure
import io.github.nuhkoca.libbra.data.failure.Failure.CancellationFailure
import io.github.nuhkoca.libbra.data.failure.Failure.SerializationFailure
import io.github.nuhkoca.libbra.data.failure.Failure.ServerFailure
import io.github.nuhkoca.libbra.data.failure.Failure.UnhandledFailure
import kotlinx.serialization.SerializationException
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecodingException
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.CancellationException

@UnstableDefault
fun OkHttpClient.Builder.errorInterceptor() = ErrorInterceptor()

/**
 * An [Interceptor] implementation for error handling.
 */
@UnstableDefault
class ErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            // This also might throw IOException
            return chain.proceed(chain.request()).apply(::checkError)
        } catch (f: Failure) {
            throw f
        } catch (e: IOException) {
            throw UnhandledFailure(e.message.toString())
        }
    }

    /**
     * Detects any kind of error and handle these for all requests within the module.
     *
     * @param response the network response
     *
     * @throws IOException
     * @throws SerializationException
     */
    @Suppress("TooGenericExceptionCaught", "ThrowsCount")
    @UnstableDefault
    @Throws(IOException::class, SerializationException::class)
    private fun checkError(response: Response) {
        if (response.isSuccessful) return

        val errorBody = response.body ?: return
        /* Since string() loads entire response body into memory we should call body and string()
         * functions separately.
         */
        val errorString = errorBody.string()

        try {
            val errorResponse = Json.parse(ErrorResponse.serializer(), errorString)
            throw ServerFailure(errorResponse.message)
        } catch (e: Exception) {
            when (e) {
                is SerializationException, is JsonDecodingException -> {
                    throw SerializationFailure(e.message.toString())
                }
                is ServerFailure -> throw ServerFailure(e.message)
                is CancellationException -> throw CancellationFailure(e.message.toString())
                else -> throw UnhandledFailure(e.message.toString())
            }
        } finally {
            response.close()
        }
    }
}

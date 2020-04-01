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
package io.github.nuhkoca.libbra.data.service

import io.github.nuhkoca.libbra.data.enums.Rate
import io.github.nuhkoca.libbra.data.model.raw.CurrencyResponseRaw
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The service interface to fetch list of currencies
 */
interface CurrencyService {

    /**
     * Fetches list of currencies
     *
     * @param base The base currency to calculate amounts
     *
     * @return [CurrencyResponseRaw]
     */
    @GET("$ENDPOINT_PREFIX/latest")
    suspend fun getCurrencyList(@Query("base") base: Rate): CurrencyResponseRaw

    private companion object {
        private const val ENDPOINT_PREFIX = "api/android"
    }
}

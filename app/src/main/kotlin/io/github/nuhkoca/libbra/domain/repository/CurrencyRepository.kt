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
package io.github.nuhkoca.libbra.domain.repository

import io.github.nuhkoca.libbra.data.Result
import io.github.nuhkoca.libbra.data.datasource.DataSource
import io.github.nuhkoca.libbra.data.enums.Rate
import io.github.nuhkoca.libbra.data.model.domain.CurrencyResponse
import io.github.nuhkoca.libbra.di.Remote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A [Repository] implementation to interact with [DataSource] in order to fetch list of
 * currencies.
 *
 * @param remoteDataSource The data source
 */
@Singleton
class CurrencyRepository @Inject constructor(
    @Remote private val remoteDataSource: DataSource
) : Repository {

    /**
     * Fetches list of currencies and returns in [Flow] builder
     *
     * @param base The base currency to fetch list
     *
     * @return [CurrencyResponse] within [Flow] builder
     */
    override fun getCurrencyList(base: Rate): Flow<Result<CurrencyResponse>> {
        return remoteDataSource.getCurrencyList(base)
    }
}

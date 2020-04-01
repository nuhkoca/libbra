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
import io.github.nuhkoca.libbra.data.enums.Rate
import io.github.nuhkoca.libbra.data.model.domain.CurrencyResponse
import io.github.nuhkoca.libbra.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A [UseCase.FlowUseCase] implementation to interact with [Repository] in order to fetch list of
 * currencies.
 *
 * @param repository The repository
 */
@Singleton
class CurrencyUseCase @Inject constructor(
    private val repository: Repository
) : UseCase.FlowUseCase<CurrencyParams, CurrencyResponse> {

    /**
     * Executes the call with the given parameters.
     *
     * @param params The [CurrencyParams] to fetch list
     *
     * @return [CurrencyResponse] within [Flow] builder
     */
    override fun execute(params: CurrencyParams): Flow<Result<CurrencyResponse>> {
        return repository.getCurrencyList(params.base)
    }
}

/**
 * The data class to fetch list with base currency
 *
 * @property base The base currency
 */
data class CurrencyParams(
    val base: Rate = Rate.EUR
) : Params()

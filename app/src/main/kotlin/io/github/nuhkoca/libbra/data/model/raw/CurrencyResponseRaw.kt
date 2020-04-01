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
@file:UseSerializers(RateSerializer::class)

package io.github.nuhkoca.libbra.data.model.raw

import io.github.nuhkoca.libbra.data.enums.Rate
import io.github.nuhkoca.libbra.data.serializers.RateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

/**
 * A data class that includes list of currencies
 *
 * @property baseCurrency The base currency to calculate amounts
 * @property rates The list of currencies
 */
@Serializable
data class CurrencyResponseRaw(
    val baseCurrency: String,
    val rates: Map<Rate, Float>
)

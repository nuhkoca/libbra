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
package io.github.nuhkoca.libbra.binding.adapters

import androidx.databinding.InverseMethod
import io.github.nuhkoca.libbra.util.formatter.CurrencyFormatter
import io.github.nuhkoca.libbra.util.formatter.Formatter

/**
 * A DataBinding converter between Float and String to obtain current multiplier.
 */
object Converter {

    private val formatter: Formatter = CurrencyFormatter()

    @InverseMethod("stringToFloat")
    @JvmStatic
    fun floatToString(value: Float): String {
        return formatter.formatNumber(value)
    }

    @JvmStatic
    fun stringToFloat(value: String): Float {
        val number = formatter.parseText(value)
        return number.toFloat()
    }
}

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
package io.github.nuhkoca.libbra.util.formatter

import io.github.nuhkoca.libbra.binding.di.BindingScope
import java.text.ParseException
import javax.inject.Inject

/**
 * The [Formatter] implementation.
 */
@BindingScope
class CurrencyFormatter @Inject constructor() : Formatter {

    @Suppress("TooGenericExceptionCaught")
    override fun formatText(text: String): String {
        return try {
            val number = parseText(text)
            formatter.format(number)
        } catch (e: Exception) {
            return when (e) {
                is ParseException -> ""
                is IllegalArgumentException -> ""
                else -> ""
            }
        }
    }

    override fun formatText(number: Number?): String {
        return try {
            formatter.format(number)
        } catch (e: IllegalArgumentException) {
            ""
        }
    }

    override fun parseText(text: String): Number {
        return try {
            formatter.parse(text)
        } catch (e: ParseException) {
            0f
        }
    }
}

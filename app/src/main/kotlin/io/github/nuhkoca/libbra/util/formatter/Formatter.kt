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

import java.text.NumberFormat
import java.text.ParseException
import java.util.*

/**
 * A currency formatter interface that parses and formats any object.
 */
interface Formatter {

    /**
     * Formats given string to desired currency format
     *
     * @param text The text to be formatted
     *
     * @return [Number]
     */
    fun formatText(text: String): String

    /**
     * Formats given number to desired string
     *
     * @param number Any number to be formatted
     *
     * @return [Number]
     *
     * @throws IllegalArgumentException if the Format cannot format the given string
     */
    @Throws(IllegalArgumentException::class)
    fun formatNumber(number: Number?): String

    /**
     * Parses the given string to desired number
     *
     * @param text The string to be parsed
     *
     * @return [String]
     *
     * @throws ParseException if the beginning of the specified string cannot be parsed
     */
    @Throws(ParseException::class)
    fun parseText(text: String): Number

    /**
     * The default formatter
     */
    val formatter: NumberFormat
        get() = NumberFormat.getInstance(Locale.getDefault())
}

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

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import io.github.nuhkoca.libbra.binding.di.BindingScope
import io.github.nuhkoca.libbra.util.formatter.Formatter
import javax.inject.Inject

/**
 * A [BindingAdapter] for views whose can work with text.
 */
@BindingScope
class TextBindingAdapter @Inject constructor(private val formatter: Formatter) {

    /**
     * Formats hint for target TextView
     *
     * @param amount The amount
     */
    @BindingAdapter("android:hint")
    fun TextInputEditText.formatHint(amount: String) {
        val formattedAmount = formatter.formatText(amount)
        hint = formattedAmount
    }
}

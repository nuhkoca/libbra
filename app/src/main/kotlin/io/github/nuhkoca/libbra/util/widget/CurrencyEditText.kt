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
package io.github.nuhkoca.libbra.util.widget

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import com.google.android.material.textfield.TextInputEditText
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.util.*


/**
 * A custom [TextInputEditText] implementation which is designed for currency handling according to
 * the current locale.
 *
 * @param context The context
 * @param attributeSet The attribute set for the view
 */
class CurrencyEditText @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : TextInputEditText(context, attributeSet) {

    private var isEditing = false

    private val textWatcher = object : TextWatcher {
        @Synchronized
        override fun afterTextChanged(s: Editable) {
            if (isEditing) return
            isEditing = true
            val formattedAmount = formatText(s.toString())
            setText(formattedAmount)
            setSelection(formattedAmount.length)
            isEditing = false
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            if (isEditing) return
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (isEditing) return
        }
    }

    /**
     * Parses and then formats given string to desired number as a currency
     *
     * @param text The text to be parsed
     *
     * @return [Number]
     */
    private fun formatText(text: String): String {
        return try {
            val parsedText = formatter.parse(text)
            formatText(parsedText)
        } catch (e: ParseException) {
            ""
        }
    }

    /**
     * Formats given number to desired text
     *
     * @param number The number to be formatted
     *
     * @return [String]
     */
    private fun formatText(number: Number?) = formatter.format(number)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addTextChangedListener(textWatcher)
    }

    override fun onEditorAction(actionCode: Int) {
        super.onEditorAction(actionCode)
        if (actionCode == EditorInfo.IME_ACTION_DONE) {
            clearFocus()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeTextChangedListener(textWatcher)
    }

    private companion object {
        private inline val formatter: NumberFormat
            get() = DecimalFormat.getInstance(Locale.getDefault())
    }
}

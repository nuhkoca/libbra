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
import io.github.nuhkoca.libbra.util.formatter.CurrencyFormatter
import io.github.nuhkoca.libbra.util.formatter.Formatter

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
            val formattedAmount = formatter.formatText(s.toString())
            setText(formattedAmount)
            setSelection(text.toString().length)
            isEditing = false
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            if (isEditing) return
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (isEditing) return
        }
    }

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
        private val formatter: Formatter = CurrencyFormatter()
    }
}

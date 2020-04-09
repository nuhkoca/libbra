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
package io.github.nuhkoca.libbra.ui.currency

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import io.github.nuhkoca.libbra.BR
import io.github.nuhkoca.libbra.ui.di.MainScope
import io.github.nuhkoca.libbra.util.ext.i
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * A [BaseObservable] implementation that holds multiplier entered by responder view.
 */
@MainScope
class BindableMultiplier @Inject constructor() : BaseObservable() {

    @get:Bindable
    var multiplier: Float by Delegates.observable(1.0f) { _, _, newValue ->
        notifyPropertyChanged(BR.multiplier)

        i { "Current multiplier is $newValue" }
    }

    fun reset() {
        multiplier = 1.0f
    }
}

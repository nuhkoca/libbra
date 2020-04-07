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
package io.github.nuhkoca.libbra

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.github.nuhkoca.libbra.util.ext.i

/**
 * A [LifecycleObserver] demonstration that just notifies application state.
 */
class ApplicationObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
        i { "App is in the background" }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        i { "App is in the foreground" }
    }
}

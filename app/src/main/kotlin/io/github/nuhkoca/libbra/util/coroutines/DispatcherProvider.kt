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
package io.github.nuhkoca.libbra.util.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * A main interface that manages Coroutines contex. This class also provides a crucial benefit for
 * testing.
 */
interface DispatcherProvider {

    /**
     * Dispatcher definition for [Dispatchers.Main]
     */
    val main: CoroutineDispatcher

    /**
     * Dispatcher definition for [Dispatchers.Default]. This thread should be used for heavy background
     * processes.
     */
    val default: CoroutineDispatcher

    /**
     * Dispatcher definition for [Dispatchers.IO]. This thread should be used for IO processes.
     */
    val io: CoroutineDispatcher

    /**
     * Dispatcher definition for [Dispatchers.Unconfined]
     */
    val unconfined: CoroutineDispatcher
}

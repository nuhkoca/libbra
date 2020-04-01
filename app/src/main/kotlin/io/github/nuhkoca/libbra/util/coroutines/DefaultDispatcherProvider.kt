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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Coroutines dispatcher implementation for [Dispatchers]. Processes will be started inside the
 * specified thread by this class.
 */
@Singleton
internal class DefaultDispatcherProvider @Inject constructor() : DispatcherProvider {

    /**
     * Binds [Dispatchers.Main] by default.
     */
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main

    /**
     * Binds [Dispatchers.Default] by default.
     */
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default

    /**
     * Binds [Dispatchers.IO] by default.
     */
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO

    /**
     * Binds [Dispatchers.Unconfined] by default.
     */
    @ExperimentalCoroutinesApi
    override val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}

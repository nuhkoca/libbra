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
@file:JvmName("TimberKt")

package io.github.nuhkoca.libbra.util.ext

import android.annotation.SuppressLint
import timber.log.Timber

/**
 * Extension function that wraps [Timber]'s debug logging.
 *
 * @param message The message block
 */
@SuppressLint("TimberLogDetector")
inline fun d(crossinline message: () -> String) = log { Timber.d(message()) }

/**
 * Extension function that wraps [Timber]'s info logging.
 *
 * @param message The message block
 */
@SuppressLint("TimberLogDetector")
inline fun i(crossinline message: () -> String) = log { Timber.i(message()) }

/**
 * Extension function that wraps [Timber]'s warning logging.
 *
 * @param message The message block
 */
@SuppressLint("TimberLogDetector")
inline fun w(crossinline message: () -> String) = log { Timber.w(message()) }

/**
 * Extension function that wraps [Timber]'s error logging.
 *
 * @param message The message block
 */
@SuppressLint("TimberLogDetector")
inline fun e(crossinline message: () -> String) = log { Timber.e(message()) }

/** @suppress */
@PublishedApi
internal inline fun log(block: () -> Unit) {
    if (Timber.treeCount() > 0) block()
}

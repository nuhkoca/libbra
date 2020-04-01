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
@file:JvmName("FragmentKt")

package io.github.nuhkoca.libbra.util.ext

import androidx.fragment.app.Fragment
import io.github.nuhkoca.libbra.LibbraApplication

/**
 * An inline value that takes [Fragment]'s application as [LibbraApplication]
 */
inline val Fragment.libbraApplication: LibbraApplication
    get() = (requireActivity().application as LibbraApplication)

/**
 * Hides keyboard from the UI.
 */
fun Fragment.hideKeyboard() = requireActivity().hideKeyboard()

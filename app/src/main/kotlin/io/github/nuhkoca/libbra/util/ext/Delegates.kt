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
@file:JvmName("DelegatesKt")

package io.github.nuhkoca.libbra.util.ext

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import io.github.nuhkoca.libbra.util.delegates.ActivityViewBindingDelegate
import io.github.nuhkoca.libbra.util.delegates.FragmentViewBindingDelegate
import io.github.nuhkoca.libbra.util.delegates.ViewHolderBindingDelegate

/**
 * A delegation function that initializes [ViewBinding] for desired [Fragment]
 *
 * @param viewBindingFactory The factory to initialize binding
 */
fun <T : ViewBinding> Fragment.viewBinding(
    viewBindingFactory: (View) -> T
) = FragmentViewBindingDelegate(this, viewBindingFactory)

/**
 * A delegation function that initializes [ViewBinding] for desired [AppCompatActivity]
 *
 * @param bindingInflater The inflater to inflate view
 */
fun <T : ViewBinding> AppCompatActivity.viewBinding(
    bindingInflater: (LayoutInflater) -> T
) = ActivityViewBindingDelegate(this, bindingInflater)

/**
 * A delegation function that initializes [ViewDataBinding] for desired [RecyclerView.ViewHolder]
 */
fun <T : ViewDataBinding> RecyclerView.ViewHolder.viewHolderBinding() =
    ViewHolderBindingDelegate<T>(this)

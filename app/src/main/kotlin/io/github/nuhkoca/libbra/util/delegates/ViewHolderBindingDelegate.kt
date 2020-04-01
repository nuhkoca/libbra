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
package io.github.nuhkoca.libbra.util.delegates

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * A [ReadOnlyProperty] to create a [ViewDataBinding] for requested [RecyclerView.ViewHolder]
 *
 * @param viewHolder The ViewHolder that has [ViewDataBinding] enabled
 */
class ViewHolderBindingDelegate<out T : ViewDataBinding>(
    private val viewHolder: RecyclerView.ViewHolder
) : ReadOnlyProperty<RecyclerView.ViewHolder, T> {
    private var binding: T? = null

    override fun getValue(thisRef: RecyclerView.ViewHolder, property: KProperty<*>): T {
        val binding = binding
        if (binding != null) {
            return binding
        }

        val dataBinding = DataBindingUtil.getBinding<T>(viewHolder.itemView)
            ?: throw IllegalStateException("The view is not a root View for a layout or view hasn't been bound.")

        return dataBinding.also { this.binding = it }
    }
}

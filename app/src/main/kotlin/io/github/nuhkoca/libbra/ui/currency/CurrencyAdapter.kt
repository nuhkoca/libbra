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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import io.github.nuhkoca.libbra.data.model.view.RateViewItem
import io.github.nuhkoca.libbra.databinding.LayoutCurrencyItemBinding
import io.github.nuhkoca.libbra.ui.di.MainScope
import io.github.nuhkoca.libbra.util.event.SingleLiveEvent
import io.github.nuhkoca.libbra.util.recyclerview.BaseViewHolder
import javax.inject.Inject

@MainScope
class CurrencyAdapter @Inject constructor(
    private val itemClickLiveData: SingleLiveEvent<String>
) : ListAdapter<RateViewItem, CurrencyAdapter.CurrencyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutCurrencyItemBinding.inflate(inflater, parent, false)
        return CurrencyViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val rate = currentList[position]
        holder.bindTo(rate)
    }

    override fun onBindViewHolder(
        holder: CurrencyViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val rate = currentList[position]
            holder.bindTo(rate)
        }
    }

    inner class CurrencyViewHolder(itemView: View) :
        BaseViewHolder<LayoutCurrencyItemBinding, RateViewItem>(itemView) {

        override fun bindTo(item: RateViewItem) {
            binding.rate = item

            binding.root.setOnClickListener {
                val list = currentList.toMutableList()
                list.removeAt(layoutPosition).also { rate ->
                    list.add(0, rate)
                    submitList(list) {
                        // Because 0 is always responder
                        if (layoutPosition > 0) itemClickLiveData.value = item.abbreviation
                    }
                }
            }

            super.bindTo(item)
        }
    }

    private companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RateViewItem>() {
            override fun areItemsTheSame(oldItem: RateViewItem, newItem: RateViewItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RateViewItem, newItem: RateViewItem): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: RateViewItem, newItem: RateViewItem): Any? {
                return if (oldItem.id != newItem.id) newItem.id else oldItem.id
            }
        }
    }
}

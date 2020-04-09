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
import androidx.recyclerview.widget.RecyclerView
import io.github.nuhkoca.libbra.data.model.view.RateViewItem
import io.github.nuhkoca.libbra.databinding.LayoutCurrencyItemBinding
import io.github.nuhkoca.libbra.databinding.LayoutCurrencyResponderItemBinding
import io.github.nuhkoca.libbra.ui.di.MainScope
import io.github.nuhkoca.libbra.util.event.SingleLiveEvent
import io.github.nuhkoca.libbra.util.recyclerview.BaseViewHolder
import javax.inject.Inject

@MainScope
class CurrencyAdapter @Inject constructor(
    private val itemClickLiveData: SingleLiveEvent<String>,
    private val bindableMultiplier: BindableMultiplier
) : ListAdapter<RateViewItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_RESPONDER -> {
                val binding = LayoutCurrencyResponderItemBinding.inflate(inflater, parent, false)
                ResponderViewHolder(binding.root)
            }
            ITEM_CURRENCY -> {
                val binding = LayoutCurrencyItemBinding.inflate(inflater, parent, false)
                CurrencyViewHolder(binding.root)
            }
            else -> throw IllegalStateException("No view type found for $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ResponderViewHolder -> {
                val rate = currentList.first()
                holder.bindTo(rate)
            }
            is CurrencyViewHolder -> {
                val rate = currentList[position]
                holder.bindTo(rate)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            when (position) {
                0 -> {
                    holder as ResponderViewHolder
                    val rate = currentList.first()
                    holder.bindTo(rate)
                }
                else -> {
                    holder as CurrencyViewHolder
                    val rate = currentList[position]
                    holder.bindTo(rate)
                }
            }
        }
    }

    /**
     * Moves tapped item to the top position.
     *
     * @param position The layout position
     * @param commitCallback The function to be called
     */
    private fun moveToTop(position: Int, commitCallback: (() -> Unit)) {
        notifyItemMoved(position, 0)
        commitCallback.invoke()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) ITEM_RESPONDER else ITEM_CURRENCY
    }

    inner class ResponderViewHolder(itemView: View) :
        BaseViewHolder<LayoutCurrencyResponderItemBinding, RateViewItem>(itemView) {

        override fun bindTo(item: RateViewItem) {
            binding.rate = item
            binding.bindable = bindableMultiplier

            super.bindTo(item)
        }
    }

    inner class CurrencyViewHolder(itemView: View) :
        BaseViewHolder<LayoutCurrencyItemBinding, RateViewItem>(itemView) {

        override fun bindTo(item: RateViewItem) {
            binding.rate = item
            binding.bindable = bindableMultiplier

            binding.root.setOnClickListener {
                moveToTop(layoutPosition) {
                    bindableMultiplier.reset()
                    itemClickLiveData.value = item.abbreviation
                }
            }

            super.bindTo(item)
        }
    }

    private companion object {
        private const val ITEM_RESPONDER = 0
        private const val ITEM_CURRENCY = 1

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

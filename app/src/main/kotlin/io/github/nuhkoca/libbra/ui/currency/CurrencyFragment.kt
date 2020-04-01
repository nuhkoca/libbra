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

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.navGraphViewModels
import io.github.nuhkoca.libbra.R
import io.github.nuhkoca.libbra.data.enums.Rate
import io.github.nuhkoca.libbra.databinding.FragmentCurrencyBinding
import io.github.nuhkoca.libbra.ui.di.MainScope
import io.github.nuhkoca.libbra.util.event.SingleLiveEvent
import io.github.nuhkoca.libbra.util.ext.addLifecycleAwareTouchListener
import io.github.nuhkoca.libbra.util.ext.snackBar
import io.github.nuhkoca.libbra.util.ext.viewBinding
import javax.inject.Inject

@MainScope
class CurrencyFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val currencyAdapter: CurrencyAdapter,
    private val itemClickLiveData: SingleLiveEvent<String>
) : Fragment(R.layout.fragment_currency) {

    private val binding by viewBinding(FragmentCurrencyBinding::bind)
    private val mergedBinding by viewBinding { binding.errorContainer }
    private val viewModel: CurrencyViewModel by navGraphViewModels(R.id.nav_graph_main) { viewModelFactory }

    private var animate: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            itemClickLiveData.observe(viewLifecycleOwner, onChanged = { currency ->
                viewModel.setBaseCurrency(Rate.valueOf(currency))
                binding.rvCurrency.scrollToPosition(0)
            })
            binding.rvCurrency.setHasFixedSize(true)
            binding.rvCurrency.adapter = currencyAdapter
            binding.rvCurrency.addLifecycleAwareTouchListener(this@CurrencyFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() = with(viewModel) {
        currencyLiveData.observe(viewLifecycleOwner, onChanged = { state ->
            binding.pbCurrency.isVisible = state.isLoading
            mergedBinding.root.isVisible = state.hasError
            if (state.hasError) {
                state.errorMessage?.let(binding.container::snackBar)
                return@observe
            }
            state.data?.let {
                currencyAdapter.submitList(it.rates)
                if (animate) binding.rvCurrency.scheduleLayoutAnimation()
                animate = false
            }
        })
    }
}

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
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import io.github.nuhkoca.libbra.R
import io.github.nuhkoca.libbra.data.enums.Rate
import io.github.nuhkoca.libbra.databinding.FragmentCurrencyBinding
import io.github.nuhkoca.libbra.ui.di.MainScope
import io.github.nuhkoca.libbra.util.event.SingleLiveEvent
import io.github.nuhkoca.libbra.util.ext.addLifecycleAwareScrollListener
import io.github.nuhkoca.libbra.util.ext.addLifecycleAwareTouchListener
import io.github.nuhkoca.libbra.util.ext.hide
import io.github.nuhkoca.libbra.util.ext.show
import io.github.nuhkoca.libbra.util.ext.smoothSnapToPosition
import io.github.nuhkoca.libbra.util.ext.snackBar
import io.github.nuhkoca.libbra.util.ext.viewBinding
import io.github.nuhkoca.libbra.util.keyboard.KeyboardState
import io.github.nuhkoca.libbra.util.keyboard.KeyboardStateLiveData
import io.github.nuhkoca.libbra.util.keyboard.bindKeyboardStateEvents
import javax.inject.Inject

/*
 * Cannot test this class along with FragmentFactory and navGraphViewModels delegation. If I don't
 * use FragmentFactory and init viewModel with viewModels delegations I am able to test. However,
 * I didn't want to change the whole logic.
 *
 * Issue tracker: https://issuetracker.google.com/issues/153364901
 */
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
                binding.rvCurrency.smoothSnapToPosition(0) {
                    viewModel.setBaseCurrency(Rate.valueOf(currency))
                }
            })
            KeyboardStateLiveData.state.observe(viewLifecycleOwner, onChanged = { state ->
                viewModel.setContinuation(state == KeyboardState.CLOSED)
            })
            bindKeyboardStateEvents()
            setupSwipeRefreshLayout()
            setupRecyclerView()
            observeViewModel()
        }
    }

    private fun setupSwipeRefreshLayout() = with(binding.srlCurrency) {
        setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark)
        setOnRefreshListener { animate = true; viewModel.refresh() }
    }

    private fun setupRecyclerView() = with(binding.rvCurrency) {
        setHasFixedSize(true); adapter = currencyAdapter
        addLifecycleAwareTouchListener(this@CurrencyFragment)
        addLifecycleAwareScrollListener(this@CurrencyFragment) { state ->
            viewModel.setContinuation(state == SCROLL_STATE_IDLE)
        }
    }

    private fun observeViewModel() = with(viewModel) {
        currencyLiveData.observe(viewLifecycleOwner, onChanged = { state ->
            binding.pbCurrency.isVisible = state.isLoading
            mergedBinding.root.isVisible = state.hasError
            binding.srlCurrency.isRefreshing = false
            if (state.hasError) {
                binding.rvCurrency.hide(); state.errorMessage?.let(binding.container::snackBar)
                return@observe
            }
            state.data?.let {
                currencyAdapter.submitList(it.rates); binding.rvCurrency.show()
                if (animate) binding.rvCurrency.scheduleLayoutAnimation(); animate = false
            }
        })
    }
}

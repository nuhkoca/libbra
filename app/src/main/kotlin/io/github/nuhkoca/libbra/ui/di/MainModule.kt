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
package io.github.nuhkoca.libbra.ui.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.github.nuhkoca.libbra.di.factory.FragmentKey
import io.github.nuhkoca.libbra.di.factory.ViewModelKey
import io.github.nuhkoca.libbra.ui.currency.CurrencyAdapter
import io.github.nuhkoca.libbra.ui.currency.CurrencyFragment
import io.github.nuhkoca.libbra.ui.currency.CurrencyViewModel
import io.github.nuhkoca.libbra.util.event.SingleLiveEvent
import javax.inject.Scope

@Module
internal abstract class MainModule {

    @Binds
    @IntoMap
    @MainScope
    @FragmentKey(CurrencyFragment::class)
    internal abstract fun bindCurrencyFragment(currencyFragment: CurrencyFragment): Fragment

    @Binds
    @IntoMap
    @MainScope
    @ViewModelKey(CurrencyViewModel::class)
    internal abstract fun bindCurrencyViewModel(viewModel: CurrencyViewModel): ViewModel

    @Module
    internal companion object {

        @Provides
        @MainScope
        internal fun provideCurrencyAdapter(
            itemClickListener: SingleLiveEvent<String>
        ) = CurrencyAdapter(itemClickListener)

        @Provides
        @MainScope
        internal fun provideItemClickListener() = SingleLiveEvent<String>()
    }
}

@Scope
@MustBeDocumented
internal annotation class MainScope

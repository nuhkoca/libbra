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
package io.github.nuhkoca.libbra.binding.di

import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderBuilder
import dagger.Module
import dagger.Provides
import io.github.nuhkoca.libbra.binding.adapters.ImageBindingAdapter
import io.github.nuhkoca.libbra.binding.adapters.TextBindingAdapter
import javax.inject.Qualifier
import javax.inject.Scope

@Module
internal object BindingModule {

    private const val DEFAULT_MEMORY_MULTIPLIER = 0.5

    @Provides
    @BindingScope
    internal fun provideImageLoader(context: Context) = ImageLoaderBuilder(context).apply {
        availableMemoryPercentage(DEFAULT_MEMORY_MULTIPLIER)
        crossfade(true)
    }.build()

    @Provides
    @InternalApi
    @BindingScope
    internal fun provideImageBindingAdapter(
        imageLoader: ImageLoader
    ) = ImageBindingAdapter(imageLoader)

    @Provides
    @InternalApi
    @BindingScope
    internal fun provideTextBindingAdapter() = TextBindingAdapter()
}

@Scope
@MustBeDocumented
internal annotation class BindingScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
private annotation class InternalApi

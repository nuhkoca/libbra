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
package io.github.nuhkoca.libbra.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import io.github.nuhkoca.libbra.binding.di.BindingComponent
import io.github.nuhkoca.libbra.di.factory.FragmentBindingsModule
import io.github.nuhkoca.libbra.di.factory.ViewModelBuilderModule
import io.github.nuhkoca.libbra.ui.di.MainComponent
import javax.inject.Singleton

/**
 * The main component to build Dagger graph.
 */
@Singleton
@Component(
    modules = [
        AppModule::class,
        SubcomponentsModule::class,
        FragmentBindingsModule::class,
        ViewModelBuilderModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun dataBindingComponent(): BindingComponent.Factory

    fun mainComponent(): MainComponent.Factory
}

@Module(
    subcomponents = [
        BindingComponent::class,
        MainComponent::class
    ]
)
object SubcomponentsModule

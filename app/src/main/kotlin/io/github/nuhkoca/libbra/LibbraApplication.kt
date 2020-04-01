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
package io.github.nuhkoca.libbra

import android.app.Application
import androidx.databinding.DataBindingUtil
import io.github.nuhkoca.libbra.BuildConfig.DEBUG
import io.github.nuhkoca.libbra.di.AppComponent
import io.github.nuhkoca.libbra.di.DaggerAppComponent
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * An application that initializes Dagger and lazily provides [AppComponent].
 *
 * Also, sets up Timber in the DEBUG BuildConfig.
 */
class LibbraApplication : Application() {

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent(): AppComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerAppComponent.factory().create(applicationContext).also { appComponent ->
            val bindingComponent = appComponent.dataBindingComponent().create()
            DataBindingUtil.setDefaultComponent(bindingComponent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (DEBUG) Timber.plant(DebugTree())
    }
}

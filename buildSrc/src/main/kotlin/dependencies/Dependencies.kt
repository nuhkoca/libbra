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
package dependencies

object Dependencies {

    object Core {
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val serialization_json =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinx_serialization_json}"
    }

    object Lint {
        const val lint = "com.android.tools.lint:lint:${Versions.lint}"
        const val api = "com.android.tools.lint:lint-api:${Versions.lint}"
        const val checks = "com.android.tools.lint:lint-checks:${Versions.lint}"
        const val tests = "com.android.tools.lint:lint-tests:${Versions.lint}"
    }

    object UI {
        const val material = "com.google.android.material:material:${Versions.material}"
        const val core_ktx = "androidx.core:core-ktx:${Versions.core}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val recylerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
        const val constraint_layout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
        const val fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragment_ktx}"
        const val activity_ktx = "androidx.activity:activity-ktx:${Versions.activity_ktx}"
        const val swipe_refresh_layout =
            "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipe_refresh_layout}"
    }

    object Navigation {
        const val nav_fragment_ktx =
            "androidx.navigation:navigation-fragment-ktx:${Versions.android_navigation}"
        const val nav_ui_ktx =
            "androidx.navigation:navigation-ui-ktx:${Versions.android_navigation}"
    }

    object Lifecycle {
        const val lifecycle_extensions =
            "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
        const val viewmodel_ktx =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val livedata_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
        const val runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
        const val common_java = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
        const val compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    }

    object Dagger {
        const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
        const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    }

    object Network {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val retrofit_serialization_adapter =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofit_serialization_adapter}"
        internal const val okhttp_bom = "com.squareup.okhttp3:okhttp-bom:${Versions.okhttp}"
        internal const val okhttp = "com.squareup.okhttp3:okhttp"
        internal const val okhttp_logging = "com.squareup.okhttp3:logging-interceptor"
    }

    object Other {
        const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
        const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
        const val coil = "io.coil-kt:coil:${Versions.coil}"
    }
}

internal object TestDependencies {
    // Core library
    const val test_core = "androidx.test:core:${Versions.test_core}"
    const val arch_core = "androidx.arch.core:core-testing:${Versions.arch_core}"
    const val coroutines_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val serialization_json =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinx_serialization_json}"

    // Fragment
    const val fragment = "androidx.fragment:fragment-testing:${Versions.fragment}"

    // Orchestrator
    const val orchestrator = "androidx.test:orchestrator:${Versions.orchestrator}"

    // AndroidJUnitRunner and JUnit Rules
    const val runner = "androidx.test:runner:${Versions.runner}"
    const val rules = "androidx.test:rules:${Versions.rules}"

    // Assertions
    const val junit = "androidx.test.ext:junit:${Versions.junit}"
    const val truth_ext = "androidx.test.ext:truth:${Versions.truth_ext}"

    // Espresso dependencies
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"
    const val idling_resource =
        "androidx.test.espresso:espresso-idling-resource:${Versions.espresso_core}"

    // Mock
    const val mockK = "io.mockk:mockk:${Versions.mockK}"
    const val mock_web_server = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"

    // JUnit5
    // (Required) Writing and executing Unit Tests on the JUnit Platform
    const val jupiter_api = "org.junit.jupiter:junit-jupiter-api:${Versions.jupiter}"
    const val jupiter_engine = "org.junit.jupiter:junit-jupiter-engine:${Versions.jupiter}"

    // (Optional) If you need "Parameterized Tests"
    const val jupiter_params = "org.junit.jupiter:junit-jupiter-params:${Versions.jupiter}"

    // (Optional) If you also have JUnit 4-based tests
    const val vintage_engine = "org.junit.vintage:junit-vintage-engine:${Versions.jupiter}"
    const val android_test_runner =
        "de.mannodermaus.junit5:android-test-runner:${Versions.android_test_runner}"
}

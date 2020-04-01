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
@file:Suppress("unused")

package common

import dependencies.Dependencies
import dependencies.TestDependencies
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * A common test dependency handler among all sub projects. This only should be called in case any
 * sub project needs test and androidTest implementations.
 */
fun DependencyHandler.addTestDependencies() {
    testImplementation(TestDependencies.test_core)
    testImplementation(TestDependencies.runner)
    testImplementation(TestDependencies.junit)
    testImplementation(TestDependencies.rules)
    testImplementation(TestDependencies.truth_ext)
    testImplementation(TestDependencies.mockK)
    testImplementation(TestDependencies.arch_core)
    testImplementation(TestDependencies.coroutines_core)
    testImplementation(TestDependencies.mock_web_server)
    testImplementation(TestDependencies.serialization_runtime)

    androidTestImplementation(TestDependencies.test_core)
    androidTestImplementation(TestDependencies.junit)
    androidTestImplementation(TestDependencies.rules)
    androidTestImplementation(TestDependencies.runner)
    androidTestImplementation(TestDependencies.espresso_core)
    androidTestImplementation(TestDependencies.idling_resource)
    androidTestUtil(TestDependencies.orchestrator)

    debugImplementation(TestDependencies.fragment)
}

/**
 * A common JUnit5 test dependency handler among all sub projects. This only should be called in
 * case any sub project needs test and androidTest implementations.
 */
fun DependencyHandler.addJUnit5TestDependencies() {
    // (Required) Writing and executing Unit Tests on the JUnit Platform
    testImplementation(TestDependencies.jupiter_api)
    testRuntimeOnly(TestDependencies.jupiter_engine)

    // (Optional) If you need "Parameterized Tests"
    testImplementation(TestDependencies.jupiter_params)

    // (Optional) If you also have JUnit 4-based tests
    testRuntimeOnly(TestDependencies.vintage_engine)
    androidTestRuntimeOnly(TestDependencies.android_test_runner)
}

/**
 * A Bill of Material implementation for OkHttp library group.
 */
fun DependencyHandler.addOkHttpBom() {
    implementation(platform(Dependencies.Network.okhttp_bom))
    implementation(Dependencies.Network.okhttp)
    implementation(Dependencies.Network.okhttp_logging)
}

/*
 * These extensions mimic the extensions that are generated on the fly by Gradle.
 * They are used here to provide above dependency syntax that mimics Gradle Kotlin DSL
 * syntax in module\build.gradle.kts files.
 */
private fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

private fun DependencyHandler.debugImplementation(dependencyNotation: Any): Dependency? =
    add("debugImplementation", dependencyNotation)

private fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

private fun DependencyHandler.testRuntimeOnly(dependencyNotation: Any): Dependency? =
    add("testRuntimeOnly", dependencyNotation)

private fun DependencyHandler.androidTestImplementation(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)

private fun DependencyHandler.androidTestRuntimeOnly(dependencyNotation: Any): Dependency? =
    add("androidTestRuntimeOnly", dependencyNotation)

private fun DependencyHandler.androidTestUtil(dependencyNotation: Any): Dependency? =
    add("androidTestUtil", dependencyNotation)

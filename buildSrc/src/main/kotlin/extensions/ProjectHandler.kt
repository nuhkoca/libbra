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

package extensions

import AndroidTest
import Debug
import Main
import Release
import ReleaseConfig
import Test
import com.android.build.gradle.api.AndroidSourceSet
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.internal.dsl.BuildType
import com.android.build.gradle.internal.dsl.SigningConfig
import org.gradle.api.DomainObjectSet
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import utils.execute
import utils.getProperty
import utils.shouldTreatCompilerWarningsAsErrors

/**
 * An extension to create release build type.
 *
 * @param namedDomainObjectContainer The container to create the corresponding build type
 *
 * @return The release [BuildType]
 */
fun Project.createRelease(namedDomainObjectContainer: NamedDomainObjectContainer<BuildType>) =
    Release.create(namedDomainObjectContainer, this)

/**
 * An extension to create debug build type.
 *
 * @param namedDomainObjectContainer The container to create the corresponding build type
 *
 * @return The debug [BuildType]
 */
fun Project.createDebug(namedDomainObjectContainer: NamedDomainObjectContainer<BuildType>) =
    Debug.create(namedDomainObjectContainer, this)

/**
 * Creates custom naming for apk outputs.
 *
 * @param domainObjectSet The variant set to generate custom apk naming for each variant
 */
fun Project.generateApplicationOutputs(
    domainObjectSet: DomainObjectSet<ApplicationVariant>
) = domainObjectSet.generateApplicationOutputs()

/**
 * An extension to create main Kotlin source set.
 *
 * @param namedDomainObjectContainer The container to create the corresponding source set
 *
 * @return The main Kotlin [AndroidSourceSet]
 */
fun Project.createKotlinMain(
    namedDomainObjectContainer: NamedDomainObjectContainer<AndroidSourceSet>
) = Main.create(namedDomainObjectContainer, this)

/**
 * An extension to create test Kotlin source set.
 *
 * @param namedDomainObjectContainer The container to create the corresponding source set
 *
 * @return The test Kotlin [AndroidSourceSet]
 */
fun Project.createKotlinTest(
    namedDomainObjectContainer: NamedDomainObjectContainer<AndroidSourceSet>
) = Test.create(namedDomainObjectContainer, this)

/**
 * An extension to create android test Kotlin source set.
 *
 * @param namedDomainObjectContainer The container to create the corresponding source set
 *
 * @return The android test Kotlin [AndroidSourceSet]
 */
fun Project.createKotlinAndroidTest(
    namedDomainObjectContainer: NamedDomainObjectContainer<AndroidSourceSet>
) = AndroidTest.create(namedDomainObjectContainer, this)

/**
 * An extension to create release signing config.
 *
 * @param namedDomainObjectContainer The container to create the corresponding signing config
 *
 * @return The release [SigningConfig]
 */
fun Project.createReleaseConfig(namedDomainObjectContainer: NamedDomainObjectContainer<SigningConfig>) =
    ReleaseConfig.create(namedDomainObjectContainer, this)

/**
 * Applies semantic versioning and returns the combined version name accordingly
 *
 * @return The version name
 */
fun Project.getSemanticAppVersionName() = utils.getSemanticAppVersionName()

/**
 * Basically fetches the recent git commit hash
 */
internal inline val Project.gitSha: String
    get() = "git rev-parse --short HEAD".execute(rootDir, "none")

/**
 * Specify whether or not treat compiler warnings as errors
 */
internal fun Project.shouldTreatCompilerWarningsAsErrors() =
    shouldTreatCompilerWarningsAsErrors(this)

/**
 * If the instrumented tests live in the Android Library projects, running
 * ./gradlew connectedDebugAndroidTest will run the androidTest related tasks for all the ones
 * without any android tests at all. This is for performance purpose to reduce build time.
 */
internal inline val Project.hasAndroidTestSource: Boolean
    get() {
        extensions
            .findByType(KotlinAndroidProjectExtension::class.java)
            ?.sourceSets
            ?.findByName("androidTest")
            ?.let {
                if (it.kotlin.files.isNotEmpty()) return true
            }
        return false
    }

/**
 * Returns the requested property
 *
 * @param name The property name
 *
 * @return The property as [String]
 */
fun Project.getProperty(name: String) = getProperty(name, this)

/**
 * If the instrumented tests live in the Android Library projects, running
 * ./gradlew connectedDebugAndroidTest will run the androidTest related tasks for all the ones
 * without any android tests at all. This is for performance purpose to reduce build time.
 */
fun Project.configureAndroidTests() {
    if (!hasAndroidTestSource) {
        project.tasks.configureEach {
            if (name.contains("androidTest", ignoreCase = true)) {
                enabled = false
            }
        }
    }
}

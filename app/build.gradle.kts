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
import common.addJUnit5TestDependencies
import common.addOkHttpBom
import common.addTestDependencies
import dependencies.Dependencies
import extensions.applyDefault
import extensions.configureAndroidTests
import extensions.createDebug
import extensions.createKotlinAndroidTest
import extensions.createKotlinMain
import extensions.createKotlinTest
import extensions.createRelease
import extensions.createReleaseConfig
import extensions.getSemanticAppVersionName
import extensions.setDefaults
import utils.javaVersion

plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinAndroidExtension)
    kotlin(Plugins.kotlinKapt)
    id(Plugins.kotlinSerialization)
    id(Plugins.junit5)
}

val baseUrl: String by project

android {
    compileSdkVersion(extra["compileSdkVersion"] as Int)

    defaultConfig {
        applicationId = "io.github.nuhkoca.libbra"
        minSdkVersion(extra["minSdkVersion"] as Int)
        targetSdkVersion(extra["targetSdkVersion"] as Int)
        versionCode = 1
        versionName = getSemanticAppVersionName()

        vectorDrawables.useSupportLibrary = true
        testApplicationId = "io.github.nuhkoca.libbra.test"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArgument(Config.JUNIT5_KEY, Config.JUNIT5_VALUE)
        testInstrumentationRunnerArgument(Config.ORCHESTRATOR_KEY, Config.ORCHESTRATOR_VALUE)

        configureAndroidTests()

        signingConfig = signingConfigs.getByName("debug")

        // All supported languages should be added here. It tells all libraries that we only want to
        // compile these languages into our project -> Reduces .APK size
        resConfigs("en")
    }

    // 4) JUnit 5 will bundle in files with identical paths; exclude them
    packagingOptions {
        exclude("META-INF/LICENSE*")
    }

    signingConfigs {
        createReleaseConfig(this)
    }

    buildTypes {
        createRelease(this)
        createDebug(this)

        forEach { type ->
            if (type.name == "release") {
                type.signingConfig = signingConfigs.getByName(type.name)
            }

            type.buildConfigField("String", "BASE_URL", baseUrl)
        }
    }

    sourceSets {
        createKotlinMain(this)
        createKotlinTest(this)
        createKotlinAndroidTest(this)
    }

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    kotlinOptions {
        jvmTarget = javaVersion.toString()
    }

    androidExtensions {
        isExperimental = true
    }

    dataBinding {
        isEnabled = true
    }

    viewBinding {
        isEnabled = true
    }

    lintOptions.setDefaults()

    testOptions.applyDefault()
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    lintChecks(project(Modules.lintRules))

    implementation(Dependencies.Core.kotlin)
    implementation(Dependencies.Core.coroutines)

    implementation(Dependencies.UI.material)
    implementation(Dependencies.UI.core_ktx)
    implementation(Dependencies.UI.appcompat)
    implementation(Dependencies.UI.fragment_ktx)
    implementation(Dependencies.UI.activity_ktx)
    implementation(Dependencies.UI.recylerview)
    implementation(Dependencies.UI.constraint_layout)

    implementation(Dependencies.Navigation.nav_fragment_ktx)
    implementation(Dependencies.Navigation.nav_ui_ktx)

    implementation(Dependencies.Lifecycle.lifecycle_extensions)
    implementation(Dependencies.Lifecycle.viewmodel_ktx)
    implementation(Dependencies.Lifecycle.livedata_ktx)
    implementation(Dependencies.Lifecycle.runtime_ktx)
    implementation(Dependencies.Lifecycle.common_java)
    kapt(Dependencies.Lifecycle.compiler)

    implementation(Dependencies.Dagger.dagger)
    kapt(Dependencies.Dagger.compiler)

    implementation(Dependencies.Network.retrofit)
    implementation(Dependencies.Network.retrofit_serialization_adapter)

    addOkHttpBom()

    implementation(Dependencies.Other.lottie)
    implementation(Dependencies.Other.timber)
    implementation(Dependencies.Other.coil)

    addTestDependencies()
    addJUnit5TestDependencies()
}

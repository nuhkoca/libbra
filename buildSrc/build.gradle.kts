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
plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
    `java-gradle-plugin`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

object PluginVersions {
    const val gradle_plugin = "8.13.0"
    const val kotlin_gradle_plugin = "1.9.25"
    const val gradle_version_plugin = "0.52.0"
    const val detekt = "1.23.8"
    const val ktlint = "9.4.1"
    const val spotless = "5.17.1"
    const val junit5 = "1.13.4.0"
}

dependencies {
    implementation("com.android.tools.build:gradle:${PluginVersions.gradle_plugin}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginVersions.kotlin_gradle_plugin}")
    implementation("org.jetbrains.kotlin:kotlin-serialization:${PluginVersions.kotlin_gradle_plugin}")
    implementation("com.github.ben-manes:gradle-versions-plugin:${PluginVersions.gradle_version_plugin}")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${PluginVersions.detekt}")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:${PluginVersions.ktlint}")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:${PluginVersions.spotless}")
    implementation("de.mannodermaus.gradle.plugins:android-junit5:${PluginVersions.junit5}")
}

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
import extensions.applyDefaults
import plugins.BuildPlugins
import tasks.BuildTasks

buildscript {
    repositories {
        google()
        mavenCentral()

        // Make this a caching provider
        jcenter()
    }
}

plugins.apply(BuildPlugins.DETEKT)
plugins.apply(BuildPlugins.UPDATE_DEPENDENCIES)
plugins.apply(BuildPlugins.KTLINT)
plugins.apply(BuildPlugins.GIT_HOOKS)

allprojects {
    repositories.applyDefaults()

    plugins.apply(BuildPlugins.DETEKT)
    plugins.apply(BuildPlugins.KTLINT)
    plugins.apply(BuildPlugins.SPOTLESS)
}

subprojects {
    plugins.apply(BuildTasks.COMMON_TASKS)

    apply {
        from("$rootDir/versions.gradle.kts")
    }
}

tasks.registering(Delete::class) {
    delete(rootProject.buildDir)
}

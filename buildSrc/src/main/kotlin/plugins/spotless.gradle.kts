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
package plugins

import Config
import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin

apply<SpotlessPlugin>()

@Suppress("INACCESSIBLE_TYPE")
configure<SpotlessExtension> {
    format("misc") {
        target(
            fileTree(
                mapOf(
                    "dir" to ".",
                    "include" to listOf("**/*.md", "**/.gitignore", "**/*.yaml", "**/*.yml"),
                    "exclude" to listOf(
                        ".gradle/**",
                        ".gradle-cache/**",
                        "**/tools/**",
                        "**/build/**"
                    )
                )
            )
        )
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }

    format("xml") {
        target("**/res/**/*.xml")
        targetExclude("**/build/**")
        indentWithSpaces(Config.SPOTLESS_INDENT_WITH_SPACES)
        trimTrailingWhitespace()
        endWithNewline()
    }

    kotlin {
        target(
            fileTree(
                mapOf(
                    "dir" to ".",
                    "include" to listOf("**/*.kt"),
                    "exclude" to listOf("**/build/**", "**/spotless/*.kt")
                )
            )
        )
        licenseHeaderFile(
            rootProject.file("spotless/copyright.kt"),
            "^(package|object|import|interface|internal|@file|//startfile)"
        )
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }

    java {
        target(
            fileTree(
                mapOf(
                    "dir" to ".",
                    "include" to listOf("**/*.java"),
                    "exclude" to listOf("**/build/**", "**/spotless/*.java")
                )
            )
        )
        licenseHeaderFile(
            rootProject.file("spotless/copyright.java"),
            "^(package|object|import|interface|@file|//startfile)"
        )
        removeUnusedImports()
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }

    kotlinGradle {
        target(
            fileTree(
                mapOf(
                    "dir" to ".",
                    "include" to listOf("**/*.gradle.kts", "*.gradle.kts"),
                    "exclude" to listOf("**/build/**", "**/spotless/*.java", "**/spotless/*.kt")
                )
            )
        )
        licenseHeaderFile(
            rootProject.file("spotless/copyright.kt"),
            "package|import|tasks|apply|plugins|include|val|object|interface|pluginManagement|@file|//startfile"
        )
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
}

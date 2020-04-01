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
package io.github.nuhkoca.rules

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import io.github.nuhkoca.rules.Stubs.CUSTOM_LOG_IMPL_KOTLIN
import io.github.nuhkoca.rules.Stubs.TIMBER_LOG_IMPL_JAVA
import org.junit.Test

/**
 * A test class for [TimberLogDetector]
 */
@Suppress("UnstableApiUsage")
class TimberLogDetectorTest : LintDetectorTest() {

    @Test
    fun `test should detect usage of Timber`() {
        val stubFile = kotlin(
            """
            package io.github.nuhkoca.libbra

            import timber.log.Timber

            class Dog {

                fun bark() {
                    Timber.d("woof! woof!")
                }
            }
        """
        ).indented()

        val lintResult = lint()
            .files(TIMBER_LOG_IMPL_JAVA, stubFile)
            .run()

        lintResult
            .expectWarningCount(1)
            .expect(
                """
             src/io/github/nuhkoca/libbra/Dog.kt:8: Warning: Directly calling timber.log.Timber usage is not recommended. [TimberLogDetector]
                     Timber.d("woof! woof!")
                     ~~~~~~~~~~~~~~~~~~~~~~~
             0 errors, 1 warnings
         """.trimIndent()
            )
    }

    @Test
    fun `test should not detect if log import is different`() {
        val fileToEvaluate = kotlin(
            """
             package io.github.nuhkoca.libbra

             import io.github.nuhkoca.libbra.util.ext.d

             class Dog {
                 fun bark() {
                     d { "woof! woof!" }
                 }
             }
         """
        ).indented()

        val lintResult = lint()
            .files(CUSTOM_LOG_IMPL_KOTLIN, fileToEvaluate)
            .run()

        lintResult
            .expectClean()
    }

    override fun getDetector(): Detector = TimberLogDetector()

    override fun getIssues(): MutableList<Issue> = mutableListOf(TimberLogDetector.ISSUE)
}

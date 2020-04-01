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

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.java
import com.android.tools.lint.checks.infrastructure.TestFile
import com.android.tools.lint.checks.infrastructure.TestFiles.kotlin

/**
 * A helper class that contains necessary stubs to test lints.
 */
object Stubs {

    /**
     * [TestFile] containing Timber.
     *
     * This is a hacky workaround for the Timber not being included on the Lint test harness
     * classpath. Ideally, we'd specify ANDROID_HOME as an environment variable.
     */
    val TIMBER_LOG_IMPL_JAVA: TestFile = java(
        """
                package timber.log;

                public final class Timber {
                    public static void d(@NonNls String message, Object... args) {
                        // Stub!
                    }
                }
            """
    ).indented()

    val CUSTOM_LOG_IMPL_KOTLIN: TestFile = kotlin(
        """
                package io.github.nuhkoca.libbra.util.ext

                inline fun d(crossinline message: () -> String) = log { Timber.d(message()) }
        """.trimIndent()
    )
}

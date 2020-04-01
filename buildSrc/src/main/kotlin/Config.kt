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
object Config {
    internal const val JSON_OUTPUT_FORMATTER = "json"
    internal const val BUILD_STABLE_REGEX = "^[0-9,.v-]+(-r)?$"
    internal const val KTLINT_COLOR_NAME = "RED"
    internal const val SPOTLESS_INDENT_WITH_SPACES = 4

    // testInstrumentationRunnerArguments
    const val JUNIT5_KEY = "runnerBuilder"
    const val JUNIT5_VALUE = "de.mannodermaus.junit5.AndroidJUnit5Builder"

    const val ORCHESTRATOR_KEY = "clearPackageData"
    const val ORCHESTRATOR_VALUE = "true"
}

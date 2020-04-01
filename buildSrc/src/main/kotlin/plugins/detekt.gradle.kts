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

import Versions
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.detekt
import utils.javaVersion

apply<DetektPlugin>()

detekt {
    toolVersion = Versions.detekt
    parallel = false
    input = files(
        "src/main/kotlin",
        "src/main/java"
    )
    config = files("${project.rootDir}/default-detekt-config.yml")

    reports {
        xml {
            enabled = true
            destination = file("${project.buildDir}/reports/detekt/detekt-report.xml")
        }
        html {
            enabled = true
            destination = file("${project.buildDir}/reports/detekt/detekt-report.html")
        }
    }
}

tasks {
    withType<Detekt> {
        include("**/*.kt", "**/*.kts")
        exclude("**/build/**", ".*/resources/.*", ".*test.*,.*/resources/.*,.*/tmp/.*")

        jvmTarget = javaVersion.toString()
    }
}

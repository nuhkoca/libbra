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
package extensions

import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import org.gradle.api.DomainObjectSet

private const val APP_NAME_PREFIX = "libbra"
private const val OUTPUT_EXTENSION = ".apk"

/**
 * Creates custom naming for apk outputs.
 */
fun DomainObjectSet<ApplicationVariant>.generateApplicationOutputs() {
    all {
        outputs.all {
            val outputImpl = this as BaseVariantOutputImpl

            val fileName = "$APP_NAME_PREFIX-${buildType.name}$OUTPUT_EXTENSION"

            outputImpl.outputFileName = fileName
        }
    }
}

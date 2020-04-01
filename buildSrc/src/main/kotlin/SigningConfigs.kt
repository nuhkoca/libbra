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
import com.android.build.gradle.internal.dsl.SigningConfig
import extensions.getProperty
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import java.io.File

/**
 * An object that includes signing configs
 */
private object InternalConfigType {
    const val RELEASE = "release"
}

/**
 * The common interface to create any signing config
 */
@FunctionalInterface
private interface SigningConfigCreator {

    /**
     * The val which includes name of the signing config from [InternalConfigType]
     */
    val name: String

    /**
     * Creates the requested signing config
     *
     * @param namedDomainObjectContainer The container to create the corresponding signing config
     * @param project The project
     *
     * @return The [SigningConfig]
     */
    fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<SigningConfig>,
        project: Project
    ): SigningConfig
}

/**
 * A [SigningConfigCreator] implementation to create release [SigningConfig]
 */
internal object ReleaseConfig : SigningConfigCreator {
    override val name = InternalConfigType.RELEASE

    override fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<SigningConfig>,
        project: Project
    ): SigningConfig {
        return namedDomainObjectContainer.create(name).apply {
            storeFile = File("${project.rootDir}/${project.getProperty("signing.store.file")}")
            storePassword = project.getProperty("signing.store.password")
            keyAlias = project.getProperty("signing.key.alias")
            keyPassword = project.getProperty("signing.key.password")
        }
    }
}

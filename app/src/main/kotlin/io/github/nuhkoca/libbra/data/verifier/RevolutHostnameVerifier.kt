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
package io.github.nuhkoca.libbra.data.verifier

import io.github.nuhkoca.libbra.BuildConfig.BASE_URL
import io.github.nuhkoca.libbra.util.ext.d
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * A [HostnameVerifier] implementation for internal network operation. Other network traffics will
 * be ignored.
 */
object RevolutHostnameVerifier : HostnameVerifier {

    private const val HOSTNAME_DELIMITER = "/"

    /**
     * Verifies if network traffic is only over [BASE_URL].
     *
     * @param hostname the host name
     * @param session SSLSession used on the connection to host
     *
     * @return true if the host name is acceptable
     */
    override fun verify(hostname: String?, session: SSLSession?): Boolean {
        val expectedHostname = BASE_URL
            .substringBeforeLast(HOSTNAME_DELIMITER)
            .substringAfterLast(HOSTNAME_DELIMITER)

        d { "Hostname is $expectedHostname}" }

        return hostname.toString().contains(expectedHostname)
    }
}

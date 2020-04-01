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
package io.github.nuhkoca.libbra.shared.dispatcher

import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okhttp3.mockwebserver.SocketPolicy.NO_RESPONSE
import java.util.concurrent.TimeUnit

/**
 * A custom [MockWebServer] [Dispatcher] implementation for timeout case. This will delay response
 * remaining faithful to [OkHttpClient] settings for the test environment.
 */
object TimeoutDispatcher : Dispatcher() {

    private const val BYTES_PER_PERIOD = 1024L
    private const val PERIOD = 2L

    /**
     * @return [MockResponse] with throttled body and [NO_RESPONSE] policy.
     */
    override fun dispatch(request: RecordedRequest): MockResponse {
        return MockResponse()
            .setSocketPolicy(NO_RESPONSE)
            .throttleBody(BYTES_PER_PERIOD, PERIOD, TimeUnit.SECONDS)
    }
}

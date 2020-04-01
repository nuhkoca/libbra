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

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import javax.net.ssl.HttpsURLConnection.HTTP_NOT_FOUND

/**
 * A custom [MockWebServer] [Dispatcher] implementation for error case. This will only return
 * [HTTP_NOT_FOUND] and will cause a crash. Therefore calls should be wrapper with
 * try-catch or so on.
 */
object ErrorDispatcher : Dispatcher() {

    /**
     * @return [MockResponse] with [HTTP_NOT_FOUND] code.
     */
    override fun dispatch(request: RecordedRequest): MockResponse {
        return MockResponse().setResponseCode(HTTP_NOT_FOUND)
    }
}

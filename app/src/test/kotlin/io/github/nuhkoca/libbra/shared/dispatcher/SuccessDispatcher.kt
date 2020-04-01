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

import io.github.nuhkoca.libbra.shared.ENDPOINT_PREFIX
import io.github.nuhkoca.libbra.shared.RESPONSE_DIR_PREFIX
import io.github.nuhkoca.libbra.shared.ext.asset
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import javax.net.ssl.HttpsURLConnection.HTTP_NOT_FOUND
import javax.net.ssl.HttpsURLConnection.HTTP_OK

/**
 * A custom [MockWebServer] [Dispatcher] implementation for success case. This will return
 * [HTTP_OK] if path contains the endpoint and file name is valid.
 */
class SuccessDispatcher(private val fileName: String) : Dispatcher() {

    /**
     * @return [MockResponse] with [HTTP_OK] if path contains the endpoint and file name is valid
     * otherwise [HTTP_NOT_FOUND]
     */
    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.path

        return if (path.toString().contains(ENDPOINT_PREFIX)) {
            val response = asset("$RESPONSE_DIR_PREFIX/$fileName")

            if (response.isNullOrEmpty()) MockResponse().setResponseCode(HTTP_NOT_FOUND)

            // response is not null in any case
            MockResponse().setResponseCode(HTTP_OK).setBody(response!!)
        } else {
            MockResponse().setResponseCode(HTTP_NOT_FOUND)
        }
    }
}

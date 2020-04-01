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
import androidx.annotation.CallSuper
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before

/**
 * A base class for all test classes. If child class wants to override any of stated
 * methods they must call super first.
 */
open class BaseTestClass {

    /**
     * An open method that initializes mock-ups before starting a test.
     */
    @CallSuper
    @Before
    open fun setUp() {
        MockKAnnotations.init(this)
    }

    /**
     * An open method that unmocks & clears all mock-ups in order to avoid any leak.
     */
    @CallSuper
    @After
    open fun tearDown() {
        unmockkAll()
        clearAllMocks()
    }
}

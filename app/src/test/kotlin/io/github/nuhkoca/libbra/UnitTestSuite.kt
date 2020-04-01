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
package io.github.nuhkoca.libbra

import io.github.nuhkoca.libbra.data.datasource.CurrencyRemoteDataSourceTest
import io.github.nuhkoca.libbra.data.mapper.CurrencyDomainMapperTest
import io.github.nuhkoca.libbra.data.serializers.RateSerializerTest
import io.github.nuhkoca.libbra.data.service.CurrencyServiceTest
import io.github.nuhkoca.libbra.data.verifier.RevolutHostnameVerifierTest
import io.github.nuhkoca.libbra.domain.mapper.CurrencyViewItemMapperTest
import io.github.nuhkoca.libbra.domain.repository.CurrencyRepositoryTest
import io.github.nuhkoca.libbra.domain.usecase.CurrencyUseCaseTest
import io.github.nuhkoca.libbra.ui.currency.CurrencyViewModelTest
import io.github.nuhkoca.libbra.util.coroutines.DefaultAsyncManagerTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * A unit test suite to execute all the test classes under this module.
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(
    CurrencyRemoteDataSourceTest::class,
    CurrencyDomainMapperTest::class,
    RateSerializerTest::class,
    CurrencyServiceTest::class,
    RevolutHostnameVerifierTest::class,
    CurrencyViewItemMapperTest::class,
    CurrencyRepositoryTest::class,
    CurrencyUseCaseTest::class,
    CurrencyViewModelTest::class,
    DefaultAsyncManagerTest::class
)
object UnitTestSuite

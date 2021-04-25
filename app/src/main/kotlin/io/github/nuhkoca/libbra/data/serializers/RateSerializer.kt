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
package io.github.nuhkoca.libbra.data.serializers

import io.github.nuhkoca.libbra.data.enums.Rate
import io.github.nuhkoca.libbra.util.ext.w
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A custom [KSerializer] implementation to serialize and deserialize [Rate] elements.
 */
@Serializer(forClass = Rate::class)
@OptIn(ExperimentalSerializationApi::class)
object RateSerializer : KSerializer<Rate> {

    private const val SERIAL_NAME = "rates"

    private val serializer = String.serializer()

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        SERIAL_NAME,
        PrimitiveKind.STRING
    )

    /**
     * Decodes a [Rate] from the given string or returns [Rate.UNKNOWN] if there is
     * no matching.
     *
     * @return [Rate] or [Rate.UNKNOWN] if there is no matching
     *
     * @throws IllegalStateException if there is no match found
     * @throws SerializationException if serializer is unable to serialize
     */
    @Throws(IllegalStateException::class, SerializationException::class)
    @Suppress("TooGenericExceptionCaught")
    override fun deserialize(decoder: Decoder): Rate {
        var type: String? = null
        return try {
            type = decoder.decodeString()
            Rate.valueOf(type)
        } catch (e: RuntimeException) {
            w { "Falling back to UNKNOWN type as there is no match found for $type." }
            Rate.UNKNOWN
        }
    }

    override fun serialize(encoder: Encoder, value: Rate) {
        encoder.encodeSerializableValue(serializer, value.name)
    }
}

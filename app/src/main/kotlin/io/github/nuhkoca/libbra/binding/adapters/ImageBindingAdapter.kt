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
package io.github.nuhkoca.libbra.binding.adapters

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.load
import coil.target.Target
import io.github.nuhkoca.libbra.binding.di.BindingScope
import io.github.nuhkoca.libbra.util.ext.e
import io.github.nuhkoca.libbra.util.ext.i
import javax.inject.Inject

/**
 * A [BindingAdapter] for ImageView processes.
 *
 * @property imageLoader The Coil loader
 */
@BindingScope
class ImageBindingAdapter @Inject constructor(private val imageLoader: ImageLoader) {

    /**
     * Binds image to target ImageView
     *
     * @param resId The res id
     */
    @BindingAdapter("android:src")
    fun ImageView.bindImage(@DrawableRes resId: Int) {
        load(resId, imageLoader) {
            size(IMAGE_WIDTH_SIZE_DEFAULT, IMAGE_HEIGHT_SIZE_DEFAULT)
            target(CustomTarget(this@bindImage))
            build()
        }
    }

    private companion object {
        private const val IMAGE_WIDTH_SIZE_DEFAULT = 200
        private const val IMAGE_HEIGHT_SIZE_DEFAULT = 200
    }
}

/**
 * A custom [Target] that only logs events.
 *
 * @param view The ImageView to load the requested drawable
 */
private open class CustomTarget(private val view: ImageView) : Target {
    override fun onStart(placeholder: Drawable?) {
        super.onStart(placeholder)
        i { "Image loading has started." }
    }

    override fun onSuccess(result: Drawable) {
        super.onSuccess(result)
        view.background = result
        i { "Image loading is successful." }
    }

    override fun onError(error: Drawable?) {
        super.onError(error)
        e { "Image loading is failed." }
    }
}

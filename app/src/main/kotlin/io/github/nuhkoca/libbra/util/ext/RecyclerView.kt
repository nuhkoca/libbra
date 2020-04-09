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
@file:JvmName("RecyclerViewKt")

package io.github.nuhkoca.libbra.util.ext

import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import io.github.nuhkoca.libbra.util.delegates.LifecycleAwareScrollListener
import io.github.nuhkoca.libbra.util.delegates.LifecycleAwareTouchListener

private const val MILLISECONDS_PER_INCH = 0.5f

/**
 * Adds lifecycle aware touch listener to target [Fragment].
 *
 * @param fragment The target fragment
 */
fun RecyclerView.addLifecycleAwareTouchListener(fragment: Fragment) {
    LifecycleAwareTouchListener(fragment, this)
}

/**
 * Adds lifecycle aware scroll listener to target [Fragment].
 *
 * @param fragment The target fragment
 * @param onStateChanged The callback that holds RecyclerView scroll state
 */
fun RecyclerView.addLifecycleAwareScrollListener(
    fragment: Fragment,
    onStateChanged: (newState: Int) -> Unit
) = LifecycleAwareScrollListener(fragment, this, onStateChanged)

/**
 * Adds a very smooth scrolling to [RecyclerView]
 *
 * @param position The target position to scroll
 * @param snapMode The scroll mode
 * @param commitCallback The callback
 */
fun RecyclerView.smoothSnapToPosition(
    position: Int,
    snapMode: Int = LinearSmoothScroller.SNAP_TO_START,
    commitCallback: () -> Unit = {}
) {
    val smoothScroller = object : LinearSmoothScroller(context) {
        override fun getVerticalSnapPreference(): Int = snapMode
        override fun getHorizontalSnapPreference(): Int = snapMode
        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
            return MILLISECONDS_PER_INCH / displayMetrics.density
        }
    }
    smoothScroller.targetPosition = position
    layoutManager?.startSmoothScroll(smoothScroller)

    if (isAnimating.not()) {
        commitCallback.invoke()
    }
}

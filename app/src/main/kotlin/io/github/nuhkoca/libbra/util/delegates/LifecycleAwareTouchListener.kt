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
package io.github.nuhkoca.libbra.util.delegates

import android.view.MotionEvent
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import io.github.nuhkoca.libbra.util.keyboard.dismissKeyboard
import io.github.nuhkoca.libbra.util.keyboard.isKeyboardOpen
import io.github.nuhkoca.libbra.util.recyclerview.DefaultItemTouchListener

/**
 * A lifecycle aware touch listener for [RecyclerView]. Touch listener is bound to target fragment's
 * lifecycle so that it will be handled properly.
 *
 * @param fragment The Fragment to bound this listener to its lifecycle
 * @param recyclerView The RecyclerView to add touch listener
 */
class LifecycleAwareTouchListener(
    private val fragment: Fragment,
    private val recyclerView: RecyclerView
) {

    private val defaultItemTouchListener = object : DefaultItemTouchListener() {
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            if (rv.isKeyboardOpen()) {
                fragment.dismissKeyboard()
            }
            return super.onInterceptTouchEvent(rv, e)
        }
    }

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                recyclerView.addOnItemTouchListener(defaultItemTouchListener)
                fragment.viewLifecycleOwnerLiveData.observe(
                    fragment, { viewLifecycleOwner ->
                        viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                            override fun onDestroy(owner: LifecycleOwner) {
                                recyclerView.removeOnItemTouchListener(defaultItemTouchListener)
                            }
                        })
                    }
                )
            }
        })
    }
}

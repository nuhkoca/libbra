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
package io.github.nuhkoca.libbra.util.keyboard

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.nuhkoca.libbra.util.ext.i
import kotlin.math.ceil

// Credit: https://github.com/GuilhE/KeyboardStateEvents
fun ComponentActivity.bindKeyboardStateEvents() {
    lifecycle.addObserver(ViewGroupHolder(findViewById(Window.ID_ANDROID_CONTENT)))
}

fun Fragment.bindKeyboardStateEvents() = requireActivity().bindKeyboardStateEvents()

fun ComponentActivity.dismissKeyboard() {
    val imm: InputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT).windowToken, 0)
}

fun Fragment.dismissKeyboard() = requireActivity().dismissKeyboard()

fun ViewGroup.isKeyboardOpen(visibleThreshold: Float = 100f): Boolean {
    val measureRect = Rect()
    getWindowVisibleDisplayFrame(measureRect)
    return rootView.height - measureRect.bottom >
        ceil((visibleThreshold * Resources.getSystem().displayMetrics.density))
}

enum class KeyboardState { OPEN, CLOSED }

object KeyboardStateLiveData {
    private val _state = MutableLiveData<KeyboardState>()
    val state: LiveData<KeyboardState> = _state

    fun post(state: KeyboardState) {
        _state.postValue(state)
        i { "Keyboard state is $state" }
    }
}

private class ViewGroupHolder(private val root: ViewGroup) : LifecycleEventObserver {

    private val listener = object : ViewTreeObserver.OnGlobalLayoutListener {
        private var previous: Boolean = root.isKeyboardOpen()

        override fun onGlobalLayout() {
            root.isKeyboardOpen().let {
                if (it != previous) {
                    KeyboardStateLiveData.post(
                        if (it) KeyboardState.OPEN else KeyboardState.CLOSED
                    )
                    previous = previous.not()
                }
            }
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_PAUSE) {
            unregisterKeyboardListener()
        } else if (event == Lifecycle.Event.ON_RESUME) {
            registerKeyboardListener()
        }
    }

    private fun registerKeyboardListener() {
        root.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }

    private fun unregisterKeyboardListener() {
        root.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }
}

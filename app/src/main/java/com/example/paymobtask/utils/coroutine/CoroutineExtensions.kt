package com.example.paymobtask.utils.coroutine

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

inline fun <T> AppCompatActivity.collect(flow: Flow<T>, crossinline block: (T) -> Unit) =
    lifecycleScope.launch {
        flow
            .flowWithLifecycle(this@collect.lifecycle, minActiveState = Lifecycle.State.RESUMED)
            .collect { action ->
                block.invoke(action)
            }
    }

inline fun <T> Fragment.collect(flow: Flow<T>, crossinline block: (T) -> Unit) =
    lifecycleScope.launch {
        flow
            .flowWithLifecycle(this@collect.lifecycle, minActiveState = Lifecycle.State.RESUMED)
            .collect { action ->
                block.invoke(action)
            }
    }

inline fun LifecycleCoroutineScope.executeAfterWhile(
    time: Long = 1000L,
    crossinline block: () -> Unit,
) = launch {
    delay(time)
    block.invoke()
}

fun repeat(str: String?, i: Int): String {
    return String(CharArray(i)).replace("\u0000", str!!)
}
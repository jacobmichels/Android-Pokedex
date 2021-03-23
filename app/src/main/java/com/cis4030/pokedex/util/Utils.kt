package com.cis4030.pokedex.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


/**
 * Code taken from u/BigJhonny here: https://www.reddit.com/r/Kotlin/comments/9tc4gp/coroutines_are_executing_sequentially/
 */
fun <T> parallelFor(range: Iterable<T>, action: suspend (T) -> Unit) = runBlocking {
    for (t in range) launch(Dispatchers.Default) {
        action(t)
    }
}
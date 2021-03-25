package com.cis4030.pokedex.util

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.graphics.drawable.toDrawable
import com.cis4030.pokedex.R
import com.cis4030.pokedex.database.DatabasePokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


/**
 * Utility function to parallelize an action performed over an iterable.
 * Code taken from u/BigJhonny here: https://www.reddit.com/r/Kotlin/comments/9tc4gp/coroutines_are_executing_sequentially/
 */
fun <T> parallelFor(range: Iterable<T>, action: suspend (T) -> Unit) = runBlocking {
    for (t in range) launch(Dispatchers.Default) {
        action(t)
    }
}

/**
 * Utility function to return the background colour of a pokemon for the UI
 */
fun getColor(pokemon: DatabasePokemon): Drawable {
    return when(pokemon.types[0]){
        "grass" -> Color.parseColor("#4dd0b1").toDrawable()
        "fire" -> Color.parseColor("#fa6b6e").toDrawable()
        "water" -> Color.parseColor("#A4FF9A").toDrawable()
        "electric" -> Color.parseColor("#fec52b").toDrawable()
        "normal" -> Color.parseColor("#c6c6a7").toDrawable()
        "psychic" -> Color.parseColor("#fa92b2").toDrawable()
        "fighting" -> Color.parseColor("#ff564d").toDrawable()
        "flying" -> Color.parseColor("#a890f0").toDrawable()
        "poison" -> Color.parseColor("#a040a0").toDrawable()
        "ground" -> Color.parseColor("#e0c068").toDrawable()
        "rock" -> Color.parseColor("#b8a038").toDrawable()
        "ice" -> Color.parseColor("#98d8d8").toDrawable()
        "bug" -> Color.parseColor("#a8b820").toDrawable()
        "dragon" -> Color.parseColor("#8f61ff").toDrawable()
        "ghost" -> Color.parseColor("#af89f0").toDrawable()
        "dark" -> Color.parseColor("#8f857f").toDrawable()
        "steel" -> Color.parseColor("#b8b8d0").toDrawable()
        "fairy" -> Color.parseColor("#ffa1dc").toDrawable()
        else -> Color.WHITE.toDrawable()
    }
}
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
fun getColor(pokemon: DatabasePokemon): Int {
    return when(pokemon.type1.toLowerCase()){
        "grass" -> R.drawable.custom_rounded_corners_grass
        "fire" -> R.drawable.custom_rounded_corners_fire
        "water" -> R.drawable.custom_rounded_corners_water
        "electric" -> R.drawable.custom_rounded_corners_electric
        "normal" -> R.drawable.custom_rounded_corners_normal
        "psychic" -> R.drawable.custom_rounded_corners_psychic
        "fighting" -> R.drawable.custom_rounded_corners_fighting
        "flying" -> R.drawable.custom_rounded_corners_flying
        "poison" -> R.drawable.custom_rounded_corners_poison
        "ground" -> R.drawable.custom_rounded_corners_ground
        "rock" -> R.drawable.custom_rounded_corners_rock
        "ice" -> R.drawable.custom_rounded_corners_ice
        "bug" -> R.drawable.custom_rounded_corners_bug
        "dragon" -> R.drawable.custom_rounded_corners_dragon
        "ghost" -> R.drawable.custom_rounded_corners_ghost
        "dark" -> R.drawable.custom_rounded_corners_dark
        "steel" -> R.drawable.custom_rounded_corners_steel
        "fairy" -> R.drawable.custom_rounded_corners_fairy
        else -> R.drawable.custom_rounded_corners_white
    }
}
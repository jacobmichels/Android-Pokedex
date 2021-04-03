package com.cis4030.pokedex.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.core.graphics.drawable.toDrawable
import com.cis4030.pokedex.R
import com.cis4030.pokedex.database.DatabasePokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException


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
fun getColorRounded(type: String): Int {
    return when(type.toLowerCase()){
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

fun getTypeColor(typename: String): Int {
    return when(typename.toLowerCase()){
        "grass" -> R.color.grass
        "fire" -> R.color.fire
        "water" -> R.color.water
        "electric" -> R.color.electric
        "normal" -> R.color.normal
        "psychic" -> R.color.psychic
        "fighting" -> R.color.fighting
        "flying" -> R.color.flying
        "poison" -> R.color.poison
        "ground" -> R.color.ground
        "rock" -> R.color.rock
        "ice" -> R.color.ice
        "bug" -> R.color.bug
        "dragon" -> R.color.dragon
        "ghost" -> R.color.ghost
        "dark" -> R.color.dark
        "steel" -> R.color.steel
        "fairy" -> R.color.fairy
        else -> R.color.white
    }
}

fun getTeamBackground():Int{
    return R.drawable.team_rounded_corners
}

fun getBitmapFromUri(context: Context, uri:Uri): Bitmap? {
    try{
        val input = context.contentResolver.openInputStream(uri) ?: return null
        return BitmapFactory.decodeStream(input)
    }
    catch (e: FileNotFoundException){
        Log.d("POKEDEX","File not found.")
    }
    return null
}

fun bitmapToByteArray(bitmap:Bitmap):ByteArray{
    val output = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG,100,output)
    return output.toByteArray()
}

fun byteArrayToBitmap(bytes:ByteArray):Bitmap{
    val input = ByteArrayInputStream(bytes)
    return BitmapFactory.decodeStream(input)
}
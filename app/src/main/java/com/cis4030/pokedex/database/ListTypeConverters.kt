package com.cis4030.pokedex.database

import androidx.room.TypeConverter
import com.cis4030.pokedex.domain.Ability
import com.cis4030.pokedex.domain.Effect
import com.cis4030.pokedex.domain.Move
import com.cis4030.pokedex.domain.Type
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class ListTypeConverters {

    private val moshi: Moshi = Moshi.Builder().build()

    private val listOfStringsType = Types.newParameterizedType(List::class.java,String::class.java)
    private val listOfStringsAdapter = moshi.adapter<List<String>>(listOfStringsType)

    private val listOfEffectsType = Types.newParameterizedType(List::class.java, Effect::class.java)
    private val listOfEffectsAdapter = moshi.adapter<List<Effect>>(listOfEffectsType)

    @TypeConverter
    fun stringListToJson(strings: List<String>?):String?{
        return listOfStringsAdapter.toJson(strings)
    }
    @TypeConverter
    fun jsonToStringList(string: String?):List<String>?{
        return string?.let { listOfStringsAdapter.fromJson(string) }
    }

    @TypeConverter
    fun effectListToJson(effects: List<Effect>?):String?{
        return listOfEffectsAdapter.toJson(effects)
    }

    @TypeConverter
    fun jsonToEffectList(string:String?):List<Effect>?{
        return string?.let{ listOfEffectsAdapter.fromJson(string)}
    }
}
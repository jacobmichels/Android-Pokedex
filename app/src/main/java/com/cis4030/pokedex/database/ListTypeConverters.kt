package com.cis4030.pokedex.database

import androidx.room.TypeConverter
import com.cis4030.pokedex.domain.Ability
import com.cis4030.pokedex.domain.Move
import com.cis4030.pokedex.domain.Type
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class ListTypeConverters {

    private val moshi: Moshi = Moshi.Builder().build()
    private val listOfMovesType = Types.newParameterizedType(List::class.java,Move::class.java)
    private val listOfMovesAdapter = moshi.adapter<List<Move>>(listOfMovesType)

    private val listOfAbilitiesType = Types.newParameterizedType(List::class.java,Ability::class.java)
    private val listOfAbilitiesAdapter = moshi.adapter<List<Ability>>(listOfAbilitiesType)

    private val listOfTypesType = Types.newParameterizedType(List::class.java, Type::class.java)
    private val listOfTypesAdapter = moshi.adapter<List<Type>>(listOfTypesType)

    @TypeConverter
    fun moveListToJson(moves:List<Move>?):String?{
        return listOfMovesAdapter.toJson(moves)
    }

    @TypeConverter
    fun jsonToMoveList(string: String?): List<Move>? {
        return string?.let{listOfMovesAdapter.fromJson(string)}
    }

    @TypeConverter
    fun abilityListToJson(abilities:List<Ability>?):String?{
        return listOfAbilitiesAdapter.toJson(abilities)
    }
    @TypeConverter
    fun jsonToAbilityList(string: String?):List<Ability>?{
        return string?.let{listOfAbilitiesAdapter.fromJson(string)}
    }

    @TypeConverter
    fun typeListToJson(types:List<Type>?):String?{
        return listOfTypesAdapter.toJson(types)
    }
    @TypeConverter
    fun jsonToTypeList(string: String?):List<Type>?{
        return string?.let{listOfTypesAdapter.fromJson(string)}
    }
}
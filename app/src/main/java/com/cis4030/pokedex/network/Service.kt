package com.cis4030.pokedex.network

import com.cis4030.pokedex.network.datatransferobjects.ability.AbilityDTO
import com.cis4030.pokedex.network.datatransferobjects.ability.AbilityListDTO
import com.cis4030.pokedex.network.datatransferobjects.move.MoveDTO
import com.cis4030.pokedex.network.datatransferobjects.move.MoveListDTO
import com.cis4030.pokedex.network.datatransferobjects.pokemon.PokemonDTO
import com.cis4030.pokedex.network.datatransferobjects.pokemon.PokemonListDTO
import com.cis4030.pokedex.network.datatransferobjects.type.TypeDTO
import com.cis4030.pokedex.network.datatransferobjects.type.TypeListDTO
import com.squareup.moshi.Moshi
import org.w3c.dom.TypeInfo
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * This is the interface that defines how we interact with pokeapi.
 * We define methods and data types and they are implemented by retrofit automatically.
 */
interface PokeAPIService{
    @GET("pokemon?limit=2000")
    suspend fun getAllPokemon(): PokemonListDTO

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): PokemonDTO

    @GET("type/{name}")
    suspend fun getType(@Path("name")name: String): TypeDTO

    @GET("type?limit=2000")
    suspend fun getTypes(): TypeListDTO

    @GET("ability/{name}")
    suspend fun getAbility(@Path("name")name: String): AbilityDTO

    @GET("ability?limit=2000")
    suspend fun getAbilities(): AbilityListDTO

    @GET("move/{name}")
    suspend fun getMove(@Path("name")name:String): MoveDTO

    @GET("move?limit=100000")
    suspend fun getMoves(): MoveListDTO
}

object PokeAPINetwork {
    private val moshi = Moshi.Builder()
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val pokeAPI = retrofit.create(PokeAPIService::class.java)
}
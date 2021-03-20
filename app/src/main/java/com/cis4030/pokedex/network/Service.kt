package com.cis4030.pokedex.network

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface PokeAPIService{
    @GET("pokemon")
    suspend fun getPokemon(): PokemonListDTO
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
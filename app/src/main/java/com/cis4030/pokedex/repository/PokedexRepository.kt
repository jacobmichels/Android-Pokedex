package com.cis4030.pokedex.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.cis4030.pokedex.database.*
import com.cis4030.pokedex.domain.Pokemon
import com.cis4030.pokedex.network.PokeAPINetwork
import com.cis4030.pokedex.network.datatransferobjects.move.asDatabaseModel
import com.cis4030.pokedex.network.datatransferobjects.pokemon.asDatabaseModel
import com.cis4030.pokedex.network.datatransferobjects.type.asDatabaseModel
import com.cis4030.pokedex.network.datatransferobjects.ability.asDatabaseModel
import com.cis4030.pokedex.util.parallelFor
import com.google.api.Http
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.HttpCookie
import java.util.concurrent.TimeoutException
import kotlin.coroutines.coroutineContext

/**
 * Repository for fetching pokemon objects from pokeAPI and storing them on disk
 */
class PokedexRepository(private val database: PokemonDatabase) {
    val pokemon: LiveData<List<DatabasePokemon>> = database.pokemonDao.getPokemon()

    val types: LiveData<List<DatabaseType>> = database.typeDao.getTypes()

    val moves: LiveData<List<DatabaseMove>> = database.moveDao.getMoves()

    val abilities: LiveData<List<DatabaseAbility>> = database.abilityDao.getAbilities()

    suspend fun refreshDatabase() {

        val pokemonList = PokeAPINetwork.pokeAPI.getAllPokemon()
        parallelFor(pokemonList.results){
            try{
                val currentPokemon = PokeAPINetwork.pokeAPI.getPokemon(it.name)
                database.pokemonDao.insertOne(currentPokemon.asDatabaseModel())
                Log.d("POKEDEX","Inserted ${currentPokemon.name} || ${currentPokemon.id}")
            } catch (e: HttpException){
                Log.d("POKEDEX","HttpException occurred while refreshing pokemon: ${e.code()} || ${e.message()}")
            } catch (e: TimeoutException){
                Log.d("POKEDEX","Timeout occured while refreshing ability ${e.message}")
            }
        }

        val typeList = PokeAPINetwork.pokeAPI.getTypes()
        parallelFor(typeList.results){
            try {
                val currentType = PokeAPINetwork.pokeAPI.getType(it.name)
                database.typeDao.insertOne(currentType.asDatabaseModel())
                Log.d("POKEDEX", "Inserted ${currentType.name} || ${currentType.id}")
            } catch (e: HttpException) {
                Log.d("POKEDEX", "HttpException occurred while refreshing types: ${e.code()} || ${e.message()}")
            } catch (e: TimeoutException){
                Log.d("POKEDEX","Timeout occured while refreshing ability ${e.message}")
            }
        }

        val moveList = PokeAPINetwork.pokeAPI.getMoves()
        parallelFor(moveList.results){
            try {
                val currentMove = PokeAPINetwork.pokeAPI.getMove(it.name)
                database.moveDao.insertOne(currentMove.asDatabaseModel())
                Log.d("POKEDEX", "Inserted ${currentMove.name} || ${currentMove.id}")
            } catch (e: HttpException) {
                Log.d("POKEDEX", "HttpException occurred while refreshing moves: ${e.code()} || ${e.message()}")
            } catch (e: TimeoutException){
                Log.d("POKEDEX","Timeout occured while refreshing ability ${e.message}")
            }
        }

        val abilityList = PokeAPINetwork.pokeAPI.getAbilities()
        parallelFor(abilityList.results){
            try {
                val currentAbility = PokeAPINetwork.pokeAPI.getAbility(it.name)
                database.abilityDao.insertOne(currentAbility.asDatabaseModel())
                Log.d("POKEDEX", "Inserted ${currentAbility.name} || ${currentAbility.id}")
            } catch (e: HttpException) {
                Log.d("POKEDEX", "HttpException occurred while refreshing abilities: ${e.code()} || ${e.message()}")
            } catch (e: TimeoutException){
                Log.d("POKEDEX","Timeout occured while refreshing ability ${e.message}")
            }
        }
    }
}
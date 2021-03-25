package com.cis4030.pokedex.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.cis4030.pokedex.database.*
import com.cis4030.pokedex.network.PokeAPINetwork
import com.cis4030.pokedex.network.datatransferobjects.move.asDatabaseModel
import com.cis4030.pokedex.network.datatransferobjects.pokemon.asDatabaseModel
import com.cis4030.pokedex.network.datatransferobjects.type.asDatabaseModel
import com.cis4030.pokedex.network.datatransferobjects.ability.asDatabaseModel
import com.cis4030.pokedex.util.parallelFor
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

/**
 * Repository for fetching pokemon objects from pokeAPI/Room.
 * This is a layer of abstraction over top pokeAPI and Room.
 */
class PokedexRepository(private val database: PokemonDatabase) {
    /**
     * The list of pokemon updated in real time.
     */
    val pokemon: LiveData<List<DatabasePokemon>> = database.pokemonDao.getPokemon()

    /**
     * The list of types updated in real time.
     */
    val types: LiveData<List<DatabaseType>> = database.typeDao.getTypes()

    /**
     * The list of moves updated in real time.
     */
    val moves: LiveData<List<DatabaseMove>> = database.moveDao.getMoves()

    /**
     * The list of abilities updated in real time.
     */
    val abilities: LiveData<List<DatabaseAbility>> = database.abilityDao.getAbilities()

    /**
     * This function updates the database with new data from pokeAPI
     */
    suspend fun refreshDatabase() {

        val pokemonToFetch = PokeAPINetwork.pokeAPI.getAllPokemon()
        val pokemonToInsert = mutableListOf<DatabasePokemon>()
        parallelFor(pokemonToFetch.results){        //asynchronously iterate through the pokemon we need to fetch, and get them from the API
            try{
                val currentPokemon = PokeAPINetwork.pokeAPI.getPokemon(it.name)
                pokemonToInsert.add(currentPokemon.asDatabaseModel())       //add the pokemon we just retrieved to the list of pokemon to insert into the database
                Log.d("POKEDEX","Fetched ${currentPokemon.name} || ${currentPokemon.id}")
            } catch (e: HttpException){
                Log.d("POKEDEX","HttpException occurred while refreshing pokemon: ${e.code()} || ${e.message()}")
            } catch (e: SocketTimeoutException){
                Log.d("POKEDEX","Timeout occured while refreshing ability ${e.message}")
            }
        }
        //insert all the pokemon we fetched into the database
        database.pokemonDao.insertAll(pokemonToInsert)
        Log.d("POKEDEX","Inserted all fetched pokemon.")

        //do the same process with types, then moves, then abilities.

        val typesToFetch = PokeAPINetwork.pokeAPI.getTypes()
        val typesToInsert = mutableListOf<DatabaseType>()
        parallelFor(typesToFetch.results){
            try {
                val currentType = PokeAPINetwork.pokeAPI.getType(it.name)
                typesToInsert.add(currentType.asDatabaseModel())
                Log.d("POKEDEX", "Fetched ${currentType.name} || ${currentType.id}")
            } catch (e: HttpException) {
                Log.d("POKEDEX", "HttpException occurred while refreshing types: ${e.code()} || ${e.message()}")
            } catch (e: SocketTimeoutException){
                Log.d("POKEDEX","Timeout occured while refreshing ability ${e.message}")
            }
        }
        database.typeDao.insertAll(typesToInsert)
        Log.d("POKEDEX","Inserted all fetched types.")

        val movesToFetch = PokeAPINetwork.pokeAPI.getMoves()
        val movesToInsert = mutableListOf<DatabaseMove>()
        parallelFor(movesToFetch.results){
            try {
                val currentMove = PokeAPINetwork.pokeAPI.getMove(it.name)
                movesToInsert.add(currentMove.asDatabaseModel())
                Log.d("POKEDEX", "Inserted ${currentMove.name} || ${currentMove.id}")
            } catch (e: HttpException) {
                Log.d("POKEDEX", "HttpException occurred while refreshing moves: ${e.code()} || ${e.message()}")
            } catch (e: SocketTimeoutException){
                Log.d("POKEDEX","Timeout occured while refreshing ability ${e.message}")
            }
        }
        database.moveDao.insertAll(movesToInsert)
        Log.d("POKEDEX","Inserted all fetched moves.")


        val abilitiesToFetch = PokeAPINetwork.pokeAPI.getAbilities()
        val abilitiesToInsert = mutableListOf<DatabaseAbility>()
        parallelFor(abilitiesToFetch.results){
            try {
                val currentAbility = PokeAPINetwork.pokeAPI.getAbility(it.name)
                abilitiesToInsert.add(currentAbility.asDatabaseModel())
                Log.d("POKEDEX", "Inserted ${currentAbility.name} || ${currentAbility.id}")
            } catch (e: HttpException) {
                Log.d("POKEDEX", "HttpException occurred while refreshing abilities: ${e.code()} || ${e.message()}")
            } catch (e: SocketTimeoutException){
                Log.d("POKEDEX","Timeout occured while refreshing ability ${e.message}")
            }
        }
        database.abilityDao.insertAll(abilitiesToInsert)
        Log.d("POKEDEX","Inserted all fetched abilities.")
    }
}
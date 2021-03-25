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
 * Repository for fetching pokemon objects from pokeAPI and storing them on disk
 */
class PokedexRepository(private val database: PokemonDatabase) {
    val pokemon: LiveData<List<DatabasePokemon>> = database.pokemonDao.getPokemon()

    val types: LiveData<List<DatabaseType>> = database.typeDao.getTypes()

    val moves: LiveData<List<DatabaseMove>> = database.moveDao.getMoves()

    val abilities: LiveData<List<DatabaseAbility>> = database.abilityDao.getAbilities()

    suspend fun refreshDatabase() {

        val pokemonToFetch = PokeAPINetwork.pokeAPI.getAllPokemon()
        val pokemonToInsert = mutableListOf<DatabasePokemon>()
        parallelFor(pokemonToFetch.results){
            try{
                val currentPokemon = PokeAPINetwork.pokeAPI.getPokemon(it.name)
                pokemonToInsert.add(currentPokemon.asDatabaseModel())
                Log.d("POKEDEX","Fetched ${currentPokemon.name} || ${currentPokemon.id}")
            } catch (e: HttpException){
                Log.d("POKEDEX","HttpException occurred while refreshing pokemon: ${e.code()} || ${e.message()}")
            } catch (e: SocketTimeoutException){
                Log.d("POKEDEX","Timeout occured while refreshing ability ${e.message}")
            }
        }
        database.pokemonDao.insertAll(pokemonToInsert)
        Log.d("POKEDEX","Inserted all fetched pokemon.")

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
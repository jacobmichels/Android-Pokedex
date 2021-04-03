package com.cis4030.pokedex.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
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
    var dbPokemon: LiveData<List<DatabasePokemon>> = database.pokemonDao.getPokemonByNameDsc()

    val pokemonMediator = MediatorLiveData<List<DatabasePokemon>>()

    var currentSource: LiveData<List<DatabasePokemon>>

    init{
        val source = database.pokemonDao.getPokemonByIdAsc()
        pokemonMediator.addSource(source) {
            pokemonMediator.value = it
        }
        currentSource=source
    }

    /**
     * The list of types updated in real time.
     */
    val dbTypes: LiveData<List<DatabaseType>> = database.typeDao.getTypes()

    /**
     * The list of moves updated in real time.
     */
    val dbMoves: LiveData<List<DatabaseMove>> = database.moveDao.getMoves()

    /**
     * The list of abilities updated in real time.
     */
    val dbAbilities: LiveData<List<DatabaseAbility>> = database.abilityDao.getAbilities()

    fun reorganizePokemon(order: String, generationFilters:List<Int>, typeFilters: List<String>){
        if(generationFilters.isNotEmpty() && typeFilters.isNotEmpty()){
            when(order){
                "ID Ascending"-> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByIdAsc(generationFilters, typeFilters))
                }
                "ID Descending" ->{
                    setNewPokemonSource(database.pokemonDao.getPokemonByIdDsc(generationFilters,typeFilters))
                }
                "Name Ascending"->{
                    setNewPokemonSource(database.pokemonDao.getPokemonByName(generationFilters,typeFilters))
                }
                "Name Descending"->{
                    setNewPokemonSource(database.pokemonDao.getPokemonByNameDsc(generationFilters,typeFilters))
                }
            }
        }
        else if(generationFilters.isEmpty()) {
            when (order) {
                "ID Ascending" ->{
                    setNewPokemonSource(database.pokemonDao.getPokemonByIdAsc(typeFilters))
                }
                "ID Descending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByIdDsc(typeFilters))
                }
                "Name Ascending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByName(typeFilters))
                }
                "Name Descending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByNameDsc(typeFilters))
                }
            }
        }
        else if(typeFilters.isEmpty()){
            when (order) {
                "ID Ascending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByIdAscGenerationFilter(generationFilters))
                }
                "ID Descending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByIdDscGenerationFilter(generationFilters))
                }
                "Name Ascending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByNameGenerationFilter(generationFilters))
                }
                "Name Descending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByNameDscGenerationFilter(generationFilters))
                }
            }
        }
        //both empty
        else{
            when (order) {
                "ID Ascending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByIdAsc())
                }
                "ID Descending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByIdDsc())
                }
                "Name Ascending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByName())
                }
                "Name Descending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByNameDsc())
                }
            }
        }
    }

    fun searchPokemon(query:String,order: String, generationFilters:List<Int>, typeFilters: List<String>){
        if(generationFilters.isNotEmpty() && typeFilters.isNotEmpty()){
            when(order){
                "ID Ascending"-> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByIdAsc(query,generationFilters, typeFilters))
                }
                "ID Descending" ->{
                    setNewPokemonSource(database.pokemonDao.getPokemonByIdDsc(query,generationFilters,typeFilters))
                }
                "Name Ascending"->{
                    setNewPokemonSource(database.pokemonDao.getPokemonByName(query,generationFilters,typeFilters))
                }
                "Name Descending"->{
                    setNewPokemonSource(database.pokemonDao.getPokemonByNameDsc(query,generationFilters,typeFilters))
                }
            }
        }
        else if(generationFilters.isEmpty()) {
            when (order) {
                "ID Ascending" ->{
                    setNewPokemonSource(database.pokemonDao.getPokemonByIdAsc(query,typeFilters))
                }
                "ID Descending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByIdDsc(query,typeFilters))
                }
                "Name Ascending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByName(query,typeFilters))
                }
                "Name Descending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByNameDsc(query,typeFilters))
                }
            }
        }
        else if(typeFilters.isEmpty()){
            when (order) {
                "ID Ascending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByIdAscGenerationFilter(query,generationFilters))
                }
                "ID Descending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByIdDscGenerationFilter(query,generationFilters))
                }
                "Name Ascending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByNameGenerationFilter(query,generationFilters))
                }
                "Name Descending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByNameDscGenerationFilter(query,generationFilters))
                }
            }
        }
        //both empty
        else{
            when (order) {
                "ID Ascending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByIdAsc(query))
                }
                "ID Descending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByIdDsc(query))
                }
                "Name Ascending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByName(query))
                }
                "Name Descending" -> {
                    setNewPokemonSource(database.pokemonDao.getPokemonByNameDsc(query))
                }
            }
        }
    }

    private fun setNewPokemonSource(nextSource: LiveData<List<DatabasePokemon>>) {
        pokemonMediator.removeSource(currentSource)
        pokemonMediator.value = null
        currentSource = nextSource
        pokemonMediator.addSource(currentSource) {
            pokemonMediator.value = it
        }
    }

    fun restoreDefaultOrganization(){
        dbPokemon=database.pokemonDao.getPokemonByIdAsc()
    }

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
                Log.d("POKEDEX","Timeout occured while refreshing pokemon ${e.message}")
            }
        }
        val generationsToFetch = PokeAPINetwork.pokeAPI.getGenerations()
        parallelFor(generationsToFetch.results){ generation ->
            try{
                val currentGeneration = PokeAPINetwork.pokeAPI.getGeneration(generation.name)
                for (pokemon in currentGeneration.pokemon_species) {
                    pokemonToInsert.filter {
                        it.speciesName.equals(pokemon.name, ignoreCase = true)
                    }.map {
                        it.generation=currentGeneration.id
                    }
                }
            } catch (e:HttpException){
                Log.d("POKEDEX","HttpException occurred while update pokemon generations: ${e.code()} || ${e.message()}")
            } catch (e: SocketTimeoutException) {
                Log.d("POKEDEX","Timeout occured while fetching generations ${e.message}")
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
                Log.d("POKEDEX","Timeout occured while refreshing types ${e.message}")
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
                Log.d("POKEDEX","Timeout occured while refreshing moves ${e.message}")
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
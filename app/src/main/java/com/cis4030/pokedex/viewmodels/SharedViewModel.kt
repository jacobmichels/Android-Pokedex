package com.cis4030.pokedex.viewmodels

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.cis4030.pokedex.database.*
import com.cis4030.pokedex.repository.PokedexRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.reflect.typeOf

/**
 * This is a ViewModel which stores data for views.
 * It is needed because it survives configuration changes like screen rotations when views do not.
 */
class SharedViewModel(application: Application): AndroidViewModel(application) {
    private val _createText = MutableLiveData<String>().apply {
        value = "Creation screen placeholder"
    }
    val createText: LiveData<String> = _createText

    private val _teamText = MutableLiveData<String>().apply {
        value = "Team comp screen placeholder"
    }
    val teamText: LiveData<String> = _teamText

    var selectedOrder = "ID Ascending"

    //start with all generations and types selected
    var generationsSelected = mutableListOf<Int>(1,2,3,4,5,6,7,8)
    var typesSelected = mutableListOf<String>("Normal","Fire","Fighting","Water","Flying","Grass","Poison","Electric","Ground","Psychic","Rock","Ice","Bug","Dragon","Ghost","Dark","Steel","Fairy")

    private var generationsSelectedCopy: List<Int>? = null
    private var typesSelectedCopy: List<String>? = null

    fun createFilterListCopies(){
        generationsSelectedCopy= generationsSelected.toMutableList()
        typesSelectedCopy= typesSelected.toMutableList()
    }

    fun saveFilterChanges(){
        generationsSelectedCopy=null
        typesSelectedCopy=null
    }

    fun deleteFilterChanges(){
        generationsSelected= (generationsSelectedCopy as MutableList<Int>?)!!
        typesSelected= (typesSelectedCopy as MutableList<String>?)!!
    }

    /**
     * The data source this ViewModel will fetch results from.
     */
    private val pokedexRepository: PokedexRepository = PokedexRepository(getDatabase(application))

    val pokemonList: LiveData<List<DatabasePokemon>> = pokedexRepository.pokemonMediator

    val typeList: LiveData<List<DatabaseType>> = pokedexRepository.dbTypes

    val moveList: LiveData<List<DatabaseMove>> = pokedexRepository.dbMoves

    val abilityList:LiveData<List<DatabaseAbility>> = pokedexRepository.dbAbilities

    fun refreshDatabase(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                pokedexRepository.refreshDatabase()
            }
        }
    }

    fun sortAndFilterPokemon(){
        pokedexRepository.reorganizePokemon(selectedOrder,generationsSelected,typesSelected)
    }

    fun displayPokemonDetails(pokemon: DatabasePokemon){
        Log.d("POKEDEX","displaying pokemon with name: ${pokemon.name}")
    }

    /**
     * Factory for constructing SharedViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SharedViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}
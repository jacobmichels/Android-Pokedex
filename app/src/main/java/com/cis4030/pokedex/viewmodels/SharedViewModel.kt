package com.cis4030.pokedex.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.cis4030.pokedex.database.DatabasePokemon
import com.cis4030.pokedex.database.DatabaseType
import com.cis4030.pokedex.database.getDatabase
import com.cis4030.pokedex.repository.PokedexRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    /**
     * The data source this ViewModel will fetch results from.
     */
    private val pokedexRepository: PokedexRepository = PokedexRepository(getDatabase(application))

    val pokemonList: LiveData<List<DatabasePokemon>> = pokedexRepository.pokemon

    val typeList: LiveData<List<DatabaseType>> = pokedexRepository.types

    fun refreshDatabase(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                pokedexRepository.refreshDatabase()
            }
        }
    }

    fun displayPokemonDetails(pokemon: DatabasePokemon){
        Log.d("POKEDEX","displaying pokemon with name: ${pokemon.name}")
    }

    /**
     * Factory for constructing DevByteViewModel with parameter
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
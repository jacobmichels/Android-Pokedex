package com.cis4030.pokedex.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.cis4030.pokedex.database.DatabaseCustomPokemon
import com.cis4030.pokedex.database.getDatabase
import com.cis4030.pokedex.repository.PokedexRepository

class CustomListViewModel(application: Application): AndroidViewModel(application) {
    private val repository = PokedexRepository(getDatabase(application))

    val customPokemon = repository.customPokemon

    fun displayPokemonDetails(pokemon: DatabaseCustomPokemon){
        Log.d("POKEDEX","Clicked ${pokemon.name}")
    }


}
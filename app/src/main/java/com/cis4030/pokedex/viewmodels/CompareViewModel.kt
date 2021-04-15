package com.cis4030.pokedex.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.cis4030.pokedex.database.DatabasePokemon
import com.cis4030.pokedex.database.getDatabase
import com.cis4030.pokedex.repository.PokedexRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates

class CompareViewModel(application: Application): AndroidViewModel(application) {
    val repository = PokedexRepository(getDatabase(application))

    lateinit var pokemon1: DatabasePokemon
    lateinit var pokemon2: DatabasePokemon

    var power1 by Delegates.notNull<Int>()
    var power2 by Delegates.notNull<Int>()

    fun getPokemon(pokemon1Name: String, pokemon2Name:String){
        runBlocking {
            withContext(Dispatchers.IO){
                Log.d("POKEDEX","finding pokemon with names ${pokemon1Name} and ${pokemon2Name}")
                pokemon1 = repository.getPokemonByName(pokemon1Name.capitalize())
                pokemon2 = repository.getPokemonByName(pokemon2Name.capitalize())
            }
        }
    }

    fun calculatePowers(){
        power1 = pokemon1.baseHP + pokemon1.baseAtk + pokemon1.baseDfn + pokemon1.spDfn + pokemon1.spAtk + pokemon1.speed
        power2 = pokemon2.baseHP + pokemon2.baseAtk + pokemon2.baseDfn + pokemon2.spDfn + pokemon2.spAtk + pokemon2.speed
    }
}
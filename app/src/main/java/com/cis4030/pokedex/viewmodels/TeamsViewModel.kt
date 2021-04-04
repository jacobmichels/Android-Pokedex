package com.cis4030.pokedex.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.cis4030.pokedex.database.DatabasePokemon
import com.cis4030.pokedex.database.DatabaseTeam
import com.cis4030.pokedex.database.getDatabase
import com.cis4030.pokedex.repository.PokedexRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeamsViewModel(application: Application): AndroidViewModel(application) {
    private val repository = PokedexRepository(getDatabase(application))
    val teamsList: LiveData<List<DatabaseTeam>> = repository.teams

    var pokemon1: DatabasePokemon? = null
    var pokemon2: DatabasePokemon? = null
    var pokemon3: DatabasePokemon? = null
    var pokemon4: DatabasePokemon? = null
    var pokemon5: DatabasePokemon? = null
    var pokemon6: DatabasePokemon? = null

    lateinit var teamToDisplay: DatabaseTeam

    fun addTeam(teamname:String):Boolean{
        if(teamsList.value?.any { it.name==teamname } == true){
            return false
        }
        else{
            val team = DatabaseTeam(
                name=teamname,
                pokemon1Name = null,
                pokemon2Name = null,
                pokemon3Name = null,
                pokemon4Name = null,
                pokemon5Name = null,
                pokemon6Name = null,
                isEmpty = true
            )
            viewModelScope.launch {
                withContext(Dispatchers.IO){
                    repository.insertTeam(team)
                }
            }
            return true
        }
    }

    fun populatePokemonFields(){
        if(teamToDisplay.pokemon1Name!=null){
            pokemon1= repository.dbPokemon.value?.find { it.name==teamToDisplay.pokemon1Name }
        }
        if(teamToDisplay.pokemon2Name!=null){
            pokemon2= repository.dbPokemon.value?.find { it.name==teamToDisplay.pokemon2Name }
        }
        if(teamToDisplay.pokemon3Name!=null){
            pokemon3= repository.dbPokemon.value?.find { it.name==teamToDisplay.pokemon3Name }
        }
        if(teamToDisplay.pokemon4Name!=null){
            pokemon4= repository.dbPokemon.value?.find { it.name==teamToDisplay.pokemon4Name }
        }
        if(teamToDisplay.pokemon5Name!=null){
            pokemon5= repository.dbPokemon.value?.find { it.name==teamToDisplay.pokemon5Name }
        }
        if(teamToDisplay.pokemon6Name!=null){
            pokemon6= repository.dbPokemon.value?.find { it.name==teamToDisplay.pokemon6Name }
        }
    }

}
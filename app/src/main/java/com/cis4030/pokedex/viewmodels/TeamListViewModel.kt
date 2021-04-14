package com.cis4030.pokedex.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cis4030.pokedex.database.DatabasePokemon
import com.cis4030.pokedex.database.DatabaseTeam
import com.cis4030.pokedex.database.getDatabase
import com.cis4030.pokedex.repository.PokedexRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeamListViewModel(application: Application): AndroidViewModel(application) {
    val repository = PokedexRepository(getDatabase(application))
    val teamsList: LiveData<List<DatabaseTeam>> = repository.teams

    private val _powerText = MutableLiveData<String>("Team Power Level: 0")
    val powerText :LiveData<String> = _powerText

    fun addTeam(teamname:String):Boolean{
        if(teamsList.value?.any { it.name==teamname } == true){
            return false
        }
        else{
            val team = DatabaseTeam(
                name=teamname,
                power=0,
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

//    fun populatePokemonFields(){
//        if(teamToDisplay.pokemon1Name!=null){
//            pokemon1= repository.dbPokemon.value?.find { it.name==teamToDisplay.pokemon1Name }
//        }
//        if(teamToDisplay.pokemon2Name!=null){
//            pokemon2= repository.dbPokemon.value?.find { it.name==teamToDisplay.pokemon2Name }
//        }
//        if(teamToDisplay.pokemon3Name!=null){
//            pokemon3= repository.dbPokemon.value?.find { it.name==teamToDisplay.pokemon3Name }
//        }
//        if(teamToDisplay.pokemon4Name!=null){
//            pokemon4= repository.dbPokemon.value?.find { it.name==teamToDisplay.pokemon4Name }
//        }
//        if(teamToDisplay.pokemon5Name!=null){
//            pokemon5= repository.dbPokemon.value?.find { it.name==teamToDisplay.pokemon5Name }
//        }
//        if(teamToDisplay.pokemon6Name!=null){
//            pokemon6= repository.dbPokemon.value?.find { it.name==teamToDisplay.pokemon6Name }
//        }
//        _powerText.value="Team Power Level: ${teamToDisplay.power}"
//    }

//    fun updateTeamName(name: String): Boolean{
//        if(teamsList.value?.any {
//                it.name==name
//            } == true){
//            return false
//        }
//
//        viewModelScope.launch {
//            withContext(Dispatchers.IO){
//                repository.deleteTeam(teamToDisplay)
//                teamToDisplay.name=name
//                repository.insertTeam(teamToDisplay)
//            }
//        }
//        return true
//    }

//    fun deleteTeam(){
//        viewModelScope.launch {
//            withContext(Dispatchers.IO){
//                repository.deleteTeam(teamToDisplay)
//            }
//        }
//    }

//    fun addPokemon(name: String){
//        viewModelScope.launch {
//            withContext(Dispatchers.IO){
//                repository.deleteTeam(teamToDisplay)
//                when {
//                    teamToDisplay.pokemon1Name.isNullOrBlank() -> {
//                        teamToDisplay.pokemon1Name=name
//                    }
//                    teamToDisplay.pokemon2Name.isNullOrBlank() -> {
//                        teamToDisplay.pokemon2Name=name
//                    }
//                    teamToDisplay.pokemon3Name.isNullOrBlank() -> {
//                        teamToDisplay.pokemon3Name=name
//                    }
//                    teamToDisplay.pokemon4Name.isNullOrBlank() -> {
//                        teamToDisplay.pokemon4Name=name
//                    }
//                    teamToDisplay.pokemon5Name.isNullOrBlank() -> {
//                        teamToDisplay.pokemon5Name=name
//                    }
//                    teamToDisplay.pokemon6Name.isNullOrBlank() -> {
//                        teamToDisplay.pokemon6Name=name
//                    }
//                }
//                repository.insertTeam(teamToDisplay)
//            }
//        }




//        viewModelScope.launch {
//            val pokemonToAdd = repository.getPokemonByName(name)
//            Log.d("POKEDEX", "Adding pokemon with species name: ${pokemonToAdd.speciesName}")
//            if(pokemon1==null){
//                teamToDisplay.pokemon1 = pokemonToAdd
//            }
//            else if(pokemon2 ==null){
//                pokemon2=pokemonToAdd
//            }
//            else if(pokemon3 ==null){
//                pokemon3=pokemonToAdd
//            }
//            else if(pokemon4 ==null){
//                pokemon4=pokemonToAdd
//            }
//            else if(pokemon5 ==null){
//                pokemon5=pokemonToAdd
//            }
//            else if(pokemon6 ==null){
//                pokemon6=pokemonToAdd
//            }
//        }

}
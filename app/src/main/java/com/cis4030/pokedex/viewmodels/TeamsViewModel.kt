package com.cis4030.pokedex.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.cis4030.pokedex.database.DatabaseTeam
import com.cis4030.pokedex.database.getDatabase
import com.cis4030.pokedex.repository.PokedexRepository

class TeamsViewModel(application: Application): AndroidViewModel(application) {
    private val repository = PokedexRepository(getDatabase(application))
    val teamsList: LiveData<List<DatabaseTeam>> = repository.teams

    fun addTeam(teamname:String):Boolean{
        if(teamsList.value?.any { it.name==teamname } == true){
            return false
        }
        else{
            return true
        }
    }

}
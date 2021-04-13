package com.cis4030.pokedex.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import com.cis4030.pokedex.database.DatabasePokemon
import com.cis4030.pokedex.database.DatabaseTeam
import com.cis4030.pokedex.database.getDatabase
import com.cis4030.pokedex.repository.PokedexRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class TeamViewModel(application: Application): AndroidViewModel(application) {

    private val repository = PokedexRepository(getDatabase(application))

    val powerText = MutableLiveData<String>()

    val refreshUI = MutableLiveData<Boolean>()

    var pokemon1: DatabasePokemon? = null
    var pokemon2: DatabasePokemon? = null
    var pokemon3: DatabasePokemon? = null
    var pokemon4: DatabasePokemon? = null
    var pokemon5: DatabasePokemon? = null
    var pokemon6: DatabasePokemon? = null

    var team: DatabaseTeam? = null
    var teamname: String? = null


    fun loadTeam(name:String){
        powerText.value="Team Power Rating: 0"
        teamname = name
        runBlocking {
            team = repository.getTeamByName(name)!!

            pokemon1=getPokemon(team!!.pokemon1Name)
            pokemon2=getPokemon(team!!.pokemon2Name)
            pokemon3=getPokemon(team!!.pokemon3Name)
            pokemon4=getPokemon(team!!.pokemon4Name)
            pokemon5=getPokemon(team!!.pokemon5Name)
            pokemon6=getPokemon(team!!.pokemon6Name)
        }
    }

    fun addPokemon(name: String){
        team?.isEmpty=false
        when{
            team?.pokemon1Name==null->{
                team?.pokemon1Name=name
                pokemon1=getPokemon(team?.pokemon1Name)
                Log.d("POKEDEX","added $name to slot 1")
            }
            team?.pokemon2Name==null->{
                team?.pokemon2Name=name
                pokemon2=getPokemon(team?.pokemon2Name)
                Log.d("POKEDEX","added $name to slot 2")
            }
            team?.pokemon3Name==null->{
                team?.pokemon3Name=name
                pokemon3=getPokemon(team?.pokemon3Name)
                Log.d("POKEDEX","added $name to slot 3")
            }
            team?.pokemon4Name==null->{
                team?.pokemon4Name=name
                pokemon4=getPokemon(team?.pokemon4Name)
                Log.d("POKEDEX","added $name to slot 4")
            }
            team?.pokemon5Name==null->{
                team?.pokemon5Name=name
                pokemon5=getPokemon(team?.pokemon5Name)
                Log.d("POKEDEX","added $name to slot 5")
            }
            team?.pokemon6Name==null->{
                team?.pokemon6Name=name
                pokemon6=getPokemon(team?.pokemon6Name)
                Log.d("POKEDEX","added $name to slot 6")
            }
        }

        team?.power = getPower()

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.updateTeam(team!!)
                refreshUI.postValue(true)
            }
        }
    }

    fun refreshedUI(){
        refreshUI.value=false
    }

    fun getPokemon(name:String?):DatabasePokemon?{
        if(name!=null){
            return runBlocking {
                repository.getPokemonByName(name)
            }
        }
        return null
    }

    fun setPower(power: Int){
        powerText.value="Team Power Rating: $power"
    }

    fun getPower():Int{
        var power = 0
        pokemon1?.let{
            power += it.baseHP + it.baseAtk + it.baseDfn + it.spAtk+it.spDfn+it.speed
        }
        pokemon2?.let{
            power += it.baseHP + it.baseAtk + it.baseDfn + it.spAtk+it.spDfn+it.speed
        }
        pokemon3?.let{
            power += it.baseHP + it.baseAtk + it.baseDfn + it.spAtk+it.spDfn+it.speed
        }
        pokemon4?.let{
            power += it.baseHP + it.baseAtk + it.baseDfn + it.spAtk+it.spDfn+it.speed
        }
        pokemon5?.let{
            power += it.baseHP + it.baseAtk + it.baseDfn + it.spAtk+it.spDfn+it.speed
        }
        pokemon6?.let{
            power += it.baseHP + it.baseAtk + it.baseDfn + it.spAtk+it.spDfn+it.speed
        }
        return power
    }

    fun deleteTeam(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.deleteTeam(team!!)
                team=null
            }
        }
    }

    fun updateTeamName(name:String):Boolean{
        val teamExists = runBlocking<Boolean> { repository.getTeamByName(name)!=null }
        if(teamExists){
            return false
        }


        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.deleteTeam(team!!)
                team?.name=name
                repository.insertTeam(team!!)
            }
        }
        return true
    }

    fun clearTeam(){
        team?.isEmpty=true
        team?.pokemon1Name=null
        team?.pokemon2Name=null
        team?.pokemon3Name=null
        team?.pokemon4Name=null
        team?.pokemon5Name=null
        team?.pokemon6Name=null


        pokemon1=null
        pokemon2=null
        pokemon3=null
        pokemon4=null
        pokemon5=null
        pokemon6=null

        powerText.value="Team Power Rating: 0"

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.updateTeam(team!!)
            }
        }
    }

}
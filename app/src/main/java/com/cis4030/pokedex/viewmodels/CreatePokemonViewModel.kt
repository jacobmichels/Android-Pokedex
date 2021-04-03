package com.cis4030.pokedex.viewmodels

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.*
import com.cis4030.pokedex.database.DatabaseCustomMove
import com.cis4030.pokedex.database.DatabaseCustomPokemon
import com.cis4030.pokedex.database.DatabaseMove
import com.cis4030.pokedex.database.getDatabase
import com.cis4030.pokedex.repository.PokedexRepository
import kotlinx.coroutines.launch

class CreatePokemonViewModel(application: Application): AndroidViewModel(application) {

    val repository = PokedexRepository(getDatabase(application))

    lateinit var customPokemon: List<DatabaseCustomPokemon>

    val moveList= mutableListOf<DatabaseCustomMove>()

    val updateMoveList = MutableLiveData<Boolean?>()

    val updateHP = MutableLiveData<Boolean?>()
    val updateATK = MutableLiveData<Boolean?>()
    val updateDEF = MutableLiveData<Boolean?>()
    val updateSPATK = MutableLiveData<Boolean?>()
    val updateSPDEF = MutableLiveData<Boolean?>()
    val updateSPD = MutableLiveData<Boolean?>()

    val enableType2Spinner = MutableLiveData<Boolean?>()

    lateinit var imageBitmap: Bitmap

    var name : String? = null

    var description: String? = null

    var type1: String = "Select Type"
    var type2: String = "Select Type"

    var hp = 0
    var atk = 0
    var def = 0
    var spAtk = 0
    var spDef = 0
    var spd = 0

    init {
        getCustomPokemonList()
    }

    fun updateText(string: String){
        when(string){
            "hp"->{
                updateHP.value=true
            }
            "atk"->{
                updateATK.value=true
            }
            "def"->{
                updateDEF.value=true
            }
            "sp_atk"->{
                updateSPATK.value=true
            }
            "sp_def"->{
                updateSPDEF.value=true
            }
            "spd"->{
                updateSPD.value=true
            }
        }
    }

    fun textUpdated(string: String){
        when(string){
            "hp"->{
                updateHP.value=null
            }
            "atk"->{
                updateATK.value=null
            }
            "def"->{
                updateDEF.value=null
            }
            "sp_atk"->{
                updateSPATK.value=null
            }
            "sp_def"->{
                updateSPDEF.value=null
            }
            "spd"->{
                updateSPD.value=null
            }
        }
    }
    fun enableType2Spinner(){
        enableType2Spinner.value=true
    }

    fun updateMoveList(){
        updateMoveList.value=true
    }
    fun moveListUpdated(){
        updateMoveList.value=null
    }

    fun insertCustomPokemon(pokemon:DatabaseCustomPokemon){
        viewModelScope.launch {
            repository.insertCustomPokemon(pokemon)
        }
    }

    fun insertCustomMoves(){
        viewModelScope.launch {
            repository.insertCustomMoves(moveList)
        }
    }

    private fun getCustomPokemonList(){
        viewModelScope.launch {
            customPokemon= repository.getCustomPokemon()
        }
    }
}
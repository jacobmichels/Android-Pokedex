package com.cis4030.pokedex.ui.pokedex_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cis4030.pokedex.network.PokeAPINetwork
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is where the pokemon entries will appear."
    }
    val text: LiveData<String> = _text

    init{
        viewModelScope.launch {
            val list = PokeAPINetwork.pokeAPI.getPokemon()
            _text.value = list.count.toString()
        }

    }
}
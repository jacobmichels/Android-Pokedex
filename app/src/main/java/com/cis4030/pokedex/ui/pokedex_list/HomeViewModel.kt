package com.cis4030.pokedex.ui.pokedex_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is where the pokemon entries will appear."
    }
    val text: LiveData<String> = _text
}
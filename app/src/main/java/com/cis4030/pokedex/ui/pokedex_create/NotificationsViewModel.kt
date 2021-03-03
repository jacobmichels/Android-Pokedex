package com.cis4030.pokedex.ui.pokedex_create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is where the user will be able to create and upload a pokemon."
    }
    val text: LiveData<String> = _text
}
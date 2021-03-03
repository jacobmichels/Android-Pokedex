package com.cis4030.pokedex.ui.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is where the user will be able to manage their team."
    }
    val text: LiveData<String> = _text
}
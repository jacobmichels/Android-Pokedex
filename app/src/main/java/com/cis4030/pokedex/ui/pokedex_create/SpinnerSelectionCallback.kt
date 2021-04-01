package com.cis4030.pokedex.ui.pokedex_create

import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.cis4030.pokedex.R
import com.cis4030.pokedex.viewmodels.CreatePokemonViewModel

class SpinnerSelectionCallback(val viewModel: CreatePokemonViewModel): AdapterView.OnItemSelectedListener {
    //this callback gets called on initialization of the fragment. use callcount to make sure it's the user calling selecting an item.
    var callCount = 0

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        callCount++
        if(callCount==1||callCount==2){
            return
        }
        if (parent != null) {
            if(parent.id== R.id.type_1_spinner){
                viewModel.type1=parent.getItemAtPosition(position) as String
                viewModel.enableType2Spinner()
            }
            if(parent.id== R.id.type_2_spinner){
                viewModel.type2=parent.getItemAtPosition(position) as String
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.d("POKEDEX","Nothing selected.")
    }
}
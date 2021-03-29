package com.cis4030.pokedex.ui.pokedex_list

import android.widget.SearchView
import com.cis4030.pokedex.viewmodels.SharedViewModel


class PokemonQueryListener(sharedViewModel: SharedViewModel): SearchView.OnQueryTextListener {
    val sharedViewModel = sharedViewModel
    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            sharedViewModel.searchPokemon("%${newText}%")
        }
        return true
    }
}
package com.cis4030.pokedex.ui.pokedex_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.cis4030.pokedex.R
import com.cis4030.pokedex.viewmodels.SharedViewModel

class PokedexListFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val root = inflater.inflate(R.layout.fragment_list, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        viewModel.pokemonList.observe(viewLifecycleOwner, Observer {
            textView.text = "Pokemon count: ${it.size}"
        })
        return root
    }
}
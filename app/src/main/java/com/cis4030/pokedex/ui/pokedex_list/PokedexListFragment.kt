package com.cis4030.pokedex.ui.pokedex_list

import android.os.Bundle
import android.util.Log
import android.view.*
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
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_list, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        viewModel.pokemonList.observe(viewLifecycleOwner, Observer {
            textView.text = "Pokemon count: ${it.size}"
        })
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.refresh_menu_button ->{
                viewModel.refreshDatabase()
                true
            }
            R.id.about_menu_button ->{
                Log.d("POKEDEX","About menu button clicked.")
                true
            }
            R.id.settings_menu_button ->{
                Log.d("POKEDEX","Settings menu button clicked.")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
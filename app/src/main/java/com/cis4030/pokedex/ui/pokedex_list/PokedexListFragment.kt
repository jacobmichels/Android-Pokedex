package com.cis4030.pokedex.ui.pokedex_list

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.cis4030.pokedex.R
import com.cis4030.pokedex.database.DatabasePokemon
import com.cis4030.pokedex.databinding.FragmentListBinding
import com.cis4030.pokedex.databinding.PokedexViewItemBinding
import com.cis4030.pokedex.viewmodels.SharedViewModel

class PokedexListFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentListBinding = FragmentListBinding.inflate(inflater)

        binding.lifecycleOwner=this
        binding.viewModel=viewModel
        binding.pokemonGrid.adapter = PokemonGridAdapter(PokemonGridAdapter.OnClickListener{
            viewModel.displayPokemonDetails(it)
        })

        setHasOptionsMenu(true)

//        viewModel.pokemonList.observe(viewLifecycleOwner, Observer {
//            binding.textHome.text = "Pokemon count: ${it.size}"
//        })
        return binding.root
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
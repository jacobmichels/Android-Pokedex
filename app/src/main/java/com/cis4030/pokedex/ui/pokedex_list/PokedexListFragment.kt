package com.cis4030.pokedex.ui.pokedex_list

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.cis4030.pokedex.R
import com.cis4030.pokedex.database.DatabasePokemon
import com.cis4030.pokedex.databinding.FragmentListBinding
import com.cis4030.pokedex.viewmodels.SharedViewModel

class PokedexListFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //Use data binding to remove expensive calls to findviewbyid()
        val binding: FragmentListBinding = FragmentListBinding.inflate(inflater)
        binding.lifecycleOwner=this
        binding.viewModel=viewModel

        //add the PokemonGridAdapter, which is responsible for mapping pokemon data to the recyclerview
        binding.pokemonGrid.adapter = PokemonGridAdapter(PokemonGridAdapter.OnClickListener{
//            viewModel.displayPokemonDetails(it)    //onclick, call this function,
            view?.let { it1 -> displayPokemonDetails(it, it1) }
        })

        binding.pokemonGrid.addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.item_margin)))
        viewModel.pokemonList.observe(viewLifecycleOwner){
            binding.noPokemonText.isVisible = it.isEmpty()
        }

        setHasOptionsMenu(true)     //use an options menu

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_fragment_menu,menu)
        (menu.findItem(R.id.search_menu_button).actionView as SearchView).apply {
            setOnQueryTextListener(PokemonQueryListener(viewModel))
            viewModel.clearSearchText.observe(viewLifecycleOwner){
                if(it){
                    setQuery("",true)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.refresh_menu_button ->{
                Toast.makeText(context,"Refreshing information. This may take a few minutes.",Toast.LENGTH_LONG).show()
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
            R.id.filter_menu_button -> {
                Log.d("POKEDEX","Filter menu button clicked.")
                val dialogFragment = FilterDialogFragment()
                dialogFragment.isCancelable = false
                dialogFragment.show(parentFragmentManager,"Dialog")
                true
            }
            R.id.sort_menu_button-> {
                val dialogFragment = SortDialogFragment()
                dialogFragment.isCancelable = false
                dialogFragment.show(parentFragmentManager,"Dialog")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // goto the detail view from this view.
    private fun displayPokemonDetails(pokemon: DatabasePokemon, view:View) {
        Log.d("detail", "Pokemon clicked " + pokemon.name)
        view.findNavController().navigate(PokedexListFragmentDirections.actionPokedexHomeToPokemonDetailFragment(pokemon.id))
    }
}
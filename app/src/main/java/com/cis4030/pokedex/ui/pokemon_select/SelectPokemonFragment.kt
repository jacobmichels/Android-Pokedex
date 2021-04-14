package com.cis4030.pokedex.ui.pokemon_select

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cis4030.pokedex.R
import com.cis4030.pokedex.database.DatabasePokemon
import com.cis4030.pokedex.databinding.FragmentListSelectBinding
import com.cis4030.pokedex.ui.pokedex_list.FilterDialogFragment
import com.cis4030.pokedex.ui.pokedex_list.MarginItemDecoration
import com.cis4030.pokedex.ui.pokedex_list.PokemonQueryListener
import com.cis4030.pokedex.ui.pokedex_list.SortDialogFragment
import com.cis4030.pokedex.viewmodels.SharedViewModel
import java.util.*

class SelectPokemonFragment: Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()

    var compare:Boolean = false
    var basePokemon: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if(arguments?.getBoolean("compare")==true){
            compare=true
            basePokemon = requireArguments().getString("basePokemon")
        }

        val binding = FragmentListSelectBinding.inflate(inflater)
        binding.lifecycleOwner=this
        binding.viewModel=sharedViewModel
        binding.selectPokemonGrid.adapter = PokemonSelectGridAdapter(PokemonSelectGridAdapter.OnClickListener{
            selectPokemon(it)
        })
        binding.selectPokemonGrid.addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.item_margin)))
        sharedViewModel.pokemonList.observe(viewLifecycleOwner){
            binding.noPokemonText.isVisible=it.isEmpty()
        }
        Log.d("POKEDEX","number of pokemon: ${sharedViewModel.pokemonList.value?.size}")

        setHasOptionsMenu(true)

        (activity as AppCompatActivity?)?.supportActionBar?.title="Select a Pokemon"

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.select_fragment_menu,menu)
        (menu.findItem(R.id.search_menu_button).actionView as SearchView).apply {
            setOnQueryTextListener(PokemonQueryListener(sharedViewModel))
            sharedViewModel.clearSearchText.observe(viewLifecycleOwner){
                if(it){
                    setQuery("",true)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.filter_menu_button -> {
                val dialog = FilterDialogFragment()
                dialog.isCancelable=false
                dialog.show(parentFragmentManager,"Filter Dialog")
                true
            }
            R.id.sort_menu_button -> {
                val dialog = SortDialogFragment()
                dialog.isCancelable=false
                dialog.show(parentFragmentManager,"Sort Dialog")
                true
            }
            android.R.id.home -> {
                findNavController().navigateUp()
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun selectPokemon(pokemon: DatabasePokemon){
        if(compare){
            Toast.makeText(context,"Selected: ${pokemon.name}",Toast.LENGTH_LONG).show()
            val pokemon2 = pokemon.name
            val action = SelectPokemonFragmentDirections.actionSelectPokemonFragmentToCompareFragment(pokemon1 = basePokemon!!,pokemon2 = pokemon2)
            findNavController().navigate(action)
        }
        else{
            Toast.makeText(context,"Selected: ${pokemon.name}",Toast.LENGTH_LONG).show()
            val action = SelectPokemonFragmentDirections.actionSelectPokemonFragmentToTeamViewFragment(pokemon = pokemon.name)
            findNavController().navigate(action)
        }
    }
}
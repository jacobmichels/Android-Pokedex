package com.cis4030.pokedex.ui.pokedex_create

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cis4030.pokedex.R
import com.cis4030.pokedex.databinding.FragmentCreationListBinding
import com.cis4030.pokedex.ui.pokedex_list.MarginItemDecoration
import com.cis4030.pokedex.viewmodels.CustomListViewModel
import com.cis4030.pokedex.viewmodels.SharedViewModel

class CustomPokemonListFragment : Fragment() {

    private val viewModel: CustomListViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCreationListBinding.inflate(inflater)
        binding.lifecycleOwner=this
        binding.viewModel=viewModel
        binding.customPokemonGrid.adapter = CustomPokemonGridAdapter(CustomPokemonGridAdapter.OnClickListener{
            viewModel.displayPokemonDetails(it)     //onclick, call this function
        })
        binding.customPokemonGrid.addItemDecoration(LinearMarginItemDecoration(resources.getDimensionPixelSize(R.dimen.item_margin)))

        (activity as AppCompatActivity?)?.supportActionBar?.title="My Pokemon"

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.create_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.new_custom_pokemon -> {
                val action = CustomPokemonListFragmentDirections.actionPokedexCreateToCreatePokemonFragment()
                findNavController().navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
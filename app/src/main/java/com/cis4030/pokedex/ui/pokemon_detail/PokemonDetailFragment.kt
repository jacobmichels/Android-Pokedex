package com.cis4030.pokedex.ui.pokemon_detail

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.cis4030.pokedex.R
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import me.sargunvohra.lib.pokekotlin.model.Pokemon
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies

import kotlin.math.round


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PokemonDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PokemonDetailFragment : Fragment() {
    private var pokemonID: Int = 0
    private val idKey:String = "pokemonID"
    private var pokemonSpecies:PokemonSpecies? = null
    private var pokemon:Pokemon? = null


    var SDK_INT = Build.VERSION.SDK_INT


    private var name:String = ""
    private var description:String = ""
    private var growthRate:String = ""
    private var habitat:String = ""

    //in m
    private var height:String = ""

    //in KG
    private var weight:String = ""

    private var eggGroup:ArrayList<String> = arrayListOf()

    private var heightNum:Float = 0.0f
    private var weightNum:Float = 0.0f





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pokemonID = it.getInt(idKey)


            //just debug stuff for now, will put this into a class or something.

            //need this to make the api calls
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)

            val pokeApi = PokeApiClient()
            pokemonSpecies = pokeApi.getPokemonSpecies(pokemonID)
            pokemon = pokeApi.getPokemon(pokemonID)

            // need to know what generations the pokemon is in, 10 is FireRed description but it wont work for all of them.
            name = pokemonSpecies!!.name
            description = pokemonSpecies!!.flavorTextEntries[10].flavorText
            description = description.replace("\\n", "")
            description = description.replace("\\t", "")
            growthRate = pokemonSpecies!!.growthRate.name
            habitat = pokemonSpecies!!.habitat!!.name


            for(element in pokemonSpecies!!.eggGroups) {
                eggGroup.add(element.name)
            }
            heightNum = (pokemon!!.height * 0.1f)
            weightNum = (pokemon!!.weight * 0.1f)

            weight = "$weightNum kg"
            height = "$heightNum m"

            Log.d("detail", description)
            Log.d("detail", growthRate)
            Log.d("detail", habitat)
            Log.d("detail", weight)
            Log.d("detail", height)

            for(element in eggGroup) {
                Log.d("detail", element)
            }

//            println(pokemon!!.flavorTextEntries[10].flavorText)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var nameView:TextView = requireView().findViewById(R.id.pokemon_detail_view_name)
        nameView.text = this.name;




        return inflater.inflate(R.layout.fragment_pokemon_detail, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PokemonDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PokemonDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
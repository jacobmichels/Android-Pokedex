package com.cis4030.pokedex.ui.pokemon_detail

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.cis4030.pokedex.MainActivity
import com.cis4030.pokedex.R
import com.cis4030.pokedex.database.PokemonDatabase
import com.cis4030.pokedex.database.getDatabase
import com.cis4030.pokedex.repository.PokedexRepository
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import me.sargunvohra.lib.pokekotlin.model.Pokedex
import me.sargunvohra.lib.pokekotlin.model.Pokemon
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies

import kotlin.math.round
import kotlin.properties.Delegates


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
    private var elevation:Float? = null
    private var statusBarColorOriginal:Int = 0

    private var pokemonID: Int = 0
    private val idKey:String = "pokemonID"
    private var pokemonSpecies:PokemonSpecies? = null
    private var pokemon:Pokemon? = null



    private lateinit var name:String
    private var type:ArrayList<String> = arrayListOf()

    private lateinit var description:String
    private lateinit var growthRate:String
    private lateinit var habitat:String

    //in meters
    private lateinit var height:String

    //in kilograms
    private lateinit var weight:String

    private var eggGroup:ArrayList<String> = arrayListOf()
    private var heightNum:Float = 0.0f
    private var weightNum:Float = 0.0f

    private var primaryColor by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pokemonID = it.getInt(idKey)


//            pokeDB = getDatabase(requireContext())
//            println(pokeDB)
//
//            var thePokemon = pokeDB.pokemonDao.getPokemonByIdNum(pokemonID)
//
//            println("HERE:::" + thePokemon.toString())


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

//            type = pokemon!!.types[0].type.name
//            for(type: pokemon!!.types) {
//
//        }
            for(pokemonType in pokemon!!.types) {
                type.add(pokemonType.type.name)
            }

            description = pokemonSpecies!!.flavorTextEntries[10].flavorText
            description = description.replace("  ", "")

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
//        var nameView:TextView = requireView().findViewById(R.id.pokemon_detail_view_name)
//        nameView.text = this.name;

        return inflater.inflate(R.layout.fragment_pokemon_detail, container, false)
    }

    //when the view is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = this.name.capitalize()

        determinePrimaryColor()

        setPageColors(view)

        setButtonListeners(view)

        populateAboutSection(view)

        // populate the stats section

        // populate the moves section

        // populate the evolutions section
        initView(view)


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



    private fun initView(view: View) {
        //save the elevation
        this.elevation = (activity as MainActivity).supportActionBar?.elevation

        //set the elevation to 0 so there is no shadow
        (activity as MainActivity).supportActionBar?.elevation = 0.0f

        val about:ToggleButton = view.findViewById(R.id.about_button)
        val stats:ToggleButton = view.findViewById(R.id.stats_button)
        val moves:ToggleButton = view.findViewById(R.id.moves_button)
        val evo:ToggleButton = view.findViewById(R.id.evolutions_button)

        val aboutContent:LinearLayout = view.findViewById(R.id.about_container)
        val statsContent:LinearLayout = view.findViewById(R.id.stats_container)
        val movesContent:LinearLayout = view.findViewById(R.id.moves_container)
        val evoContent:LinearLayout = view.findViewById(R.id.evolution_container)


        //set the buttons
        about.isChecked = true
        about.isClickable = false

        stats.isChecked = false
        moves.isChecked = false
        evo.isChecked = false

        //set the view visibility
        aboutContent.visibility = View.VISIBLE
        statsContent.visibility = View.GONE
        movesContent.visibility = View.GONE
        evoContent.visibility = View.GONE
    }

    // set the listeners for the buttons
    private fun setButtonListeners(view: View) {
        val about:ToggleButton = view.findViewById(R.id.about_button)
        val stats:ToggleButton = view.findViewById(R.id.stats_button)
        val moves:ToggleButton = view.findViewById(R.id.moves_button)
        val evo:ToggleButton = view.findViewById(R.id.evolutions_button)

        val aboutContent:LinearLayout = view.findViewById(R.id.about_container)
        val statsContent:LinearLayout = view.findViewById(R.id.stats_container)
        val movesContent:LinearLayout = view.findViewById(R.id.moves_container)
        val evoContent:LinearLayout = view.findViewById(R.id.evolution_container)


        //set the listener for the about button
        about.setOnCheckedChangeListener{_, isChecked->

            if(isChecked) {

                stats.isChecked = false
                moves.isChecked = false
                evo.isChecked = false
                about.isClickable = false


                //make about content visible, hide the other stuff
                aboutContent.visibility = View.VISIBLE


            }

            else {
                about.isClickable = true
                aboutContent.visibility = View.GONE

            }

        }

        //set the stats button listener
        stats.setOnCheckedChangeListener{_, isChecked->

            if(isChecked) {
                about.isChecked = false
                moves.isChecked = false
                evo.isChecked = false
                stats.isClickable = false

                //make about content visible, hide the other stuff
                statsContent.visibility = View.VISIBLE
            }

            else {
                stats.isClickable = true
                statsContent.visibility = View.GONE

            }

        }


        //set the moves button listener
        moves.setOnCheckedChangeListener{_, isChecked->

            if(isChecked) {
                about.isChecked = false
                stats.isChecked = false
                evo.isChecked = false
                moves.isClickable = false


                movesContent.visibility = View.VISIBLE
            }

            else {
                moves.isClickable = true
                movesContent.visibility = View.GONE
            }

        }


        //set the evolution button listener
        evo.setOnCheckedChangeListener{_, isChecked->

            if(isChecked) {
                about.isChecked = false
                stats.isChecked = false
                moves.isChecked = false
                evo.isClickable = false

                evoContent.visibility = View.VISIBLE
            }

            else {
                evo.isClickable = true
                evoContent.visibility = View.GONE
            }

        }
        return
    }


    private fun populateAboutSection(view: View) {

        val aboutContent:LinearLayout = view.findViewById(R.id.about_container)
        val descriptionView:TextView = view.findViewById(R.id.description_content)
        val egg1: Button = view.findViewById(R.id.egg_group_1)
        val egg2: Button = view.findViewById(R.id.egg_group_2)

        //set the description content
        descriptionView.text = this.description

        //set the egg group section
        // set the

        egg1.text = eggGroup[0].capitalize()
        setEggGroupColor(eggGroup[0].toLowerCase(), egg1)

        if(eggGroup.size < 2) {
            egg2.visibility = View.INVISIBLE
        }
        else {
            egg2.text = eggGroup[1].capitalize()
            setEggGroupColor(eggGroup[1].toLowerCase(), egg2)
        }
        return
    }

    @SuppressLint("ResourceAsColor")
    private fun setEggGroupColor(egg_group:String, btn:Button) {

        if(egg_group == "monster") {
            btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_monster)
        }
        else if(egg_group =="water1") {
            btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_water1)
        }
        else if(egg_group =="bug") {
            btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_bug)
        }
        else if(egg_group =="flying") {
            btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_flying)
        }
        else if(egg_group =="ground") {
            btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_mineral)
        }
        else if(egg_group =="fairy") {
            btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_fairy)
        }
        else if(egg_group =="plant") {
            btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_grass)
        }
        else if(egg_group =="humanshape") {
            btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_human)
        }
        else if(egg_group =="water3") {
            btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_water3)
        }
        else if(egg_group == "mineral") {
            btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_mineral)
        }
        else if(egg_group == "indeterminate") {
            btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_gender_unknown)
        }
        else if(egg_group == "water2") {
            btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_water2)
        }
        else if(egg_group == "ditto") {
            btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_ditto)
        }
        else if(egg_group == "dragon") {
            btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_dragon)
        }
        else if(egg_group == "no-eggs") {
            btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_no_eggs_discovered)
        }
        return
    }

    private fun setPageColors(view:View) {

        val portrait:LinearLayout = view.findViewById(R.id.portrait_container)

        //save old action bar color

        //set the action bar color to the primary color
        (activity as MainActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(this.primaryColor))

        //set the portrait background
        portrait.setBackgroundColor(this.primaryColor)

        val window:Window = requireActivity().window

        //save the status bar color
        this.statusBarColorOriginal = window.statusBarColor

        //set the status bar color
        window.statusBarColor = this.primaryColor

        //set the button accent colors
//        view.findViewById<ToggleButton>(R.id.about_button).setBackgroundColor(this.primaryColor)
//        view.findViewById<ToggleButton>(R.id.stats_button).setBackgroundColor(this.primaryColor)
//        view.findViewById<ToggleButton>(R.id.moves_button).setBackgroundColor(this.primaryColor)
//        view.findViewById<ToggleButton>(R.id.evolutions_button).setBackgroundColor(this.primaryColor)
        return
    }

    private fun determinePrimaryColor() {

        //just look at the first type
        var firstType = this.type[0].toLowerCase()

        when (firstType) {
            "normal" -> {
                this.primaryColor = resources.getColor(R.color.normal, null)
            }
            "fighting" -> {
                this.primaryColor = resources.getColor(R.color.fighting, null)
            }
            "flying" -> {
                this.primaryColor = resources.getColor(R.color.flying, null)
            }
            "poison" -> {
                this.primaryColor = resources.getColor(R.color.poison, null)
            }
            "ground" -> {
                this.primaryColor = resources.getColor(R.color.ground, null)
            }
            "rock" -> {
                this.primaryColor = resources.getColor(R.color.rock, null)
            }
            "bug" -> {
                this.primaryColor = resources.getColor(R.color.bug, null)
            }
            "ghost" -> {
                this.primaryColor = resources.getColor(R.color.ghost, null)
            }
            "steel" -> {
                this.primaryColor = resources.getColor(R.color.steel, null)
            }
            "fire" -> {
                this.primaryColor = resources.getColor(R.color.fire, null)
            }
            "water" -> {
                this.primaryColor = resources.getColor(R.color.water, null)
            }
            "grass" -> {
                this.primaryColor = resources.getColor(R.color.grass, null)
            }
            "electric" -> {
                this.primaryColor = resources.getColor(R.color.electric, null)
            }
            "psychic" -> {
                this.primaryColor = resources.getColor(R.color.psychic, null)
            }
            "ice" -> {
                this.primaryColor = resources.getColor(R.color.ice, null)
            }
            "dragon" -> {
                this.primaryColor = resources.getColor(R.color.dragon, null)
            }
            "dark" -> {
                this.primaryColor = resources.getColor(R.color.dark, null)
            }
            "fairy" -> {
                this.primaryColor = resources.getColor(R.color.fairy, null)
            }
            "unknown" -> {
                this.primaryColor = resources.getColor(R.color.unknown, null)
            }
            "shadow" -> {
                this.primaryColor = resources.getColor(R.color.ghost, null)
            }
            else -> {
                this.primaryColor = resources.getColor(R.color.deep_red, null)
            }
        }

    }

    override fun onDestroyView() {

        super.onDestroyView()

        // change the title bar color back to its original red
        (activity as MainActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.deep_red,null)))

        //reset the elevation
        (activity as MainActivity).supportActionBar?.elevation = this.elevation!!

        //change the status bar back to normal
        requireActivity().window.statusBarColor = this.statusBarColorOriginal

    }

}



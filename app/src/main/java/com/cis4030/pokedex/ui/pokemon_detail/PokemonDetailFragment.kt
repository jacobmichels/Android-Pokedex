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
import kotlin.math.roundToInt
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
    private var primaryColor by Delegates.notNull<Int>()


    private var pokemonID: Int = 0
    private val idKey:String = "pokemonID"
    private var pokemonSpecies:PokemonSpecies? = null
    private var pokemon:Pokemon? = null



    private lateinit var name:String
    private var type:ArrayList<String> = arrayListOf()

    private lateinit var description:String
    private lateinit var growthRate:String
    private lateinit var habitat:String
    private lateinit var height:String //in meters
    private lateinit var weight:String //in kilograms
    private var eggGroup:ArrayList<String> = arrayListOf()
    private var heightNum:Float = 0.0f
    private var weightNum:Float = 0.0f

    //pokemon stats
    private var hp:Int = 0
    private var atk:Int = 0
    private var def:Int = 0
    private var sp_atk:Int = 0
    private var sp_def:Int = 0
    private var spd:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


            pokemonID = it.getInt(idKey)

            //need this to make the api calls
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)

            val pokeApi = PokeApiClient()
            pokemonSpecies = pokeApi.getPokemonSpecies(pokemonID)
            pokemon = pokeApi.getPokemon(pokemonID)


            // need to know what generations the pokemon is in, 10 is FireRed description but it wont work for all of them.
            name = pokemonSpecies!!.name

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

            //get the data for the stats screen
            retrievePokemonStats()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pokemon_detail, container, false)
    }

    //when the view is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        //set the title on teh action bar
        (activity as MainActivity).supportActionBar?.title = this.name.capitalize()

        determinePrimaryColor()

        setPageColors(view)

        setButtonListeners(view)

        populateAboutSection(view)

        populateStatsSection(view)

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

        val descriptionView:TextView = view.findViewById(R.id.description_content)
        val egg1: Button = view.findViewById(R.id.egg_group_1)
        val egg2: Button = view.findViewById(R.id.egg_group_2)
        val heightContent:TextView = view.findViewById(R.id.height_content)
        val weightContent:TextView = view.findViewById(R.id.weight_content)
        val habitatContent:TextView = view.findViewById(R.id.habitat_content)
        val growthContent:TextView = view.findViewById(R.id.growth_content)


        //set the description content
        descriptionView.text = this.description

        //set the egg group sections
        egg1.text = eggGroup[0].capitalize()
        setEggGroupColor(eggGroup[0].toLowerCase(), egg1)

        if(eggGroup.size < 2) {
            egg2.visibility = View.INVISIBLE
        }
        else {
            egg2.text = eggGroup[1].capitalize()
            setEggGroupColor(eggGroup[1].toLowerCase(), egg2)
        }

        //set the height section
        heightContent.text = this.height

        //set the weight section
        weightContent.text = this.weight

        //set the habitat section
        habitatContent.text = this.habitat.capitalize()

        //set the growth rate section
        growthContent.text = this.growthRate.capitalize()

        return
    }

    private fun populateStatsSection(view: View) {
        val hpBar: Button = view.findViewById(R.id.hp_bar)
        val atkBar: Button = view.findViewById(R.id.atk_bar)
        val defBar: Button = view.findViewById(R.id.def_bar)
        val spAtkBar: Button = view.findViewById(R.id.sp_atk_bar)
        val spDefBar: Button = view.findViewById(R.id.sp_def_bar)
        val spdBar: Button = view.findViewById(R.id.spd_bar)
        val scale:Float = requireContext().resources.displayMetrics.density

        val maxBar:Int = 220
        val minBar:Int = 55

        //setup the hp bar
        var hpSize:Int = (maxBar * (this.hp*0.01)).roundToInt()
        if(hpSize > maxBar) {
            hpSize = maxBar
        }
        else if (hpSize < minBar) {
            hpSize = minBar
        }
        hpBar.text = this.hp.toString()
        hpBar.layoutParams.width = (hpSize * scale + 0.5f).toInt();

        //setup the atk bar
        var atkSize:Int = (maxBar * (this.atk*0.01)).roundToInt()
        if(atkSize > maxBar) {
            atkSize = maxBar
        }
        else if (atkSize < minBar) {
            atkSize = minBar
        }
        atkBar.text = this.atk.toString()
        atkBar.layoutParams.width = (atkSize * scale + 0.5f).toInt();

        //setup the def bar
        var defSize:Int = (maxBar * (this.def*0.01)).roundToInt()
        if(defSize > maxBar) {
            defSize = maxBar
        }
        else if (defSize < minBar) {
            defSize = minBar
        }
        defBar.text = this.def.toString()
        defBar.layoutParams.width = (defSize * scale + 0.5f).toInt();

        //setup the atk bar
        var spAtkSize:Int = (maxBar * (this.sp_atk*0.01)).roundToInt()
        if(spAtkSize > maxBar) {
            spAtkSize = maxBar
        }
        else if (spAtkSize < minBar) {
            spAtkSize = minBar
        }
        spAtkBar.text = this.sp_atk.toString()
        spAtkBar.layoutParams.width = (spAtkSize * scale + 0.5f).toInt();

        //setup the def bar
        var spDefSize:Int = (maxBar * (this.sp_def*0.01)).roundToInt()
        if(spDefSize > maxBar) {
            spDefSize = maxBar
        }
        else if (spDefSize < minBar) {
            spDefSize = minBar
        }
        spDefBar.text = this.sp_def.toString()
        spDefBar.layoutParams.width = (spDefSize * scale + 0.5f).toInt();

        //setup the def bar
        var spdSize:Int = (maxBar * (this.spd*0.01)).roundToInt()
        if(spdSize > maxBar) {
            spdSize = maxBar
        }
        else if (spdSize < minBar) {
            spdSize = minBar
        }
        spdBar.text = this.spd.toString()
        spdBar.layoutParams.width = (spdSize * scale + 0.5f).toInt()

        return
    }

    @SuppressLint("ResourceAsColor")
    private fun setEggGroupColor(egg_group:String, btn:Button) {

        when (egg_group) {
            "monster" -> {
                btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_monster)
            }
            "water1" -> {
                btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_water1)
            }
            "bug" -> {
                btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_bug)
            }
            "flying" -> {
                btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_flying)
            }
            "ground" -> {
                btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_mineral)
            }
            "fairy" -> {
                btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_fairy)
            }
            "plant" -> {
                btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_grass)
            }
            "humanshape" -> {
                btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_human)
            }
            "water3" -> {
                btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_water3)
            }
            "mineral" -> {
                btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_mineral)
            }
            "indeterminate" -> {
                btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_gender_unknown)
            }
            "water2" -> {
                btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_water2)
            }
            "ditto" -> {
                btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_ditto)
            }
            "dragon" -> {
                btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_dragon)
            }
            "no-eggs" -> {
                btn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.egg_no_eggs_discovered)
            }
        }
        return
    }

    private fun setPageColors(view:View) {

        val portrait:LinearLayout = view.findViewById(R.id.portrait_container)

        // stat "bars"
        val hpBar: Button = view.findViewById(R.id.hp_bar)
        val atkBar: Button = view.findViewById(R.id.atk_bar)
        val defBar: Button = view.findViewById(R.id.def_bar)
        val spAtkBar: Button = view.findViewById(R.id.sp_atk_bar)
        val spDefBar: Button = view.findViewById(R.id.sp_def_bar)
        val spdBar: Button = view.findViewById(R.id.spd_bar)

        //set the action bar color to the primary color
        (activity as MainActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(this.primaryColor))

        //set the portrait background
        portrait.setBackgroundColor(this.primaryColor)

        //save the status bar color
        val window:Window = requireActivity().window
        this.statusBarColorOriginal = window.statusBarColor

        //set the status bar color
        window.statusBarColor = this.primaryColor

        //set stat bar colors
        hpBar.background.setTint(this.primaryColor)
        atkBar.background.setTint(this.primaryColor)
        defBar.background.setTint(this.primaryColor)
        spAtkBar.background.setTint(this.primaryColor)
        spDefBar.background.setTint(this.primaryColor)
        spdBar.background.setTint(this.primaryColor)

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

    // populates gets the data required for the stats page
    private fun retrievePokemonStats() {

        // get the hp stat
        this.hp = pokemon!!.stats[0].baseStat

        // get the attack stat
        this.atk = pokemon!!.stats[1].baseStat

        // get the defense stat
        this.def = pokemon!!.stats[2].baseStat

        // get the sp attack stat
        this.sp_atk = pokemon!!.stats[3].baseStat

        // get the sp def stat
        this.sp_def = pokemon!!.stats[4].baseStat

        // get the speed stat
        this.spd = pokemon!!.stats[5].baseStat

        return
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



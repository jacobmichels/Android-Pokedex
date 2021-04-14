package com.cis4030.pokedex.ui.pokemon_detail

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.text.Layout
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.cis4030.pokedex.MainActivity
import com.cis4030.pokedex.R
import com.cis4030.pokedex.database.PokemonDatabase
import com.cis4030.pokedex.database.getDatabase
import com.cis4030.pokedex.repository.PokedexRepository
import com.squareup.picasso.Picasso
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import me.sargunvohra.lib.pokekotlin.model.*

import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.properties.Delegates

import kotlinx.coroutines.*
import java.io.InterruptedIOException
import java.lang.Exception
import java.lang.Runnable
import java.lang.Thread
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread


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

    // 0 = move name  1 = move description  2 = damage type
    private var moves:ArrayList<ArrayList<String>> = arrayListOf()

    //pokemon stats
    private var hp:Int = 0
    private var atk:Int = 0
    private var def:Int = 0
    private var sp_atk:Int = 0
    private var sp_def:Int = 0
    private var spd:Int = 0

    private var movesLoaded:Boolean = false

    // 0 = evolution name 1 = pokemon id  2 = min level
    private var evolutions:ArrayList<ArrayList<String>> = arrayListOf()

    private lateinit var pokeApi:PokeApiClient

    private var aboutThread:Thread? = null

    private var myThread:Thread? = null

    private var statsThread:Thread? = null

    private lateinit var currentView:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


            pokemonID = it.getInt(idKey)

            //need this to make the api calls
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)

            this.pokeApi = PokeApiClient()
            this.pokemonSpecies = pokeApi.getPokemonSpecies(pokemonID)
            this.pokemon = pokeApi.getPokemon(pokemonID)


            name = pokemonSpecies!!.name

            for(pokemonType in pokemon!!.types) {
                type.add(pokemonType.type.name)
            }



            //get the data for the stats screen
//            retrievePokemonStats()

            //get the move list for the pokemon
//            retrieveMoveList()

            //get the evolution data, (levels req and evolution stages)
//            getEvolutionChain()

            //https://pokeres.bastionbot.org/images/pokemon/1.png example
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_pokemon_detail, container, false)
    }

    //when the view is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        this.currentView = view

        //set the title on teh action bar
        (activity as MainActivity).supportActionBar?.title = this.name.capitalize()

        //set the pokemon portrait
        setPortrait(pokemonID, view)

        determinePrimaryColor()

        setPageColors(view)

        setButtonListeners(view)

        aboutThread = AboutThread()
        aboutThread!!.start()

        statsThread = StatsThread()
        statsThread!!.start()

        myThread = MovesThread()
        myThread!!.start()

        // populate the evolutions section
        populateEvolutionSection()


        initView(view)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                findNavController().navigateUp()
            }
        }
        return true
    }

    private fun getAboutSectionData() {
        // need to know what generations the pokemon is in, 10 is FireRed description but it wont work for all of them.
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
    }
    inner class AboutThread(): Thread() {
        override fun run() {
            try {

                getAboutSectionData()

                requireActivity().runOnUiThread(java.lang.Runnable {
                    populateAboutSection()
                })

            } catch(e:InterruptedException) {
                Log.d("AboutThread()", e.toString())

            } catch (eio:InterruptedIOException) {
                Log.d("AboutThread()", eio.toString())
            }
        }
    }

    inner class StatsThread(): Thread(){

        override fun run() {
            try {

                retrievePokemonStats()

                requireActivity().runOnUiThread(java.lang.Runnable {
                    populateStatsSection()
                })

            } catch(e:InterruptedException) {
                Log.d("StatsThread()", e.toString())

            } catch (eio:InterruptedIOException) {
                Log.d("StatsThread()", eio.toString())
            }
        }
    }

    inner class MovesThread(): Thread() {

        override fun run() {
            try {
                populateMovesSection()
            } catch(e:InterruptedException) {
                Log.d("MovesThread()", e.toString())

            } catch (eio:InterruptedIOException) {
                Log.d("MovesThread()", eio.toString())
            }
        }
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
                if(!movesLoaded) {

                    movesLoaded= true
                }


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



    private fun populateAboutSection() {

        val view:View = this.currentView
        val descriptionView:TextView = view.findViewById(R.id.description_content)
        val egg1: Button = view.findViewById(R.id.egg_group_1)
        val egg2: Button = view.findViewById(R.id.egg_group_2)
        val type1: Button = view.findViewById(R.id.type_1)
        val type2: Button = view.findViewById(R.id.type_2)
        val heightContent:TextView = view.findViewById(R.id.height_content)
        val weightContent:TextView = view.findViewById(R.id.weight_content)
        val habitatContent:TextView = view.findViewById(R.id.habitat_content)
        val growthContent:TextView = view.findViewById(R.id.growth_content)


        //set the description content
        descriptionView.text = this.description

        //set the type section
        type1.text = this.type[0]
        setMoveDamageColor(type1, this.type[0])
        if (this.type.size > 1) {
            type2.text = this.type[1]
            setMoveDamageColor(type2, this.type[1])
        } else {
            type2.visibility = View.INVISIBLE
        }

//        set the type colors.


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

    private fun populateStatsSection() {

        val view = this.currentView

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

    private fun setMoveDamageColor(btn:Button, type:String) {

        when (type.toLowerCase()) {
            "normal" -> {
                btn.background.setTint(resources.getColor(R.color.normal, null))
            }
            "fighting" -> {
                btn.background.setTint(resources.getColor(R.color.fighting, null))
            }
            "flying" -> {
                btn.background.setTint(resources.getColor(R.color.flying, null))

            }
            "poison" -> {
                btn.background.setTint(resources.getColor(R.color.poison, null))
            }
            "ground" -> {
                btn.background.setTint(resources.getColor(R.color.ground, null))
            }
            "rock" -> {
                btn.background.setTint(resources.getColor(R.color.rock, null))
            }
            "bug" -> {
                btn.background.setTint(resources.getColor(R.color.bug, null))
            }
            "ghost" -> {
                btn.background.setTint(resources.getColor(R.color.ghost, null))
            }
            "steel" -> {
                btn.background.setTint(resources.getColor(R.color.steel, null))
            }
            "fire" -> {
                btn.background.setTint(resources.getColor(R.color.fire, null))
            }
            "water" -> {
                btn.background.setTint(resources.getColor(R.color.water, null))
            }
            "grass" -> {
                btn.background.setTint(resources.getColor(R.color.grass, null))
            }
            "electric" -> {
                btn.background.setTint(resources.getColor(R.color.electric, null))
            }
            "psychic" -> {
                btn.background.setTint(resources.getColor(R.color.psychic, null))
            }
            "ice" -> {
                btn.background.setTint(resources.getColor(R.color.ice, null))
            }
            "dragon" -> {
                btn.background.setTint(resources.getColor(R.color.dragon, null))
            }
            "dark" -> {
                btn.background.setTint(resources.getColor(R.color.dark, null))
            }
            "fairy" -> {
                btn.background.setTint(resources.getColor(R.color.fairy, null))
            }
            "unknown" -> {
                btn.background.setTint(resources.getColor(R.color.unknown, null))
            }
            "shadow" -> {
                btn.background.setTint(resources.getColor(R.color.ghost, null))
            }
            else -> {
                btn.background.setTint(resources.getColor(R.color.deep_red, null))
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

    private fun retrieveMoveList() {
        val moveList:List<PokemonMove> = pokemon!!.moves

        for(move in moveList) {

            //only add moves that are the same as this pokemon's type


            var moveEntry:ArrayList<String> = arrayListOf()

            //name the name first

            var rawName = move.move.name.replace("-"," ")

            //Capitalize every word
            rawName = rawName.toLowerCase().split(" ").joinToString(" ") { it.capitalize() }

            // get the move id num
            var urlSplit:List<String> = move.move.url.split("/")
            val moveNum:Int = urlSplit[6].toInt()
            var theMove: Move = pokeApi.getMove(moveNum)
            //for((counter, item) in urlSplit.withIndex()) {
            //    println("$counter $item")
            //}

            //get the move description
            var descriptionRaw:String = theMove.effectEntries[0].effect
            descriptionRaw = descriptionRaw.replace("\$effect_chance%", "")
            descriptionRaw = descriptionRaw.replace("  ", " ")

            if((theMove.type.name !in type)) {
                continue
            }


            moveEntry.add(rawName)
            moveEntry.add(descriptionRaw)
            moveEntry.add(theMove.type.name)
            this.moves.add(moveEntry)
        }
    }

    override fun onDestroyView() {

        super.onDestroyView()

        // change the title bar color back to its original red
        (activity as MainActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                resources.getColor(
                    R.color.deep_red,
                    null
                )
            )
        )

        //reset the elevation
        (activity as MainActivity).supportActionBar?.elevation = this.elevation!!

        //change the status bar back to normal
        requireActivity().window.statusBarColor = this.statusBarColorOriginal

        // kill the threads
        aboutThread?.interrupt()
        statsThread?.interrupt()
        myThread?.interrupt()

    }

    //co routine bc its so slow
    private fun populateMovesSection() {

        retrieveMoveList()

        val view:View = this.currentView


        requireActivity().runOnUiThread(java.lang.Runnable {
            val movesSection: LinearLayout = view.findViewById(R.id.moves_container)
            val scale: Float = requireContext().resources.displayMetrics.density

            for (move in this.moves) {

                //create the sub container for the move title, button, and description
                val oneMove = LinearLayout(activity)
                val oneMoveId: Int = View.generateViewId()
                oneMove.id = oneMoveId
                movesSection.addView(oneMove)
                val subContainer: LinearLayout = view.findViewById<LinearLayout>(oneMoveId)
                subContainer.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                subContainer.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                subContainer.setPadding(0, (40 * scale + 0.5f).toInt(), 0, 0)
                subContainer.orientation = LinearLayout.VERTICAL

                //create the title and button container
                val titleTypeContainer = LinearLayout(activity)
                val titleTypeId: Int = View.generateViewId()
                titleTypeContainer.id = titleTypeId
                subContainer.addView(titleTypeContainer)
                titleTypeContainer.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                titleTypeContainer.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                titleTypeContainer.orientation = LinearLayout.HORIZONTAL

                //create the title and add it to the titleType container
                val moveName = TextView(activity)
                val moveNameId: Int = View.generateViewId()
                moveName.id = moveNameId
                titleTypeContainer.addView(moveName)
                val mName: TextView = view.findViewById(moveNameId)
                mName.text = move[0]
                mName.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                mName.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                mName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
                mName.setTextColor(Color.BLACK)

                // create the space item and add it to the
                val moveSpace = Space(activity)
                val moveSpaceId: Int = View.generateViewId()
                moveSpace.id = moveSpaceId
                titleTypeContainer.addView(moveSpace)
                val space: Space = view.findViewById(moveSpaceId)
                space.layoutParams.width = (20 * scale + 0.5f).toInt();
                space.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

                // create the damage type graphic
                val dmgType = Button(activity)
                val dmgTypeId: Int = View.generateViewId()
                dmgType.id = dmgTypeId
                titleTypeContainer.addView(dmgType)
                val dmg: Button = view.findViewById(dmgTypeId)


                //change the corner radius
                var gd: GradientDrawable = GradientDrawable()
                gd.setColor(Color.BLUE)
                gd.cornerRadius = (25 * scale + 0.5f);
                dmg.background = (gd)

                dmg.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                dmg.layoutParams.height = (35 * scale + 0.5f).toInt();
                dmg.text = move[2]
                dmg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                dmg.setTextColor(Color.WHITE)
                dmg.minHeight = 0
                dmg.minWidth = 0
                dmg.letterSpacing = 0F
                dmg.setPadding(0)
                dmg.isClickable = false
                dmg.textAlignment = ViewGroup.TEXT_ALIGNMENT_CENTER
                setMoveDamageColor(dmg, move[2])

                //Add the description
                val moveDescription = TextView(activity)
                val moveDescriptionId = View.generateViewId()
                moveDescription.id = moveDescriptionId
                subContainer.addView(moveDescription)

                val desc: TextView = view.findViewById(moveDescriptionId)
                desc.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                desc.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                desc.text = move[1]
            }
        })
    }

    private fun setPortrait(id:Int, view:View) {
        val portrait = view.findViewById<ImageView>(R.id.pokemon_portrait)


//        portrait.setBa
        Picasso.get().load("https://pokeres.bastionbot.org/images/pokemon/$id.png").into(portrait)

        //https://pokeres.bastionbot.org/images/pokemon/1.png example
    }

    private fun getEvolutionChain() {

        var urlSplit:List<String> = pokemonSpecies!!.evolutionChain.url.split("/")
        val evoIndex:Int = urlSplit[6].toInt()
        val evo:EvolutionChain =  pokeApi.getEvolutionChain(evoIndex)
        var current:ChainLink = evo.chain

        //set the first array
        evolutions.add(arrayListOf())
        evolutions[0].add(current.species.name)
        var pkmnUrl:List<String> = current.species.url.split("/")
        evolutions[0].add(pkmnUrl[6]) // get the pokemon id
        evolutions[0].add("0")

        var i:Int = 1
        while(current.evolvesTo.isNotEmpty()) {
            current = current.evolvesTo[0]
            evolutions.add(arrayListOf())
            evolutions[i].add(current.species.name) // get the name
            var speciesUrl:List<String> = current.species.url.split("/")
            evolutions[i].add(speciesUrl[6]) // get the pokemon id

            var minLevel:String = current.evolutionDetails[0].minLevel.toString()
            if(minLevel == "null") {
                minLevel = "0"
            }
            evolutions[i].add(minLevel)
            i++
        }

//        check if data is correct
        for(item in evolutions) {
            for(str in item) {
                Log.d("evolution",str)
            }
        }
    }

    private fun populateEvolutionSection() {
        getEvolutionChain()


        val view:View = this.currentView
        //threading...
        //requireActivity().runOnUiThread(java.lang.Runnable {

        val evoSection: LinearLayout = view.findViewById(R.id.evolution_container)
        val scale: Float = requireContext().resources.displayMetrics.density
        val pokemonWidth:Int = 500

        Log.d("evolution","starting")

        var notFirst:Boolean = false
        for (evolution in this.evolutions) {


            if (notFirst) {

                //add small space
                var titleSpace = Space(activity)
                val titleSpID = View.generateViewId()
                titleSpace.id = titleSpID
                evoSection.addView(titleSpace)
                titleSpace = view.findViewById(titleSpID)
                titleSpace.layoutParams.height = (20 * scale + 0.5f).toInt()
                titleSpace.layoutParams.width = (20 * scale + 0.5f).toInt()


                var levelReq = LinearLayout (activity)
                val levelReqId = View.generateViewId()
                levelReq.id = levelReqId
                evoSection.addView(levelReq)
                levelReq = view.findViewById(levelReqId)
                levelReq.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                levelReq.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                levelReq.gravity = Gravity.CENTER_HORIZONTAL
                levelReq.orientation = LinearLayout.VERTICAL

                val minLvl:String = evolution[2]
                if (minLvl != "0") {
                    var minLevel = TextView(activity)
                    val minLvlId:Int = View.generateViewId()
                    minLevel.id = minLvlId
                    levelReq.addView(minLevel)
                    minLevel = view.findViewById(minLvlId)
                    val minimum:String = evolution[2]
                    minLevel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25F)
                    minLevel.setTextColor(Color.DKGRAY)
                    minLevel.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    minLevel.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                    minLevel.text = "Minimum Level: $minimum"
                    minLevel.gravity = Gravity.CENTER_HORIZONTAL
                }

                var downArrow = ImageView(activity)
                val arrowId = View.generateViewId()
                downArrow.id = arrowId
                levelReq.addView(downArrow)
                downArrow = view.findViewById(arrowId)

                Picasso.get().load(R.drawable.down_arrow).into(downArrow)

                downArrow.layoutParams.height = (100 * scale + 0.5f).toInt()
                downArrow.layoutParams.width = (200 * scale + 0.5f).toInt()
                downArrow.setColorFilter(Color.DKGRAY)
            }
            notFirst = true


            //add a space here
            var formatSpace = Space(activity)
            val formatID = View.generateViewId()
            formatSpace.id = formatID
            evoSection.addView(formatSpace)
            formatSpace = view.findViewById(formatID)
            formatSpace.layoutParams.height = (20 * scale + 0.5f).toInt()
            formatSpace.layoutParams.width = (20 * scale + 0.5f).toInt()


            var oneEvolution = LinearLayout(activity)
            val oneEvoId:Int = View.generateViewId()
            oneEvolution.id = oneEvoId
            evoSection.addView(oneEvolution)

            oneEvolution = view.findViewById(oneEvoId)
            oneEvolution.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            oneEvolution.layoutParams.width = (300 * scale + 0.5f).toInt();
            oneEvolution.orientation = LinearLayout.VERTICAL

            var gd: GradientDrawable = GradientDrawable()
            gd.setColor(this.primaryColor)
            gd.cornerRadius = (30 * scale + 0.5f);
            oneEvolution.background = gd
            oneEvolution.gravity = Gravity.CENTER_HORIZONTAL

            //add the name tag
            var evoName = TextView(activity)
            val evoId:Int = View.generateViewId()
            evoName.id = evoId
            oneEvolution.addView(evoName)
            evoName = view.findViewById(evoId)

            evoName.text = evolution[0]
            evoName.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            evoName.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            evoName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            evoName.setTextColor(Color.WHITE)

            // add the picture
            var portrait = ImageView(activity)
            val portraitId:Int = View.generateViewId()
            portrait.id = portraitId
            oneEvolution.addView(portrait)
            portrait = view.findViewById(portraitId)

            val pkmID:String = evolution[1]
            Picasso.get().load("https://pokeres.bastionbot.org/images/pokemon/$pkmID.png").into(portrait)
            portrait.layoutParams.width  = (200 * scale + 0.5f).toInt();
            portrait.layoutParams.height  = (200 * scale + 0.5f).toInt();

            Log.d("evolution","completed one of them")
        }

        Log.d("evolution","completed all")
    }
}



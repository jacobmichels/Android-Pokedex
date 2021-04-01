package com.cis4030.pokedex.ui.pokedex_create

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cis4030.pokedex.R
import com.cis4030.pokedex.database.DatabaseCustomPokemon
import com.cis4030.pokedex.database.DatabasePokemon
import com.cis4030.pokedex.databinding.FragmentCreatePokemonBinding
import com.cis4030.pokedex.util.bitmapToByteArray
import com.cis4030.pokedex.util.getBitmapFromUri
import com.cis4030.pokedex.viewmodels.CreatePokemonViewModel
import java.lang.NumberFormatException


class CreatePokemonFragment() :Fragment() {

    val viewModel: CreatePokemonViewModel by activityViewModels()
    lateinit var binding:FragmentCreatePokemonBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatePokemonBinding.inflate(inflater)
        binding.lifecycleOwner=this
        binding.viewModel=viewModel


        val seekbarCallback = SeekbarChangeCallback(viewModel)
        binding.hpSeekbar.setOnSeekBarChangeListener(seekbarCallback)
        binding.atkSeekbar.setOnSeekBarChangeListener(seekbarCallback)
        binding.defSeekbar.setOnSeekBarChangeListener(seekbarCallback)
        binding.spAtkSeekbar.setOnSeekBarChangeListener(seekbarCallback)
        binding.spDefSeekbar.setOnSeekBarChangeListener(seekbarCallback)
        binding.spdSeekbar.setOnSeekBarChangeListener(seekbarCallback)

        viewModel.updateHP.observe(viewLifecycleOwner){
            if(it == true){
                binding.hpText.text=SpannableStringBuilder(viewModel.hp.toString())
                viewModel.textUpdated("hp")
            }
        }
        viewModel.updateATK.observe(viewLifecycleOwner){
            if(it == true){
                binding.atkText.text=SpannableStringBuilder(viewModel.atk.toString())
                viewModel.textUpdated("atk")
            }
        }
        viewModel.updateDEF.observe(viewLifecycleOwner){
            if(it == true){
                binding.defText.text=SpannableStringBuilder(viewModel.def.toString())
                viewModel.textUpdated("def")
            }
        }
        viewModel.updateSPATK.observe(viewLifecycleOwner){
            if(it == true){
                binding.spAtkText.text=SpannableStringBuilder(viewModel.spAtk.toString())
                viewModel.textUpdated("sp_atk")
            }
        }
        viewModel.updateSPDEF.observe(viewLifecycleOwner){
            if(it == true){
                binding.spDefText.text=SpannableStringBuilder(viewModel.spDef.toString())
                viewModel.textUpdated("sp_def")
            }
        }
        viewModel.updateSPD.observe(viewLifecycleOwner){
            if(it == true){
                binding.spdText.text=SpannableStringBuilder(viewModel.spd.toString())
                viewModel.textUpdated("spd")
            }
        }

        binding.hpText.addTextChangedListener {
            val num: Int = try{
                it.toString().toInt()
            } catch (e: NumberFormatException){
                5
            }

            if(num>255){
                binding.hpText.text=SpannableStringBuilder("255")
                binding.hpSeekbar.progress=255
            }
            else if(num<5){
                binding.hpText.text=SpannableStringBuilder("5")
                binding.hpSeekbar.progress=5
            }
            else{
                binding.hpSeekbar.progress=num
            }
        }
        binding.atkText.addTextChangedListener {
            val num: Int = try{
                it.toString().toInt()
            } catch (e: NumberFormatException){
                5
            }

            if(num>255){
                binding.atkText.text=SpannableStringBuilder("255")
                binding.atkSeekbar.progress=255
            }
            else if(num<5){
                binding.atkText.text=SpannableStringBuilder("5")
                binding.atkSeekbar.progress=5
            }
            else{
                binding.atkSeekbar.progress=num
            }
        }
        binding.defText.addTextChangedListener {
            val num: Int = try{
                it.toString().toInt()
            } catch (e: NumberFormatException){
                5
            }

            if(num>255){
                binding.defText.text=SpannableStringBuilder("255")
                binding.defSeekbar.progress=255
            }
            else if(num<5){
                binding.defText.text=SpannableStringBuilder("5")
                binding.defSeekbar.progress=5
            }
            else{
                binding.defSeekbar.progress=num
            }
        }
        binding.spAtkText.addTextChangedListener {
            val num: Int = try{
                it.toString().toInt()
            } catch (e: NumberFormatException){
                5
            }

            if(num>255){
                binding.spAtkText.text=SpannableStringBuilder("255")
                binding.spAtkSeekbar.progress=255
            }
            else if(num<5){
                binding.spAtkText.text=SpannableStringBuilder("5")
                binding.spAtkSeekbar.progress=5
            }
            else{
                binding.spAtkSeekbar.progress=num
            }
        }
        binding.spDefText.addTextChangedListener {
            val num: Int = try{
                it.toString().toInt()
            } catch (e: NumberFormatException){
                5
            }

            if(num>255){
                binding.spDefText.text=SpannableStringBuilder("255")
                binding.spDefSeekbar.progress=255
            }
            else if(num<5){
                binding.spDefText.text=SpannableStringBuilder("5")
                binding.spDefSeekbar.progress=5
            }
            else{
                binding.spDefSeekbar.progress=num
            }
        }

        binding.spdText.addTextChangedListener {
            val num: Int = try{
                it.toString().toInt()
            } catch (e: NumberFormatException){
                5
            }

            if(num>255){
                binding.spdText.text=SpannableStringBuilder("255")
                binding.spdSeekbar.progress=255
            }
            else if(num<5){
                binding.spdText.text=SpannableStringBuilder("5")
                binding.spdSeekbar.progress=5
            }
            else{
                binding.spdSeekbar.progress=num
            }
        }

        binding.nameText.addTextChangedListener{
            viewModel.name=it.toString()
            Log.d("POKEDEX","${viewModel.name}")
        }

        val spinnerCallback = SpinnerSelectionCallback(viewModel)
        binding.type1Spinner.onItemSelectedListener = spinnerCallback
        binding.type2Spinner.onItemSelectedListener=spinnerCallback

        viewModel.enableType2Spinner.observe(viewLifecycleOwner){
            if(it==true){
                binding.type2Spinner.isEnabled=true
            }
        }

        binding.descriptionText.addTextChangedListener{
            viewModel.description=it.toString()
        }

        binding.selectImageButton.setOnClickListener{
            selectImage()
        }

        viewModel.updateMoveList.observe(viewLifecycleOwner){
            if(it==true){
                val adapter = binding.customPokemonMovelist.adapter as CreatePokemonMovelistAdapter
                adapter.moves=viewModel.moveList
                adapter.notifyDataSetChanged()
                viewModel.moveListUpdated()
                Log.d("POKDEX","updated move list. length: ${adapter.moves.size}")
                binding.addMoveButton.isEnabled = adapter.moves.size != 4
            }
        }

        val spinner1: Spinner = binding.type1Spinner
        val spinner2: Spinner = binding.type2Spinner

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.pokemon_types,
            android.R.layout.simple_spinner_item
        ).also{ arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner1.adapter=arrayAdapter
            spinner2.adapter=arrayAdapter
            spinner2.isEnabled=false
        }

        binding.addMoveButton.setOnClickListener{
            val dialog = AddMoveDialogFragment()
            dialog.isCancelable=false
            dialog.show(parentFragmentManager,"Add Move Dialog")
        }

        (activity as AppCompatActivity?)?.supportActionBar?.title="Create a Pokemon"



        binding.customPokemonMovelist.adapter=CreatePokemonMovelistAdapter(viewModel)

        val listAdapater = binding.customPokemonMovelist.adapter as CreatePokemonMovelistAdapter
        listAdapater.moves = viewModel.moveList

        binding.doneButton.setOnClickListener{
            if(binding.nameText.text.isNullOrBlank()){
                Toast.makeText(context,"Pokemon needs a name!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(binding.descriptionText.text.isNullOrBlank()){
                Toast.makeText(context,"Pokemon needs a description.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(binding.type1Spinner.selectedItemPosition==0){
                Toast.makeText(context,"Pokemon needs a first type.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(binding.type1Spinner.selectedItemPosition==binding.type2Spinner.selectedItemPosition){
                Toast.makeText(context,"Pokemon types cannot be the same.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(viewModel.moveList.size==0){
                Toast.makeText(context,"Pokemon needs at least 1 move.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(viewModel.moveList.size>4){
                Toast.makeText(context,"Pokemon have a max of 4 moves.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(viewModel.customPokemon.any { it.name==viewModel.name }){
                Toast.makeText(context,"Custom Pokemon with same name found, please use a unique name.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val pokemon= DatabaseCustomPokemon(
                name = binding.nameText.text.toString(),
                description = binding.descriptionText.text.toString(),
                movesList = viewModel.moveList.map{
                    it.name
                },
                baseHP = viewModel.hp,
                baseAtk = viewModel.atk,
                baseDfn = viewModel.def,
                spAtk = viewModel.spAtk,
                spDfn = viewModel.spDef,
                speed = viewModel.spd,
                type1 = viewModel.type1,
                type2 = viewModel.type2,
                moves = viewModel.moveList.map{
                    it.name
                }
            )
            viewModel.insertCustomPokemon(pokemon)
            viewModel.insertCustomMoves()
            val bytes = bitmapToByteArray(viewModel.imageBitmap)
            context?.openFileOutput(viewModel.name, Context.MODE_PRIVATE).use{
                it?.write(bytes)
            }
            Toast.makeText(context, "Created Pokemon successfully!", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }



        setHasOptionsMenu(true)
        return binding.root
    }

    private fun selectImage(){
        val intent = Intent()
        intent.type="image/*"
        intent.action=Intent.ACTION_GET_CONTENT

        startActivityForResult(Intent.createChooser(intent,"Select picture"),200)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK){
            if(requestCode==200){
                val selectedImageUri = data?.data
                if(selectedImageUri!=null){
                    Log.d("POKEDEX","$selectedImageUri")
                    val bitmap = getBitmapFromUri(requireContext(),selectedImageUri)
                    viewModel.imageBitmap = bitmap!!
                    binding.pokemonImage.setImageBitmap(bitmap)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home->{
                activity?.onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onDestroyView() {
        viewModel.moveList.clear()
        super.onDestroyView()
    }
}
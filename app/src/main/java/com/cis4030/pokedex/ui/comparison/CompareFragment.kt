package com.cis4030.pokedex.ui.comparison

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cis4030.pokedex.databinding.CompareFragmentBinding
import com.cis4030.pokedex.util.getColorRounded
import com.cis4030.pokedex.viewmodels.CompareViewModel

class CompareFragment: Fragment() {

    val viewmodel: CompareViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val pokemon1Name = requireArguments().getString("pokemon1")!!
        val pokemon2Name = requireArguments().getString("pokemon2")!!

        viewmodel.getPokemon(pokemon1Name,pokemon2Name)
        viewmodel.calculatePowers()

        setHasOptionsMenu(true)

        val binding = CompareFragmentBinding.inflate(inflater)
        binding.viewModel=viewmodel
        binding.pokemon1=viewmodel.pokemon1
        binding.pokemon2=viewmodel.pokemon2

        binding.pokemon1PowerText.text="Power: ${viewmodel.power1}"
        binding.pokemon2PowerText.text="Power: ${viewmodel.power2}"

        if(viewmodel.power1>viewmodel.power2){
            binding.pokemon1Winner.isVisible=true
            binding.pokemon2Winner.isVisible=false
        }
        else{

            binding.pokemon2Winner.isVisible=true
            binding.pokemon1Winner.isVisible=false
        }
        binding.pokemon1Bg.setBackgroundResource(getColorRounded(viewmodel.pokemon1.type1))
        binding.pokemon2Bg.setBackgroundResource(getColorRounded(viewmodel.pokemon2.type1))

        if(viewmodel.pokemon1.baseHP>viewmodel.pokemon2.baseHP){
            binding.hpWinner1.isVisible=true
            binding.hpWinner2.isVisible=false
        }
        else if(viewmodel.pokemon1.baseHP<viewmodel.pokemon2.baseHP){
            binding.hpWinner1.isVisible=false
            binding.hpWinner2.isVisible=true
        }
        else{
            binding.hpWinner1.isVisible=false
            binding.hpWinner2.isVisible=false
        }

        if(viewmodel.pokemon1.baseAtk>viewmodel.pokemon2.baseAtk){
            binding.atkWinner1.isVisible=true
            binding.atkWinner2.isVisible=false
        }
        else if(viewmodel.pokemon1.baseAtk<viewmodel.pokemon2.baseAtk){
            binding.atkWinner1.isVisible=false
            binding.atkWinner2.isVisible=true
        }
        else{
            binding.atkWinner1.isVisible=false
            binding.atkWinner2.isVisible=false
        }

        if(viewmodel.pokemon1.baseDfn>viewmodel.pokemon2.baseDfn){
            binding.defWinner1.isVisible=true
            binding.defWinner2.isVisible=false
        }
        else if(viewmodel.pokemon1.baseDfn<viewmodel.pokemon2.baseDfn){
            binding.defWinner1.isVisible=false
            binding.defWinner2.isVisible=true
        }
        else{
            binding.defWinner1.isVisible=false
            binding.defWinner2.isVisible=false
        }

        if(viewmodel.pokemon1.spAtk>viewmodel.pokemon2.spAtk){
            binding.spatkWinner1.isVisible=true
            binding.spatkWinner2.isVisible=false
        }
        else if(viewmodel.pokemon1.spAtk<viewmodel.pokemon2.spAtk){
            binding.spatkWinner1.isVisible=false
            binding.spatkWinner2.isVisible=true
        }
        else{
            binding.spatkWinner1.isVisible=false
            binding.spatkWinner2.isVisible=false
        }

        if(viewmodel.pokemon1.spDfn>viewmodel.pokemon2.spDfn){
            binding.spdefWinner1.isVisible=true
            binding.spdefWinner2.isVisible=false
        }
        else if(viewmodel.pokemon1.spDfn<viewmodel.pokemon2.spDfn){
            binding.spdefWinner1.isVisible=false
            binding.spdefWinner2.isVisible=true
        }
        else{
            binding.spdefWinner1.isVisible=false
            binding.spdefWinner2.isVisible=false
        }

        if(viewmodel.pokemon1.speed>viewmodel.pokemon2.speed){
            binding.spdWinner1.isVisible=true
            binding.spdWinner2.isVisible=false
        }
        else if(viewmodel.pokemon1.speed<viewmodel.pokemon2.speed){
            binding.spdWinner1.isVisible=false
            binding.spdWinner2.isVisible=true
        }
        else{
            binding.spdWinner1.isVisible=false
            binding.spdWinner2.isVisible=false
        }

        binding.atkLabel1.text=viewmodel.pokemon1.baseAtk.toString()
        binding.atkLabel2.text=viewmodel.pokemon2.baseAtk.toString()

        binding.defLabel1.text=viewmodel.pokemon1.baseDfn.toString()
        binding.defLabel2.text=viewmodel.pokemon2.baseDfn.toString()

        binding.hpLabel1.text=viewmodel.pokemon1.baseHP.toString()
        binding.hpLabel2.text=viewmodel.pokemon2.baseHP.toString()

        binding.spatkLabel1.text=viewmodel.pokemon1.spAtk.toString()
        binding.spatkLabel2.text=viewmodel.pokemon2.spAtk.toString()

        binding.spdefLabel1.text=viewmodel.pokemon1.spDfn.toString()
        binding.spdefLabel2.text=viewmodel.pokemon2.spDfn.toString()

        binding.spdLabel1.text=viewmodel.pokemon1.speed.toString()
        binding.spdLabel2.text=viewmodel.pokemon2.speed.toString()

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                findNavController().navigateUp()
            }
        }
        return true
    }
}
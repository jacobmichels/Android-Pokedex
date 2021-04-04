package com.cis4030.pokedex.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cis4030.pokedex.databinding.FragmentTeamViewBinding
import com.cis4030.pokedex.util.getColorRounded
import com.cis4030.pokedex.util.loadImage
import com.cis4030.pokedex.viewmodels.TeamsViewModel

class TeamViewFragment: Fragment() {
    val viewModel: TeamsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTeamViewBinding.inflate(inflater)

        getTeamPokemon()

        binding.pokemon1Name.text=viewModel.pokemon1?.name
        binding.pokemon1Name.isGone=!viewModel.pokemon1?.name.isNullOrEmpty()

        binding.pokemon2Name.text=viewModel.pokemon2?.name
        binding.pokemon2Name.isGone=!viewModel.pokemon2?.name.isNullOrEmpty()

        binding.pokemon3Name.text=viewModel.pokemon3?.name
        binding.pokemon3Name.isGone=!viewModel.pokemon4?.name.isNullOrEmpty()

        binding.pokemon4Name.text=viewModel.pokemon4?.name
        binding.pokemon4Name.isGone=!viewModel.pokemon4?.name.isNullOrEmpty()

        binding.pokemon5Name.text=viewModel.pokemon5?.name
        binding.pokemon5Name.isGone=!viewModel.pokemon5?.name.isNullOrEmpty()

        binding.pokemon6Name.text=viewModel.pokemon6?.name
        binding.pokemon6Name.isGone=!viewModel.pokemon6?.name.isNullOrEmpty()

        loadImage(binding.pokemon1Sprite,viewModel.pokemon1?.imageUrl)
        binding.pokemon1Sprite.isGone=viewModel.pokemon1==null

        loadImage(binding.pokemon2Sprite,viewModel.pokemon2?.imageUrl)
        binding.pokemon2Sprite.isGone=viewModel.pokemon2==null

        loadImage(binding.pokemon3Sprite,viewModel.pokemon3?.imageUrl)
        binding.pokemon3Sprite.isGone=viewModel.pokemon3==null

        loadImage(binding.pokemon4Sprite,viewModel.pokemon4?.imageUrl)
        binding.pokemon4Sprite.isGone=viewModel.pokemon4==null

        loadImage(binding.pokemon5Sprite,viewModel.pokemon5?.imageUrl)
        binding.pokemon5Sprite.isGone=viewModel.pokemon5==null

        loadImage(binding.pokemon6Sprite,viewModel.pokemon6?.imageUrl)
        binding.pokemon6Sprite.isGone=viewModel.pokemon6==null

        binding.pokemon1Type1.text=viewModel.pokemon1?.type1
        binding.pokemon1Type1.isGone=viewModel.pokemon1?.type1.isNullOrEmpty()
        binding.pokemon1Type2.text=viewModel.pokemon1?.type2
        binding.pokemon1Type2.isGone=viewModel.pokemon1?.type2.isNullOrEmpty()

        binding.pokemon2Type1.text=viewModel.pokemon2?.type1
        binding.pokemon2Type1.isGone=viewModel.pokemon2?.type1.isNullOrEmpty()
        binding.pokemon2Type2.text=viewModel.pokemon2?.type2
        binding.pokemon2Type2.isGone=viewModel.pokemon2?.type2.isNullOrEmpty()

        binding.pokemon3Type1.text=viewModel.pokemon3?.type1
        binding.pokemon3Type1.isGone=viewModel.pokemon3?.type1.isNullOrEmpty()
        binding.pokemon3Type2.text=viewModel.pokemon3?.type2
        binding.pokemon3Type2.isGone=viewModel.pokemon3?.type2.isNullOrEmpty()

        binding.pokemon4Type1.text=viewModel.pokemon4?.type1
        binding.pokemon4Type1.isGone=viewModel.pokemon4?.type1.isNullOrEmpty()
        binding.pokemon4Type2.text=viewModel.pokemon4?.type2
        binding.pokemon4Type2.isGone=viewModel.pokemon4?.type2.isNullOrEmpty()

        binding.pokemon5Type1.text=viewModel.pokemon5?.type1
        binding.pokemon5Type1.isGone=viewModel.pokemon5?.type1.isNullOrEmpty()
        binding.pokemon5Type2.text=viewModel.pokemon5?.type2
        binding.pokemon5Type2.isGone=viewModel.pokemon5?.type2.isNullOrEmpty()

        binding.pokemon6Type1.text=viewModel.pokemon6?.type1
        binding.pokemon6Type1.isGone=viewModel.pokemon6?.type1.isNullOrEmpty()
        binding.pokemon6Type2.text=viewModel.pokemon6?.type2
        binding.pokemon6Type2.isGone=viewModel.pokemon6?.type2.isNullOrEmpty()

        binding.pokemon1Bg.setBackgroundResource(getColorRounded(viewModel.pokemon1?.type1))
        binding.pokemon1Bg.isGone=viewModel.pokemon1==null
        binding.pokemon2Bg.setBackgroundResource(getColorRounded(viewModel.pokemon2?.type1))
        binding.pokemon2Bg.isGone=viewModel.pokemon2==null
        binding.pokemon3Bg.setBackgroundResource(getColorRounded(viewModel.pokemon3?.type1))
        binding.pokemon3Bg.isGone=viewModel.pokemon3==null
        binding.pokemon4Bg.setBackgroundResource(getColorRounded(viewModel.pokemon4?.type1))
        binding.pokemon4Bg.isGone=viewModel.pokemon4==null
        binding.pokemon5Bg.setBackgroundResource(getColorRounded(viewModel.pokemon5?.type1))
        binding.pokemon5Bg.isGone=viewModel.pokemon5==null
        binding.pokemon6Bg.setBackgroundResource(getColorRounded(viewModel.pokemon6?.type1))
        binding.pokemon6Bg.isGone=viewModel.pokemon6==null

        binding.lifecycleOwner=this


        return binding.root
    }

    private fun getTeamPokemon(){
        viewModel.populatePokemonFields()
    }

    override fun onDestroyView() {
        viewModel.pokemon1=null
        viewModel.pokemon2=null
        viewModel.pokemon3=null
        viewModel.pokemon4=null
        viewModel.pokemon5=null
        viewModel.pokemon6=null
        super.onDestroyView()
    }
}
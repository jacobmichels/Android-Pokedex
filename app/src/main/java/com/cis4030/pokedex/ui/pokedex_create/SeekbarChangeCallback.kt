package com.cis4030.pokedex.ui.pokedex_create

import android.util.Log
import android.widget.SeekBar
import com.cis4030.pokedex.R
import com.cis4030.pokedex.viewmodels.CreatePokemonViewModel

class SeekbarChangeCallback(val viewModel: CreatePokemonViewModel): SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        //only respond to user input, ignore programmatic input
        if(fromUser){
            when(seekBar?.id){
                R.id.hp_seekbar -> {
                    viewModel.hp=progress
                    viewModel.updateText("hp")

                }
                R.id.atk_seekbar->{
                    viewModel.atk=progress
                    viewModel.updateText("atk")
                }
                R.id.def_seekbar->{
                    viewModel.def=progress
                    viewModel.updateText("def")
                }
                R.id.sp_atk_seekbar->{
                    viewModel.spAtk=progress
                    viewModel.updateText("sp_atk")

                }
                R.id.sp_def_seekbar->{
                    viewModel.spDef=progress
                    viewModel.updateText("sp_def")
                }
                R.id.spd_seekbar->{
                    viewModel.spd=progress
                    viewModel.updateText("spd")
                }
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        return
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        return
    }
}
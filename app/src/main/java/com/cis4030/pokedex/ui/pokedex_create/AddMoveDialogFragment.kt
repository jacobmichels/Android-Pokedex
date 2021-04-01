package com.cis4030.pokedex.ui.pokedex_create

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.cis4030.pokedex.R
import com.cis4030.pokedex.database.DatabaseCustomMove
import com.cis4030.pokedex.databinding.AddMoveDialogBinding
import com.cis4030.pokedex.viewmodels.CreatePokemonViewModel
import java.lang.IllegalStateException

class AddMoveDialogFragment: DialogFragment() {

    val viewModel: CreatePokemonViewModel by activityViewModels()
    lateinit var binding: AddMoveDialogBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val builder = AlertDialog.Builder(it)
            val inflater = it.layoutInflater
            binding = AddMoveDialogBinding.inflate(inflater)
            builder.setView(binding.root)
                .setPositiveButton("Add Move",null)
                .setNegativeButton("Cancel"){dialog,id->
                    dialog.cancel()
                }
            val dialog = builder.create()
            dialog.setOnShowListener(DialogInterface.OnShowListener {
                val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                button.setOnClickListener(View.OnClickListener {
                    Log.d("POKEDEX","In listener")
                    if(binding.moveNameText.text.toString().isEmpty()){
                        Toast.makeText(context,"Move needs a name!",Toast.LENGTH_LONG).show()
                    }
                    else if(binding.descriptionText2.text.toString().isBlank()){
                        Toast.makeText(context,"Move needs a description!",Toast.LENGTH_LONG).show()
                    }
                    else if(binding.typeSpinner.selectedItemPosition==0){
                        Toast.makeText(context,"Move needs a type!",Toast.LENGTH_LONG).show()
                    }
                    else{
                        val move = DatabaseCustomMove(binding.moveNameText.text.toString(),binding.typeSpinner.selectedItem as String, binding.descriptionText2.text.toString())
                        viewModel.moveList.add(move)
                        viewModel.updateMoveList()
                        dialog.dismiss()
                    }
                })
            })
            return dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
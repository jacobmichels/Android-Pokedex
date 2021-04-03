package com.cis4030.pokedex.ui.team

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.cis4030.pokedex.databinding.NewTeamDialogBinding
import com.cis4030.pokedex.viewmodels.TeamsViewModel

class NewTeamDialog(val viewModel: TeamsViewModel): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val binding = NewTeamDialogBinding.inflate(it.layoutInflater)
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
                .setTitle("New Team")
                .setPositiveButton("Create") {dialog, id->
                    if(!viewModel.addTeam(binding.newTeamNameText.text.toString())){
                        Toast.makeText(context,"Team names must be unique! Please try again.",Toast.LENGTH_LONG).show()
                    }
                }
                .setNegativeButton("Cancel"){dialog, id ->
                    dialog.dismiss()
                }

            builder.create()
        } ?: throw IllegalStateException("activity cannot be null")
    }
}
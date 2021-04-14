package com.cis4030.pokedex.ui.team

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.cis4030.pokedex.databinding.NewTeamDialogBinding
import com.cis4030.pokedex.viewmodels.TeamListViewModel
import com.cis4030.pokedex.viewmodels.TeamViewModel
import java.lang.ClassCastException

class RenameTeamDialog(val viewModel: TeamViewModel): DialogFragment() {

    internal lateinit var listener: RenameDialogListener

    interface RenameDialogListener {
        fun onRenameDialogPositiveClick(dialog: DialogInterface,newName: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val binding = NewTeamDialogBinding.inflate(it.layoutInflater)
            val builder = AlertDialog.Builder(it)
            binding.newTeamNameText.hint="New Team Name"
            builder.setView(binding.root)
                .setTitle("Rename Team")
                .setPositiveButton("Rename") { dialog: DialogInterface, _: Int ->
                    listener.onRenameDialogPositiveClick(dialog,binding.newTeamNameText.text.toString())
                }
                .setNegativeButton("Cancel"){ dialog, _ ->
                    dialog.dismiss()
                }

            builder.create()
        } ?: throw IllegalStateException("activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = targetFragment as RenameDialogListener
        } catch(e: ClassCastException){
            throw ClassCastException("$context must implement RenameDialogListener")
        }
    }
}
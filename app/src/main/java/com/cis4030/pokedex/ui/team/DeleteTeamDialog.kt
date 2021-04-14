package com.cis4030.pokedex.ui.team

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.cis4030.pokedex.viewmodels.TeamListViewModel
import com.cis4030.pokedex.viewmodels.TeamViewModel
import java.lang.ClassCastException

class DeleteTeamDialog(val viewModel: TeamViewModel):DialogFragment() {

    internal lateinit var listener:DeleteDialogListener

    interface DeleteDialogListener {
        fun onDeleteDialogPositiveClick(dialog: DialogInterface)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Are you sure you want to delete this team?")
                .setPositiveButton("Delete"){ dialog: DialogInterface, id: Int ->
                    listener.onDeleteDialogPositiveClick(dialog)
                }
                .setNegativeButton("Cancel"){ dialog: DialogInterface, id: Int ->
                    dialog.dismiss()
                }

            builder.create()
        } ?: throw IllegalStateException("activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = targetFragment as DeleteDialogListener
        } catch(e: ClassCastException){
            throw ClassCastException("$context must implement RenameDialogListener")
        }
    }

}
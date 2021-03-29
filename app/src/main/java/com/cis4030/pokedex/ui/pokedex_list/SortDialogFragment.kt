package com.cis4030.pokedex.ui.pokedex_list

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.cis4030.pokedex.R
import com.cis4030.pokedex.viewmodels.SharedViewModel


class SortDialogFragment():DialogFragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{ it ->
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)

            builder.setTitle("Sort Pokemon")
                .setItems(R.array.sort_options
                ) { dialog, where ->
                    sharedViewModel.selectedOrder=resources.getStringArray(R.array.sort_options)[where]
                    sharedViewModel.sortAndFilterPokemon()
                }

                // Add action buttons
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
            // Create the AlertDialog object and return it
            return builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
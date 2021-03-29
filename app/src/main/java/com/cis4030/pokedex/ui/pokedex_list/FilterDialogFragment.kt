package com.cis4030.pokedex.ui.pokedex_list

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.cis4030.pokedex.databinding.FilterDialogBinding
import com.cis4030.pokedex.viewmodels.SharedViewModel
import com.google.android.material.chip.Chip

class FilterDialogFragment: DialogFragment() {
    private lateinit var alertDialog: AlertDialog
    private val sharedViewModel:SharedViewModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{ it ->
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater

            val binding:FilterDialogBinding = FilterDialogBinding.inflate(inflater)

            setClickListeners(binding)

            binding.viewModel=sharedViewModel
            sharedViewModel.createFilterListCopies()

            builder.setView(binding.root)
                // Add action buttons
                .setPositiveButton("Apply",
                    DialogInterface.OnClickListener { dialog, id ->
                        Log.d("POKEDEX","apply clicked")
                        sharedViewModel.saveFilterChanges()
                        sharedViewModel.sortAndFilterPokemon()
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        sharedViewModel.deleteFilterChanges()
                        dialog.cancel()
                    })
            // Create the AlertDialog object and return it
            alertDialog = builder.create()
            return alertDialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    /**
     * There's probably a better way to do this, I'm at a loss. This method sets click listeners for the fields of the dialog
     */
    private fun setClickListeners(binding: FilterDialogBinding){
        binding.checkboxGeneration1.setOnClickListener { checkbox->
            checkBoxListener(checkbox)
        }
        binding.checkboxGeneration2.setOnClickListener { checkbox->
            checkBoxListener(checkbox)
        }
        binding.checkboxGeneration3.setOnClickListener { checkbox->
            checkBoxListener(checkbox)
        }
        binding.checkboxGeneration4.setOnClickListener { checkbox->
            checkBoxListener(checkbox)
        }
        binding.checkboxGeneration5.setOnClickListener { checkbox->
            checkBoxListener(checkbox)
        }
        binding.checkboxGeneration6.setOnClickListener { checkbox->
            checkBoxListener(checkbox)
        }
        binding.checkboxGeneration7.setOnClickListener { checkbox->
            checkBoxListener(checkbox)
        }
        binding.checkboxGeneration8.setOnClickListener { checkbox->
            checkBoxListener(checkbox)
        }
        binding.chipNormal.setOnClickListener(){chip ->
            chipListener(chip)                   }
        binding.chipFire.setOnClickListener(){chip ->
            chipListener(chip)                   }
        binding.chipFighting.setOnClickListener(){chip ->
            chipListener(chip)                   }
        binding.chipWater.setOnClickListener(){chip ->
            chipListener(chip)                   }
        binding.chipFlying.setOnClickListener(){chip ->
            chipListener(chip)                   }
        binding.chipGrass.setOnClickListener(){chip ->
            chipListener(chip)                   }
        binding.chipPoison.setOnClickListener(){chip ->
            chipListener(chip)                   }
        binding.chipElectric.setOnClickListener(){chip ->
            chipListener(chip)                   }
        binding.chipGround.setOnClickListener(){chip ->
            chipListener(chip)                   }
        binding.chipPsychic.setOnClickListener(){chip ->
            chipListener(chip)                   }
        binding.chipRock.setOnClickListener(){chip ->
            chipListener(chip)                   }
        binding.chipIce.setOnClickListener(){chip ->
            chipListener(chip)                   }
        binding.chipBug.setOnClickListener(){chip ->
            chipListener(chip)                   }
        binding.chipDragon.setOnClickListener(){chip ->
            chipListener(chip)                   }
        binding.chipGhost.setOnClickListener(){chip ->
            chipListener(chip)                   }
        binding.chipDark.setOnClickListener(){chip ->
            chipListener(chip)                   }
        binding.chipSteel.setOnClickListener(){chip ->
            chipListener(chip)                   }
        binding.chipFairy.setOnClickListener(){chip ->
            chipListener(chip)                   }
    }

    private fun chipListener(chip: View?) {
        if(chip is Chip){
            if (chip.isChecked) {
                sharedViewModel.typesSelected.add(chip.text as String)
            } else {
                sharedViewModel.typesSelected.remove(chip.text as String)
            }
        }
    }

    private fun checkBoxListener(checkbox: View?) {
        if (checkbox is CheckBox) {
            if (checkbox.isChecked) {
                sharedViewModel.generationsSelected.add((checkbox.text as String).toInt())
            } else {
                sharedViewModel.generationsSelected.remove((checkbox.text as String).toInt())
            }
        }
    }
}
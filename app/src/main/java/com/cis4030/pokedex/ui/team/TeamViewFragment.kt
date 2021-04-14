package com.cis4030.pokedex.ui.team

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cis4030.pokedex.R
import com.cis4030.pokedex.database.DatabaseTeam
import com.cis4030.pokedex.databinding.FragmentTeamViewBinding
import com.cis4030.pokedex.util.getColorRounded
import com.cis4030.pokedex.viewmodels.TeamViewModel

class TeamViewFragment: Fragment(), RenameTeamDialog.RenameDialogListener, DeleteTeamDialog.DeleteDialogListener {
    val viewModel: TeamViewModel by activityViewModels()
    lateinit var binding: FragmentTeamViewBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamViewBinding.inflate(inflater)

        val teamname = arguments?.getString("teamname")
        if(teamname!=null){
            Log.d("POKEDEX","displaying team: $teamname")
            viewModel.loadTeam(teamname)
        }

        val pokemonToAdd = arguments?.getString("pokemon")
        if(pokemonToAdd!=null){
            viewModel.addPokemon(pokemonToAdd)
        }

        setHasOptionsMenu(true)

        binding.viewModel=viewModel

        binding.pokemon1=viewModel.pokemon1
        binding.pokemon1Bg.setBackgroundResource(getColorRounded(viewModel.pokemon1?.type1))
        binding.pokemon2=viewModel.pokemon2
        binding.pokemon2Bg.setBackgroundResource(getColorRounded(viewModel.pokemon2?.type1))
        binding.pokemon3=viewModel.pokemon3
        binding.pokemon3Bg.setBackgroundResource(getColorRounded(viewModel.pokemon3?.type1))
        binding.pokemon4=viewModel.pokemon4
        binding.pokemon4Bg.setBackgroundResource(getColorRounded(viewModel.pokemon4?.type1))
        binding.pokemon5=viewModel.pokemon5
        binding.pokemon5Bg.setBackgroundResource(getColorRounded(viewModel.pokemon5?.type1))
        binding.pokemon6=viewModel.pokemon6
        binding.pokemon6Bg.setBackgroundResource(getColorRounded(viewModel.pokemon6?.type1))

        setPowerText(viewModel.team!!)

        binding.lifecycleOwner=this

        (activity as AppCompatActivity?)?.supportActionBar?.title=viewModel.teamname

        viewModel.refreshUI.observe(viewLifecycleOwner) {
            if (it) {
                binding.invalidateAll()
                viewModel.refreshedUI()
            }

        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.team_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home->{
                activity?.onBackPressed()
                true
            }
            R.id.add_pokemon -> {
                if(viewModel.team?.pokemon6Name?.isNotBlank() == true){
                    Toast.makeText(context,"Team is already at max capacity.",Toast.LENGTH_LONG).show()
                    return true
                }
                val action = TeamViewFragmentDirections.actionTeamViewFragmentToSelectPokemonFragment()
                findNavController().navigate(action)
                true
            }
            R.id.rename_team->{
                val dialog = RenameTeamDialog(viewModel)
                dialog.isCancelable=false
                dialog.setTargetFragment(this,0)
                dialog.show(parentFragmentManager,"Rename Team Dialog")

                true
            }
            R.id.delete_team->{
                val dialog = DeleteTeamDialog(viewModel)
                dialog.isCancelable=false
                dialog.setTargetFragment(this,0)
                dialog.show(parentFragmentManager,"Delete Team Dialog")

                true
            }
            R.id.clear_team->{
                viewModel.clearTeam()
                binding.pokemon1=null
                binding.pokemon2=null
                binding.pokemon3=null
                binding.pokemon4=null
                binding.pokemon5=null
                binding.pokemon6=null
                binding.invalidateAll()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onRenameDialogPositiveClick(dialog: DialogInterface,name: String) {
        if(name==viewModel.team?.name){
            Toast.makeText(context,"Team is already called that!", Toast.LENGTH_LONG).show()
            return
        }
        if(name.isBlank()){
            Toast.makeText(context,"Team needs a name.", Toast.LENGTH_LONG).show()
            return
        }
        if(!viewModel.updateTeamName(name)){
            Toast.makeText(context,"Team names must be unique! Please try again.", Toast.LENGTH_LONG).show()
            return
        }
        Toast.makeText(context,"Team name updated!",Toast.LENGTH_LONG).show()
        dialog.dismiss()
        findNavController().navigateUp()
    }

    override fun onDeleteDialogPositiveClick(dialog: DialogInterface) {
        viewModel.deleteTeam()
        Toast.makeText(context,"Team deleted!",Toast.LENGTH_LONG).show()
        dialog.dismiss()
        findNavController().navigateUp()
    }

    private fun setPowerText(team: DatabaseTeam){
        viewModel.setPower(team.power)
    }
}
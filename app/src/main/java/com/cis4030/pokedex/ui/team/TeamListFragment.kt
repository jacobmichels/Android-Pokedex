package com.cis4030.pokedex.ui.team

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cis4030.pokedex.R
import com.cis4030.pokedex.databinding.FragmentTeamListBinding
import com.cis4030.pokedex.ui.pokedex_create.LinearMarginItemDecoration
import com.cis4030.pokedex.viewmodels.SharedViewModel
import com.cis4030.pokedex.viewmodels.TeamsViewModel

class TeamListFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel:TeamsViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTeamListBinding.inflate(inflater)
        binding.lifecycleOwner=this
        binding.viewModel=viewModel

        binding.teamList.adapter=TeamsListAdapter(TeamsListAdapter.OnClickListener{
            viewModel.teamToDisplay=it
            val action = TeamListFragmentDirections.actionTeamViewerToTeamViewFragment()
            findNavController().navigate(action)
        })
        binding.teamList.addItemDecoration(LinearMarginItemDecoration(20))

        viewModel.teamsList.observe(viewLifecycleOwner){
            binding.noTeamText.isVisible = it.isEmpty()
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.teams_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.new_team->{
                val dialog = NewTeamDialog(viewModel)
                dialog.isCancelable=false
                dialog.show(parentFragmentManager,"New Team Dialog")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
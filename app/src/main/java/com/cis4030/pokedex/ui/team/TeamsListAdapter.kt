package com.cis4030.pokedex.ui.team

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cis4030.pokedex.database.DatabasePokemon
import com.cis4030.pokedex.database.DatabaseTeam
import com.cis4030.pokedex.databinding.TeamListItemBinding
import com.cis4030.pokedex.repository.PokedexRepository
import com.cis4030.pokedex.util.getTeamBackground
import kotlinx.coroutines.runBlocking

class TeamsListAdapter(private val onClickListener: OnClickListener, private val repository: PokedexRepository):ListAdapter<DatabaseTeam, TeamsListAdapter.ViewHolder>(DiffCallback) {
    class ViewHolder(private var binding:TeamListItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(team:DatabaseTeam, repository:PokedexRepository){
            binding.team=team
            Log.d("POKEDEX","binding teamname: ${team.name}")
            //TODO bind pokemon images here
            runBlocking {
                binding.dbpokemon1 = team.pokemon1Name?.let { repository.getPokemonByName(it) }
                binding.dbpokemon2 = team.pokemon2Name?.let { repository.getPokemonByName(it) }
                binding.dbpokemon3 = team.pokemon3Name?.let { repository.getPokemonByName(it) }
                binding.dbpokemon4 = team.pokemon4Name?.let { repository.getPokemonByName(it) }
                binding.dbpokemon5 = team.pokemon5Name?.let { repository.getPokemonByName(it) }
                binding.dbpokemon6 = team.pokemon6Name?.let { repository.getPokemonByName(it) }
            }


            binding.root.setBackgroundResource(getTeamBackground())
            binding.executePendingBindings()
        }

        fun unbind(){
            binding.pokemon1.setImageResource(android.R.color.transparent)
            binding.pokemon2.setImageResource(android.R.color.transparent)
            binding.pokemon3.setImageResource(android.R.color.transparent)
            binding.pokemon4.setImageResource(android.R.color.transparent)
            binding.pokemon5.setImageResource(android.R.color.transparent)
            binding.pokemon6.setImageResource(android.R.color.transparent)
        }
    }
    companion object DiffCallback : DiffUtil.ItemCallback<DatabaseTeam>() {
        override fun areItemsTheSame(oldItem: DatabaseTeam, newItem: DatabaseTeam): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DatabaseTeam, newItem: DatabaseTeam): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return TeamsListAdapter.ViewHolder(
            TeamListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val team = getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(team)
        }
        holder.bind(team,repository)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.unbind()
        super.onViewRecycled(holder)
    }

    class OnClickListener(val clickListener: (team: DatabaseTeam) -> Unit) {
        fun onClick(team: DatabaseTeam) = clickListener(team)
    }
}
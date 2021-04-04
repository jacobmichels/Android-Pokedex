package com.cis4030.pokedex.ui.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cis4030.pokedex.database.DatabasePokemon
import com.cis4030.pokedex.database.DatabaseTeam
import com.cis4030.pokedex.databinding.TeamListItemBinding
import com.cis4030.pokedex.util.getTeamBackground

class TeamsListAdapter(private val onClickListener: OnClickListener):ListAdapter<DatabaseTeam, TeamsListAdapter.ViewHolder>(DiffCallback) {
    class ViewHolder(private var binding:TeamListItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(team:DatabaseTeam){
            binding.team=team
            //TODO bind pokemon images here
            binding.pokemon1

            binding.root.setBackgroundResource(getTeamBackground())
            binding.executePendingBindings()
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
        holder.bind(team)
    }
    class OnClickListener(val clickListener: (team: DatabaseTeam) -> Unit) {
        fun onClick(team: DatabaseTeam) = clickListener(team)
    }
}
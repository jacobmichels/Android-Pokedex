package com.cis4030.pokedex.ui.pokedex_create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cis4030.pokedex.database.DatabaseCustomPokemon
import com.cis4030.pokedex.database.DatabasePokemon
import com.cis4030.pokedex.databinding.CustomPokemonItemViewBinding
import com.cis4030.pokedex.util.getColorRounded

class CustomPokemonGridAdapter(private val onClickListener: CustomPokemonGridAdapter.OnClickListener): ListAdapter<DatabaseCustomPokemon, CustomPokemonGridAdapter.ViewHolder>(DiffCallback) {
    class ViewHolder(private val binding: CustomPokemonItemViewBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(pokemon:DatabaseCustomPokemon){
            binding.pokemon=pokemon
            binding.root.setBackgroundResource(getColorRounded(pokemon.type1))
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DatabaseCustomPokemon>() {
        override fun areItemsTheSame(oldItem: DatabaseCustomPokemon, newItem: DatabaseCustomPokemon): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DatabaseCustomPokemon, newItem: DatabaseCustomPokemon): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomPokemonGridAdapter.ViewHolder {
        return CustomPokemonGridAdapter.ViewHolder(
            CustomPokemonItemViewBinding.inflate(LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CustomPokemonGridAdapter.ViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(pokemon)
        }
        holder.bind(pokemon)
    }

    class OnClickListener(val clickListener: (pokemon:DatabaseCustomPokemon) -> Unit) {
        fun onClick(pokemon:DatabaseCustomPokemon) = clickListener(pokemon)
    }
}
package com.cis4030.pokedex.ui.pokedex_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.cis4030.pokedex.database.DatabasePokemon
import com.cis4030.pokedex.databinding.PokedexViewItemBinding
import com.cis4030.pokedex.util.getColor

class PokemonGridAdapter(private val onClickListener: OnClickListener) : ListAdapter<DatabasePokemon, PokemonGridAdapter.PokemonViewHolder>(DiffCallback) {

    /**
     * The PokemonViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [DatabasePokemon] information.
     */
    class PokemonViewHolder(private var binding: PokedexViewItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: DatabasePokemon) {
            binding.pokemon = pokemon
            binding.backgroundColor = getColor(pokemon)
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    init {
        this.setHasStableIds(true)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [DatabasePokemon]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<DatabasePokemon>() {
        override fun areItemsTheSame(oldItem: DatabasePokemon, newItem: DatabasePokemon): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DatabasePokemon, newItem: DatabasePokemon): Boolean {
            return oldItem.name == newItem.name
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(PokedexViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(pokemon)
        }
        holder.bind(pokemon)
    }

    override fun getItemId(position: Int): Long {
        val pokemon = getItem(position)
        return pokemon.id.toLong()
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [DatabasePokemon]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [DatabasePokemon]
     */
    class OnClickListener(val clickListener: (pokemon:DatabasePokemon) -> Unit) {
        fun onClick(pokemon:DatabasePokemon) = clickListener(pokemon)
    }

}
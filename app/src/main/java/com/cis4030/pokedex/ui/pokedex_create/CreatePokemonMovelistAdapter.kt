package com.cis4030.pokedex.ui.pokedex_create

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import com.cis4030.pokedex.R
import com.cis4030.pokedex.database.DatabaseCustomMove
import com.cis4030.pokedex.database.DatabasePokemon
import com.cis4030.pokedex.databinding.CustomPokemonMovelistItemBinding
import com.cis4030.pokedex.databinding.PokedexViewItemBinding
import com.cis4030.pokedex.ui.pokedex_list.PokemonGridAdapter
import com.cis4030.pokedex.util.getTypeColor
import com.cis4030.pokedex.viewmodels.CreatePokemonViewModel
import com.google.firebase.database.collection.LLRBNode

class CreatePokemonMovelistAdapter(val viewModel: CreatePokemonViewModel): RecyclerView.Adapter<CreatePokemonMovelistAdapter.MoveListViewHolder>() {

    lateinit var moves: List<DatabaseCustomMove>

    class MoveListViewHolder(private var binding: CustomPokemonMovelistItemBinding,val viewModel: CreatePokemonViewModel):RecyclerView.ViewHolder(binding.root){
        fun bind(move: DatabaseCustomMove){
            binding.move=move
            binding.moveTypeChip.setChipBackgroundColorResource(getTypeColor(move.type))
            binding.deleteMove.setOnClickListener{
                viewModel.moveList.remove(move)
                viewModel.updateMoveList()
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoveListViewHolder {
        return MoveListViewHolder(CustomPokemonMovelistItemBinding.inflate(LayoutInflater.from(parent.context)),viewModel)
    }

    override fun onBindViewHolder(holder: MoveListViewHolder, position: Int) {
        val move = moves[position]
        holder.bind(move)
    }

    override fun getItemCount(): Int {
        return moves.size
    }
}
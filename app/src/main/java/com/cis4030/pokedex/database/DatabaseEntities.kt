package com.cis4030.pokedex.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cis4030.pokedex.domain.Ability
import com.cis4030.pokedex.domain.Move
import com.cis4030.pokedex.domain.Type

@Entity
data class DatabasePokemon constructor(
    @PrimaryKey
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val imageUrl:String,
    val baseExperience: Int,
    val abilities: List<Ability>,
    val baseHP: Int,
    val baseAtk: Int,
    val baseDfn: Int,
    val spAtk: Int,
    val spDfn: Int,
    val speed: Int,
    val types: List<Type>,
    val possibleMoves: List<Move>
)
package com.cis4030.pokedex.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cis4030.pokedex.domain.*

@Entity
data class DatabasePokemon(
    @PrimaryKey
    val name: String,
    val id: Int,
    val height: Int,
    val weight: Int,
    val imageUrl: String,
    val baseExperience: Int,
    val abilities: List<String>,
    val baseHP: Int,
    val baseAtk: Int,
    val baseDfn: Int,
    val spAtk: Int,
    val spDfn: Int,
    val speed: Int,
    val type1: String,
    val type2: String?,
    val possibleMoves: List<String>,
    val custom: Boolean,
    var generation: Int,
    val speciesName: String
)

@Entity
data class DatabaseType constructor(
    @PrimaryKey
    val name: String,
    val moveList: List<String>,
    val pokemonList: List<String>,
    val doubleDamageFrom: List<String>,
    val doubleDamageTo: List<String>,
    val halfDamageFrom: List<String>,
    val halfDamageTo: List<String>,
    val noDamageFrom: List<String>,
    val noDamageTo: List<String>
)

@Entity
data class DatabaseAbility constructor(
    @PrimaryKey
    val name: String,
    val description: String,
    val shortDescription:String,
    val pokemonList: List<String>
)

@Entity
data class DatabaseMove constructor(
    @PrimaryKey
    val name: String,
    val type: String,
    val accuracy: Int?,
    val damageType: String?,
    val effects: List<Effect>,
    val learnedByPokemon: List<String>,
    val power: Int?,
    val pp: Int?,
    val priority: Int,
    val target: String
)

@Entity
data class DatabaseCustomMove constructor(
    @PrimaryKey
    val name: String,
    val type: String,
    val description: String
)

@Entity
data class DatabaseCustomPokemon constructor(
    @PrimaryKey()
    val name: String,
    val description: String,
    val movesList: List<String>,
    val baseHP: Int,
    val baseAtk: Int,
    val baseDfn: Int,
    val spAtk: Int,
    val spDfn: Int,
    val speed: Int,
    val type1: String,
    val type2: String?,
    val moves: List<String>
)
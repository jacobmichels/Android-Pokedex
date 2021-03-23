package com.cis4030.pokedex.network.datatransferobjects.type

import com.cis4030.pokedex.database.DatabaseType
import com.cis4030.pokedex.network.datatransferobjects.pokemon.Type
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TypeDTO(
    val damage_relations: DamageRelations,
    val game_indices: List<GameIndice>,
    val generation: NameUrlPair,
    val id: Int,
    val moves: List<NameUrlPair>,
    val name: String,
    val pokemon: List<Pokemon>
)

@JsonClass(generateAdapter = true)
data class DamageRelations(
    val double_damage_from: List<NameUrlPair>,
    val double_damage_to: List<NameUrlPair>,
    val half_damage_from: List<NameUrlPair>,
    val half_damage_to: List<NameUrlPair>,
    val no_damage_from: List<NameUrlPair>,
    val no_damage_to: List<NameUrlPair>
)

@JsonClass(generateAdapter = true)
data class Pokemon(
    val slot:Int,
    val pokemon:NameUrlPair
)

@JsonClass(generateAdapter = true)
data class GameIndice(
    val game_index: Int,
    val generation: NameUrlPair
)

@JsonClass(generateAdapter = true)
data class NameUrlPair(
    val name: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class TypeListDTO(
    val count: Int,
    val results: List<NameUrlPair>
)

fun TypeDTO.asDatabaseModel():DatabaseType{
    return DatabaseType(
        name = this.name,
        moveList = this.moves.map{
            it.name
        },
        pokemonList = this.pokemon.map{
            it.pokemon.name
        },
        doubleDamageFrom = this.damage_relations.double_damage_from.map{
                 it.name
        },
        doubleDamageTo = this.damage_relations.double_damage_to.map{
            it.name
        },
        halfDamageFrom = this.damage_relations.half_damage_from.map{
            it.name
        },
        halfDamageTo = this.damage_relations.half_damage_to.map {
            it.name
        },
        noDamageFrom = this.damage_relations.no_damage_from.map{
            it.name
        },
        noDamageTo = this.damage_relations.no_damage_to.map{
            it.name
        }
    )
}


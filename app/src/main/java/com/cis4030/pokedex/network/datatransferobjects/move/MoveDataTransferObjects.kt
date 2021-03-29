package com.cis4030.pokedex.network.datatransferobjects.move

import com.cis4030.pokedex.database.DatabaseMove
import com.cis4030.pokedex.domain.Effect
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoveDTO(
    val accuracy: Int?,
    val damage_class: NameUrlPair?,
    val effect_chance: Int?,
    val effect_entries: List<EffectEntry>,
    val generation: NameUrlPair,
    val id: Int,
    val learned_by_pokemon: List<NameUrlPair>,
    val name: String,
    val names: List<Name>,
    val power: Int?,
    val pp: Int?,
    val priority: Int,
    val target: NameUrlPair,
    val type: NameUrlPair
)

@JsonClass(generateAdapter = true)
data class EffectEntry(
    val effect: String,
    val language: NameUrlPair,
    val short_effect: String
)

@JsonClass(generateAdapter = true)
data class Name(
    val language: NameUrlPair,
    val name: String
)

@JsonClass(generateAdapter = true)
data class NameUrlPair(
    val name: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class MoveListDTO(
    val count:Int,
    val results:List<NameUrlPair>
)

fun MoveDTO.asDatabaseModel(): DatabaseMove {
    return DatabaseMove(
        name=this.name.capitalize(),
        type = this.type.name.capitalize(),
        accuracy = this.accuracy,
        damageType = this.damage_class?.name,
        effects = this.effect_entries.map{
            Effect(description = it.effect.capitalize(),effectChance = this.effect_chance)
        },
        learnedByPokemon = this.learned_by_pokemon.map{
            name.capitalize()
        },
        power = this.power,
        pp = this.pp,
        priority = this.priority,
        target = this.target.name
    )
}
package com.cis4030.pokedex.network.datatransferobjects.ability

import com.cis4030.pokedex.database.DatabaseAbility
import com.cis4030.pokedex.network.datatransferobjects.pokemon.Ability
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AbilityDTO(
    val effect_changes: List<EffectChange>,
    val effect_entries: List<EffectEntryX>,
    val flavor_text_entries: List<FlavorTextEntry>,
    val generation: NameUrlPair,
    val id: Int,
    val is_main_series: Boolean,
    val name: String,
    val names: List<Name>,
    val pokemon: List<Pokemon>
)

@JsonClass(generateAdapter = true)
data class EffectChange(
    val effect_entries: List<EffectEntry>,
    val version_group: NameUrlPair
)

@JsonClass(generateAdapter = true)
data class EffectEntryX(
    val effect: String,
    val language: NameUrlPair,
    val short_effect: String
)

@JsonClass(generateAdapter = true)
data class FlavorTextEntry(
    val flavor_text: String,
    val language: NameUrlPair,
    val version_group: NameUrlPair
)

@JsonClass(generateAdapter = true)
data class NameUrlPair(
    val name: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class Name(
    val language: NameUrlPair,
    val name: String
)

@JsonClass(generateAdapter = true)
data class Pokemon(
    val is_hidden: Boolean,
    val pokemon: NameUrlPair,
    val slot: Int
)

@JsonClass(generateAdapter = true)
data class EffectEntry(
    val effect: String,
    val language: NameUrlPair
)

@JsonClass(generateAdapter = true)
data class AbilityListDTO(
    val count:Int,
    val results:List<NameUrlPair>
)

fun AbilityDTO.asDatabaseModel():DatabaseAbility{
    var description: String = ""
    var shortEffect:String = ""
    for(effect:EffectEntryX in this.effect_entries){
        if(effect.language.name=="en"){
            description=effect.effect
            shortEffect=effect.short_effect
        }
    }

    return DatabaseAbility(
        name = this.name,
        description = description,
        shortDescription = shortEffect,
        pokemonList = this.pokemon.map{
            it.pokemon.name
        }
    )
}

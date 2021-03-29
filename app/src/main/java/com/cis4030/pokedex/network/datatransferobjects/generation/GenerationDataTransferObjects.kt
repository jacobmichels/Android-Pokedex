package com.cis4030.pokedex.network.datatransferobjects.generation

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenerationListDTO(
    val count: Int,
    val results: List<NameUrlPair>
)

@JsonClass(generateAdapter = true)
data class NameUrlPair(
    val name: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class GenerationDTO(
    val id: Int,
    val main_region: NameUrlPair,
    val moves: List<NameUrlPair>,
    val name: String,
    val names: List<Name>,
    val pokemon_species: List<NameUrlPair>,
    val types: List<NameUrlPair>,
    val version_groups: List<NameUrlPair>
)

@JsonClass(generateAdapter = true)
data class Name(
    val language: NameUrlPair,
    val name: String
)

fun GenerationDTO.asDatabaseModel(){

}
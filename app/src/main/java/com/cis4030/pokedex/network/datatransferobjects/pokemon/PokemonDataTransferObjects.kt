package com.cis4030.pokedex.network.datatransferobjects.pokemon

import com.cis4030.pokedex.database.DatabasePokemon
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDTO(
    val abilities: List<Ability>,
    val base_experience: Int,
    val forms: List<NameUrlPair>,
    val game_indices: List<GameIndice>,
    val height: Int,
    val held_items: List<HeldItem>,
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: List<Move>,
    val name: String,
    val order: Int,
    val species: NameUrlPair,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int
)

@JsonClass(generateAdapter = true)
data class Ability(
    val ability: NameUrlPair,
    val is_hidden: Boolean,
    val slot: Int
)

@JsonClass(generateAdapter = true)
data class GameIndice(
    val game_index: Int,
    val version: NameUrlPair
)

@JsonClass(generateAdapter = true)
data class HeldItem(
    val item: NameUrlPair,
    val version_details: List<VersionDetail>
)

@JsonClass(generateAdapter = true)
data class Move(
    val move: NameUrlPair,
    val version_group_details: List<VersionGroupDetail>
)

@JsonClass(generateAdapter = true)
data class Sprites(
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?,
    val other: Other,
    val versions: Versions
)

@JsonClass(generateAdapter = true)
data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: NameUrlPair
)

@JsonClass(generateAdapter = true)
data class Type(
    val slot: Int,
    val type: NameUrlPair
)

@JsonClass(generateAdapter = true)
data class VersionDetail(
    val rarity: Int,
    val version: NameUrlPair
)

@JsonClass(generateAdapter = true)
data class VersionGroupDetail(
    val level_learned_at: Int,
    val move_learn_method: NameUrlPair,
    val version_group: NameUrlPair
)

@JsonClass(generateAdapter = true)
data class NameUrlPair(
    val name: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class Other(
    val dream_world: DreamWorld,
    @Json(name = "official-artwork") val officialArtwork: OfficialArtwork
)

@JsonClass(generateAdapter = true)
data class Versions(
    @Json(name = "generation-i") val generationI: GenerationI,
    @Json(name = "generation-ii") val generationII: GenerationII,
    @Json(name = "generation-iii") val generationIII: GenerationIII,
    @Json(name = "generation-iv") val generationIV: GenerationIV,
    @Json(name = "generation-v") val generationV: GenerationV,
    @Json(name = "generation-vi") val generationVI: GenerationVI,
    @Json(name = "generation-vii") val generationVII: GenerationVII,
    @Json(name = "generation-viii") val generationVIII: GenerationVIII
)

@JsonClass(generateAdapter = true)
data class DreamWorld(
    val front_default: String?,
    val front_female: String?
)

@JsonClass(generateAdapter = true)
data class OfficialArtwork(
    val front_default: String?
)

@JsonClass(generateAdapter = true)
data class GenerationI(
    @Json(name = "red-blue") val redBlue: RedBlue,
    val yellow: Yellow
)

@JsonClass(generateAdapter = true)
data class GenerationII(
    val crystal: Crystal,
    val gold: Gold,
    val silver: Silver
)

@JsonClass(generateAdapter = true)
data class GenerationIII(
    val emerald: Emerald,
    @Json(name = "firered-leafgreen") val fireredLeafgreen: FireredLeafgreen,
    @Json(name = "ruby-sapphire") val rubySapphire: RubySapphire
)

@JsonClass(generateAdapter = true)
data class GenerationIV(
    @Json(name = "diamond-pearl") val diamondPearl: DiamondPearl,
    @Json(name = "heartgold-soulsilver") val heartgoldSoulsilver: HeartgoldSoulsilver,
    val platinum: Platinum
)

@JsonClass(generateAdapter = true)
data class GenerationV(
    @Json(name = "black-white") val blackWhite: BlackWhite
)

@JsonClass(generateAdapter = true)
data class GenerationVI(
    @Json(name = "omegaruby-alphasapphire") val omegarubyAlphasapphire: OmegarubyAlphasapphire,
    @Json(name = "x-y") val xy: XY
)

@JsonClass(generateAdapter = true)
data class GenerationVII(
    val icons: Icons,
    @Json(name = "ultra-sun-ultra-moon") val ultraSunUltraMoon: UltraSunUltraMoon
)

@JsonClass(generateAdapter = true)
data class GenerationVIII(
    val icons: IconsX
)

@JsonClass(generateAdapter = true)
data class RedBlue(
    val back_default: String?,
    val back_gray: String?,
    val front_default: String?,
    val front_gray: String?
)

@JsonClass(generateAdapter = true)
data class Yellow(
    val back_default: String?,
    val back_gray: String?,
    val front_default: String?,
    val front_gray: String?
)

@JsonClass(generateAdapter = true)
data class Crystal(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?
)

@JsonClass(generateAdapter = true)
data class Gold(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?
)

@JsonClass(generateAdapter = true)
data class Silver(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?
)

@JsonClass(generateAdapter = true)
data class Emerald(
    val front_default: String?,
    val front_shiny: String?
)

@JsonClass(generateAdapter = true)
data class FireredLeafgreen(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?
)

@JsonClass(generateAdapter = true)
data class RubySapphire(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?
)

@JsonClass(generateAdapter = true)
data class DiamondPearl(
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

@JsonClass(generateAdapter = true)
data class HeartgoldSoulsilver(
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

@JsonClass(generateAdapter = true)
data class Platinum(
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

@JsonClass(generateAdapter = true)
data class BlackWhite(
    val animated: Animated,
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

@JsonClass(generateAdapter = true)
data class Animated(
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

@JsonClass(generateAdapter = true)
data class OmegarubyAlphasapphire(
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

@JsonClass(generateAdapter = true)
data class XY(
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

@JsonClass(generateAdapter = true)
data class Icons(
    val front_default: String?,
    val front_female: String?
)

@JsonClass(generateAdapter = true)
data class UltraSunUltraMoon(
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

@JsonClass(generateAdapter = true)
data class IconsX(
    val front_default: String?,
    val front_female: String?
)

@JsonClass(generateAdapter = true)
data class PokemonListDTO(
    val count: Int,
    val next: String?,
    val results: List<NameUrlPair>
)

fun PokemonDTO.asDatabaseModel(): DatabasePokemon {
    return DatabasePokemon(
        id=this.id,
        name=this.name,
        height = this.height,
        weight = this.weight,
        imageUrl = this.sprites.other.officialArtwork.front_default ?: "",
        baseExperience = this.base_experience,
        abilities = this.abilities.map{
            it.ability.name
        },
        baseHP = this.stats[0].base_stat,
        baseAtk = this.stats[1].base_stat,
        baseDfn = this.stats[2].base_stat,
        spAtk = this.stats[3].base_stat,
        spDfn = this.stats[4].base_stat,
        speed = this.stats[5].base_stat,
        types=this.types.map{
            it.type.name
        },
        possibleMoves = this.moves.map{
            it.move.name
        },
        custom=false
    )
}
package com.cis4030.pokedex.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDTO(
    val abilities: List<Ability>,
    val base_experience: Int,
    val forms: List<Form>,
    val game_indices: List<GameIndice>,
    val height: Int,
    val held_items: List<HeldItem>,
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: List<Move>,
    val name: String,
    val order: Int,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int
)

data class Ability(
    val ability: AbilityX,
    val is_hidden: Boolean,
    val slot: Int
)

data class Form(
    val name: String,
    val url: String
)

data class GameIndice(
    val game_index: Int,
    val version: Version
)

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)

data class Species(
    val name: String,
    val url: String
)

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

data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: StatX
)

data class Type(
    val slot: Int,
    val type: TypeX
)

data class AbilityX(
    val name: String,
    val url: String
)

data class Version(
    val name: String,
    val url: String
)

data class Item(
    val name: String,
    val url: String
)

data class VersionDetail(
    val rarity: Int,
    val version: VersionX
)

data class VersionX(
    val name: String,
    val url: String
)

data class MoveX(
    val name: String,
    val url: String
)

data class VersionGroupDetail(
    val level_learned_at: Int,
    val move_learn_method: MoveLearnMethod,
    val version_group: VersionGroup
)

data class MoveLearnMethod(
    val name: String,
    val url: String
)

data class VersionGroup(
    val name: String,
    val url: String
)

data class Other(
    val dream_world: DreamWorld,
    @Json(name = "official-artwork") val officialArtwork: OfficialArtwork
)

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

data class DreamWorld(
    val front_default: String?,
    val front_female: String?
)

data class OfficialArtwork(
    val front_default: String?
)

data class GenerationI(
    @Json(name = "red-blue") val redBlue: RedBlue,
    val yellow: Yellow
)

data class GenerationII(
    val crystal: Crystal,
    val gold: Gold,
    val silver: Silver
)

data class GenerationIII(
    val emerald: Emerald,
    @Json(name = "firered-leafgreen") val fireredLeafgreen: FireredLeafgreen,
    @Json(name = "ruby-sapphire") val rubySapphire: RubySapphire
)

data class GenerationIV(
    @Json(name = "diamond-pearl") val diamondPearl: DiamondPearl,
    @Json(name = "heartgold-soulsilver") val heartgoldSoulsilver: HeartgoldSoulsilver,
    val platinum: Platinum
)

data class GenerationV(
    @Json(name = "black-white") val blackWhite: BlackWhite
)

data class GenerationVI(
    @Json(name = "omegaruby-alphasapphire") val omegarubyAlphasapphire: OmegarubyAlphasapphire,
    @Json(name = "x-y") val xy: XY
)

data class GenerationVII(
    val icons: Icons,
    @Json(name = "ultra-sun-ultra-moon") val ultraSunUltraMoon: UltraSunUltraMoon
)

data class GenerationVIII(
    val icons: IconsX
)

data class RedBlue(
    val back_default: String?,
    val back_gray: String?,
    val front_default: String?,
    val front_gray: String?
)

data class Yellow(
    val back_default: String?,
    val back_gray: String?,
    val front_default: String?,
    val front_gray: String?
)

data class Crystal(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?
)

data class Gold(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?
)

data class Silver(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?
)

data class Emerald(
    val front_default: String?,
    val front_shiny: String?
)

data class FireredLeafgreen(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?
)

data class RubySapphire(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?
)

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

data class OmegarubyAlphasapphire(
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

data class XY(
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

data class Icons(
    val front_default: String?,
    val front_female: String?
)

data class UltraSunUltraMoon(
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

data class IconsX(
    val front_default: String?,
    val front_female: String?
)

data class StatX(
    val name: String,
    val url: String
)

data class TypeX(
    val name: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class PokemonListDTO(
    val count: Int,
    val next: String,
    val results: List<Result>
)

@JsonClass(generateAdapter = true)
data class Result(
    val name: String,
    val url: String
)
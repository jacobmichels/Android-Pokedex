package com.cis4030.pokedex.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Pokemon(val id: Int,
                   val name: String,
                   val height: Int,
                   val weight: Int,
                   val imageUrl:String,
                   val baseExperience: Int,
                   val abilities: List<String>,
                   val baseHP: Int,
                   val baseAtk: Int,
                   val baseDfn: Int,
                   val spAtk: Int,
                   val spDfn: Int,
                   val speed: Int,
                   val types: List<String>,
                   val possibleMoves: List<String>,
                   val custom:Boolean
                   )

@JsonClass(generateAdapter = true)
data class Ability(val name:String,
                   val description:String,
                   val shortDescription:String
)

@JsonClass(generateAdapter = true)
data class Type(val name: String,
                val doubleDamageTo: List<String>,
                val doubleDamageFrom: List<String>,
                val halfDamageTo: List<String>,
                val halfDamageFrom: List<String>,
                val noDamageTo: List<String>,
                val noDamageFrom: List<String>
                )

@JsonClass(generateAdapter = true)
data class Move(val name: String,
                val moveTypeName: String,
                val accuracy: Int,
                val power: Int,
                val specialDamage: Boolean,
                val pp: Int,
                val priority: Int,
                val effects:List<Effect>
                )

@JsonClass(generateAdapter = true)
data class Effect(val description: String,
                  val effectChance: Int?
                  )
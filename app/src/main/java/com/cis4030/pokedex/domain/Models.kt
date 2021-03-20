package com.cis4030.pokedex.domain

data class Pokemon(val id: Int,
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

data class Ability(val id: Int,
                   val name:String,
                   val description:String,
                   val generation: Int
)

data class Type(val id:Int,
                val name: String,
                val strongAgainst: List<Type>,
                val weakAgainst: List<Type>,
                val generation: Int
                )

data class Move(val id:Int,
                val name: String,
                val moveType: Type,
                val accuracy: Int,
                val power: Int,
                val specialDamage: Boolean,
                val generation: Int,
                val pp: Int,
                val priority: Int,
                val effects:List<Effect>
                )

data class Effect(val id:Int,
                  val description: String,
                  val effectChance: Int?
                  )
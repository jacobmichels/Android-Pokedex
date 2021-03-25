package com.cis4030.pokedex.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Effect(val description: String,
                  val effectChance: Int?
                  )
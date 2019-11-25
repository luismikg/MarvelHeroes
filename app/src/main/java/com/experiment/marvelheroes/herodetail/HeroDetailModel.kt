package com.experiment.marvelheroes.herodetail

import java.io.Serializable

data class HeroDetailModel(
    val abilities: String,
    val groups: String,
    val numGrups: String,
    val height: String,
    val name: String,
    val photo: String,
    val power: String,
    val realName: String,
    val isFavorite: Boolean
) : Serializable
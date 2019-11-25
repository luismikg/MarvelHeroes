package com.experiment.marvelheroes.heroes


data class ListHeroesModel(
    val abilities: String,
    val groups: String,
    val numGrups: String,
    val height: String,
    val name: String,
    val photo: String,
    val power: String,
    val realName: String,
    var isFavorite: Boolean
)
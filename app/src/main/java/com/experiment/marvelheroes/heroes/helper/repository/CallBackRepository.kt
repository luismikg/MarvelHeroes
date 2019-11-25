package com.experiment.marvelheroes.heroes.helper.repository

import com.experiment.marvelheroes.heroes.ListHeroesModel

interface CallBackRepository {
    fun callbackSuccess(arrListHeroe: ArrayList<ListHeroesModel>)
    fun callbackError()
}
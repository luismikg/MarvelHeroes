package com.experiment.marvelheroes.heroes.helper.adapter

import android.widget.ImageView
import com.experiment.marvelheroes.heroes.ListHeroesModel

interface CallBackList {
    fun onClickFavorite(dataSource: ArrayList<ListHeroesModel>, position: Int)
    fun onClickItemSelected(
        dataSource: ArrayList<ListHeroesModel>,
        position: Int,
        rootImageView: ImageView
    )
}
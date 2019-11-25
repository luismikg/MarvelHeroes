package com.experiment.marvelheroes.activities.heroesActivity

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.experiment.marvelheroes.R

class HeroesActivityViewModel : ViewModel() {

    fun initActivity(listHeroeActivity: Activity) {

        if (Build.VERSION.SDK_INT >= 21) {
            listHeroeActivity.window.statusBarColor = ContextCompat.getColor(
                listHeroeActivity,
                R.color.colorBackground
            )
        }
    }
}
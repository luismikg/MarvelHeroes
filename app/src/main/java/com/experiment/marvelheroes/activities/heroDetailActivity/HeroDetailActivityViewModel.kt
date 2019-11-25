package com.experiment.marvelheroes.activities.heroDetailActivity

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.experiment.marvelheroes.R

class HeroDetailActivityViewModel : ViewModel() {

    fun initActivity(heroDetailActivity: Activity) {

        (heroDetailActivity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if (Build.VERSION.SDK_INT >= 21) {
            heroDetailActivity.window.statusBarColor = ContextCompat.getColor(
                heroDetailActivity,
                R.color.colorBackground
            )
        }
    }
}
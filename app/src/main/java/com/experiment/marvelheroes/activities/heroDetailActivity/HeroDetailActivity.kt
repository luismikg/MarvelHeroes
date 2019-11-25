package com.experiment.marvelheroes.activities.heroDetailActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.experiment.marvelheroes.R

class HeroDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setContentView(R.layout.activity_hero_detail)
    }

    fun initView() {
        val heroDetailActivityViewModel: HeroDetailActivityViewModel =
            ViewModelProviders.of(this).get(
                HeroDetailActivityViewModel::class.java
            )
        heroDetailActivityViewModel.initActivity(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}

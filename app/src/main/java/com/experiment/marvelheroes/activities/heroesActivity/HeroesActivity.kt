package com.experiment.marvelheroes.activities.heroesActivity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.experiment.marvelheroes.R

class HeroesActivity : AppCompatActivity() {

    companion object {
        const val STORAGE_REQUEST_CODE = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_heroes)
        checkPermissions()
        initView()
    }

    fun initView() {
        val heroeActivityViewModel: HeroesActivityViewModel = ViewModelProviders.of(this).get(
            HeroesActivityViewModel::class.java
        )
        heroeActivityViewModel.initActivity(this)
    }

    //Oly check permissions
    private fun checkPermissions() {
        if ((ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED)
        ) {
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                STORAGE_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isEmpty() or (grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
            checkPermissions()
        } else {
        }
    }
}

package com.experiment.marvelheroes.herodetail

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.experiment.marvelheroes.BR
import com.experiment.marvelheroes.R
import com.experiment.marvelheroes.databinding.FragmentHeroeDetailViewBinding
import com.squareup.picasso.Picasso
import java.lang.Exception

class HeroDetailViewModel : ViewModel() {

    //Observables
    private var observableImageHero: MutableLiveData<Drawable>? = MutableLiveData()
    private var observableTitleDescriptionPower: MutableLiveData<String>? = MutableLiveData()
    private var observableexpandableViewDescriptionPower: MutableLiveData<String>? =
        MutableLiveData()
    private var observableTitleDescriptionAbilities: MutableLiveData<String>? = MutableLiveData()
    private var observableexpandableViewDescriptionAbilities: MutableLiveData<String>? =
        MutableLiveData()
    private var observableTitleDescriptionGroups: MutableLiveData<String>? = MutableLiveData()
    private var observableexpandableViewDescriptionGroups: MutableLiveData<String>? =
        MutableLiveData()

    private lateinit var binding: FragmentHeroeDetailViewBinding
    private lateinit var context: Context

    fun initView(
        context: Context,
        binding: FragmentHeroeDetailViewBinding,
        heroeDetailModel: HeroDetailModel
    ) {
        this.context = context
        this.binding = binding
        this.binding.setVariable(BR.heroDetailModel, heroeDetailModel)
        this.binding.executePendingBindings()

        initPhoto(heroeDetailModel)
        initDescriptions(heroeDetailModel, context)
    }

    //Observable
    fun getImageHero(): LiveData<Drawable>? {
        observableImageHero?.let {
            return it
        }
        return null
    }

    fun getTitleDescriptionPower(): LiveData<String>? {
        observableTitleDescriptionPower?.let {
            return it
        }
        return null
    }

    fun getExpandableViewDescriptionPower(): LiveData<String>? {
        observableexpandableViewDescriptionPower?.let {
            return it
        }
        return null
    }

    fun getTitleDescriptionAbilities(): LiveData<String>? {
        observableTitleDescriptionAbilities?.let {
            return it
        }
        return null
    }

    fun getExpandableViewDescriptionAbilities(): LiveData<String>? {
        observableexpandableViewDescriptionAbilities?.let {
            return it
        }
        return null
    }

    fun getTitleDescriptionGroups(): LiveData<String>? {
        observableTitleDescriptionGroups?.let {
            return it
        }
        return null
    }

    fun getExpandableViewDescriptionGroups(): LiveData<String>? {
        observableexpandableViewDescriptionGroups?.let {
            return it
        }
        return null
    }

    //Change values of UI
    private fun changeImageHero(drawable: Drawable) {
        observableImageHero?.let {
            it.value = drawable
        }
    }

    private fun changeTitleDescriptionPower(string: String) {
        observableTitleDescriptionPower?.let {
            it.value = string
        }
    }

    private fun changeExpandableViewDescriptionPower(string: String) {
        observableexpandableViewDescriptionPower?.let {
            it.value = string
        }
    }

    private fun changeTitleDescriptionAbilities(string: String) {
        observableTitleDescriptionAbilities?.let {
            it.value = string
        }
    }

    private fun changeExpandableViewDescriptionAbilities(string: String) {
        observableexpandableViewDescriptionAbilities?.let {
            it.value = string
        }
    }

    private fun changeTitleDescriptionGroups(string: String) {
        observableTitleDescriptionGroups?.let {
            it.value = string
        }
    }

    private fun changeExpandableViewDescriptionGroups(string: String) {
        observableexpandableViewDescriptionGroups?.let {
            it.value = string
        }
    }

    //ViewModel methods:
    fun initPhoto(heroeDetailModel: HeroDetailModel) {

        val img: ImageView = ImageView(context)
        Picasso.get().load(heroeDetailModel.photo)
            .placeholder(R.drawable.superhero_0)
            .into(img, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                    changeImageHero(img.drawable)
                }

                override fun onError(e: Exception?) {}
            })
    }

    fun initDescriptions(heroeDetailModel: HeroDetailModel, context: Context) {
        changeTitleDescriptionPower(context.getString(R.string.power))
        changeExpandableViewDescriptionPower(heroeDetailModel.power)

        changeTitleDescriptionAbilities(context.getString(R.string.abilities))
        changeExpandableViewDescriptionAbilities(heroeDetailModel.abilities)

        changeTitleDescriptionGroups(context.getString(R.string.groups))
        changeExpandableViewDescriptionGroups(heroeDetailModel.groups)
    }
}
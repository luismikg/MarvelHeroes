package com.experiment.marvelheroes.heroes

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.experiment.marvelheroes.BR
import com.experiment.marvelheroes.heroes.helper.adapter.AdapterHeroes
import com.experiment.marvelheroes.heroes.helper.adapter.CallBackList
import com.experiment.marvelheroes.heroes.helper.repository.ListHeroesRepository
import com.experiment.marvelheroes.activities.heroDetailActivity.HeroDetailActivity
import com.experiment.marvelheroes.R
import com.experiment.marvelheroes.databinding.FragmentHeroesViewBinding
import com.experiment.marvelheroes.herodetail.HeroDetailModel
import com.experiment.marvelheroes.heroes.helper.repository.CallBackRepository
import com.experiment.marvelheroes.heroes.helper.repository.SQL
import com.squareup.picasso.Picasso
import java.io.Serializable
import java.lang.Exception

class ListHeroesViewModel : ViewModel(),
    CallBackRepository, CallBackList {

    //Observables
    private var observableAdapterHeroes: MutableLiveData<AdapterHeroes>? = MutableLiveData()
    private var observableImageHeroSelected: MutableLiveData<Drawable>? = MutableLiveData()
    private var observableLinearLayoutHero: MutableLiveData<Boolean>? = MutableLiveData()
    private var observableTextSelectHero: MutableLiveData<String>? = MutableLiveData()

    private lateinit var binding: FragmentHeroesViewBinding
    private lateinit var originalAdapter: AdapterHeroes
    private lateinit var context: Context

    fun initListHeroes(context: Context, binding: FragmentHeroesViewBinding) {
        this.binding = binding
        this.context = context
        initAdapterHeroes()
    }

    private fun initAdapterHeroes() {

        changeLinearLayoutHero(false)
        //Get data
        val listHeroesRepository: ListHeroesRepository = ListHeroesRepository()
        listHeroesRepository.getDataHeroes(this, context)
    }

    //Change values of UI
    private fun changeAdapterHeroes(adapter: AdapterHeroes) {
        observableAdapterHeroes?.let {
            it.value = adapter
        }
    }

    private fun changeImageHeroSelected(drawable: Drawable) {
        observableImageHeroSelected?.let {
            it.value = drawable
        }
    }

    private fun changeLinearLayoutHero(bool: Boolean) {
        observableLinearLayoutHero?.let {
            it.value = bool
        }
    }

    private fun changeTextSelectHero(string: String) {
        observableTextSelectHero?.let {
            it.value = string
        }
    }

    //Observable
    fun getAdapterHeroes(): LiveData<AdapterHeroes>? {
        observableAdapterHeroes?.let {
            return it
        }
        return null
    }

    fun getImageHeroSelected(): LiveData<Drawable>? {
        observableImageHeroSelected?.let {
            return it
        }
        return null
    }

    fun getLinearLayoutHero(): LiveData<Boolean>? {
        observableLinearLayoutHero?.let {
            return it
        }
        return null
    }

    fun getTextSelectHero(): LiveData<String>? {
        observableTextSelectHero?.let {
            return it
        }
        return null
    }

    //ViewModel methods:
    //Clicks UI:
    fun onClickShowDetailHero(heroesViewFragment: Fragment) {

        binding.listHeroesModel?.let {
            val heroDetailModel = getDataFromHeroSelected(it)

            heroesViewFragment.activity?.let {
                val intent = Intent(it, HeroDetailActivity::class.java)
                intent.putExtra("heroDetailModel", heroDetailModel as Serializable)
                it.startActivity(intent)
                it.overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
            }
        }
    }

    fun onClickShowAllHeroes() {
        changeAdapterHeroes(originalAdapter)

        if (originalAdapter.dataSource.size > 0) {
            enableLayoutHeroBecauseThereAreHeroes(originalAdapter.dataSource)
        } else {
            disableLayoutHeroBecauseThereAreNotHeroes()
        }
    }

    fun onClickShowOnlyFavoritesHeroes() {
        var arrListFavoritesHeroes: ArrayList<ListHeroesModel> = ArrayList()

        for (item in originalAdapter.dataSource) {
            if (item.isFavorite) {
                arrListFavoritesHeroes.add(item)
            }
        }

        val adapter =
            AdapterHeroes(context, arrListFavoritesHeroes, this, enableFavoriteOption = false)
        changeAdapterHeroes(adapter)

        if (arrListFavoritesHeroes.size > 0) {
            enableLayoutHeroBecauseThereAreHeroes(arrListFavoritesHeroes)
        } else {
            disableLayoutHeroBecauseThereAreNotHeroes()
        }
    }

    private fun enableLayoutHeroBecauseThereAreHeroes(arrListHeroe: ArrayList<ListHeroesModel>) {
        setHeroSelected(arrListHeroe[0])
        changeLinearLayoutHero(true)
        changeTextSelectHero(context.getString(R.string.selectHero))
    }

    private fun disableLayoutHeroBecauseThereAreNotHeroes() {
        val img: ImageView = ImageView(context)
        img.setImageResource(R.drawable.superhero_1)
        val listHeroesModel = getElementListHeroesModelEmpty()
        setHeroSelected(listHeroesModel)
        changeLinearLayoutHero(false)

        changeTextSelectHero(context.getString(R.string.noHero))
    }

    fun getDataFromHeroSelected(listHeroesModel: ListHeroesModel): HeroDetailModel {
        val heroDetailModel = HeroDetailModel(
            listHeroesModel.abilities, listHeroesModel.groups,
            listHeroesModel.numGrups, listHeroesModel.height, listHeroesModel.name,
            listHeroesModel.photo, listHeroesModel.power, listHeroesModel.realName,
            listHeroesModel.isFavorite
        )
        return heroDetailModel
    }

    fun getElementListHeroesModelEmpty(): ListHeroesModel {
        val listHeroesModel = ListHeroesModel(
            "N/A", "N/A", "N/A",
            "N/A", "N/A", "N/A",
            "N/A", "N/A", false
        )
        return listHeroesModel
    }

    private fun setHeroSelected(listHeroesModel: ListHeroesModel/*, rootImageView: ImageView?=null*/) {

        val img: ImageView = ImageView(context)
        Picasso.get().load(listHeroesModel.photo)
            .placeholder(R.drawable.superhero_0)
            .into(img, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                    changeImageHeroSelected(img.drawable)
                }

                override fun onError(e: Exception?) {}
            })

        binding.setVariable(BR.listHeroesModel, listHeroesModel)
        binding.executePendingBindings()
    }

    //Interface methods:
    //Interface CallBackRepository methods
    override fun callbackSuccess(arrListHeroe: ArrayList<ListHeroesModel>) {

        if (arrListHeroe.size > 0) {
            enableLayoutHeroBecauseThereAreHeroes(arrListHeroe)
        } else {
            disableLayoutHeroBecauseThereAreNotHeroes()
        }

        originalAdapter = AdapterHeroes(context, arrListHeroe, this)
        changeAdapterHeroes(originalAdapter)
    }

    override fun callbackError() {
        //No DATA
    }

    //Interface CallBackList methods
    override fun onClickFavorite(dataSource: ArrayList<ListHeroesModel>, position: Int) {
        //Save Data base
        if (dataSource[position].isFavorite) {
            SQL(context).INSERT_IF_NOT_EXIST_FAVORITE(dataSource[position].name)
        } else {
            SQL(context).DELETE_FAVORITE(dataSource[position].name)
        }
    }

    override fun onClickItemSelected(
        dataSource: ArrayList<ListHeroesModel>,
        position: Int,
        rootImageView: ImageView
    ) {
        setHeroSelected(dataSource[position])
    }
}
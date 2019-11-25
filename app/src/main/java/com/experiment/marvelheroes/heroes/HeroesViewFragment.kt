package com.experiment.marvelheroes.heroes

import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.experiment.marvelheroes.R
import com.experiment.marvelheroes.heroes.helper.adapter.AdapterHeroes
import com.experiment.marvelheroes.databinding.FragmentHeroesViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * A simple [Fragment] subclass.
 */
class HeroesViewFragment : Fragment() {

    private lateinit var binding: FragmentHeroesViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_heroes_view, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        val listHeroesViewModel = ViewModelProviders.of(this).get(ListHeroesViewModel::class.java)
        initActions(listHeroesViewModel)

        //Set observer & observable to UI
        initList(listHeroesViewModel)
        initImageHeroSelected(listHeroesViewModel)
        initLinearLayoutHero(listHeroesViewModel)
        initTextSelectHero(listHeroesViewModel)

        //Start ViewModel
        this@HeroesViewFragment.context?.let {
            listHeroesViewModel.initListHeroes(it, binding)
        }
    }

    private fun initActions(listHeroesViewModel: ListHeroesViewModel) {
        binding.linearLayoutHero.setOnClickListener {
            listHeroesViewModel.onClickShowDetailHero(this)
        }

        val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.all_heroes -> {
                        listHeroesViewModel.onClickShowAllHeroes()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.favorites -> {
                        listHeroesViewModel.onClickShowOnlyFavoritesHeroes()
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            }
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(
            mOnNavigationItemSelectedListener
        )
    }

    private fun initList(listHeroesViewModel: ListHeroesViewModel) {

        //observer
        val observerListHeroe = Observer<AdapterHeroes> { adapterHeroes ->
            binding.listHeroes.adapter = adapterHeroes
            adapterHeroes.notifyDataSetChanged()
            adapterHeroes.notifyDataSetInvalidated()
        }
        //Set observable
        listHeroesViewModel.getAdapterHeroes()?.let {
            it.observe(this@HeroesViewFragment, observerListHeroe)
        }
    }

    private fun initImageHeroSelected(listHeroesViewModel: ListHeroesViewModel) {

        //observer
        val observerImageHeroSelected = Observer<Drawable> { drawable ->
            binding.imgHeroSelected.setImageDrawable(drawable)
        }
        //Set observable
        listHeroesViewModel.getImageHeroSelected()?.let {
            it.observe(this@HeroesViewFragment, observerImageHeroSelected)
        }
    }

    private fun initLinearLayoutHero(listHeroesViewModel: ListHeroesViewModel) {

        //observer
        val observerLinearLayoutHero = Observer<Boolean> { bool ->
            binding.linearLayoutHero.isEnabled = bool
        }
        //Set observable
        listHeroesViewModel.getLinearLayoutHero()?.let {
            it.observe(this@HeroesViewFragment, observerLinearLayoutHero)
        }
    }

    private fun initTextSelectHero(listHeroesViewModel: ListHeroesViewModel) {

        //observer
        val observerTextSelectHero = Observer<String> { string ->
            binding.txtSelectHero.text = string
        }
        //Set observable
        listHeroesViewModel.getTextSelectHero()?.let {
            it.observe(this@HeroesViewFragment, observerTextSelectHero)
        }
    }
}

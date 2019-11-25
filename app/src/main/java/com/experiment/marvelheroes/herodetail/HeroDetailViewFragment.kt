package com.experiment.marvelheroes.herodetail

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.experiment.marvelheroes.R
import com.experiment.marvelheroes.activities.heroesActivity.HeroesActivity
import com.experiment.marvelheroes.activities.heroesActivity.HeroesActivityViewModel
import com.experiment.marvelheroes.databinding.FragmentHeroeDetailViewBinding
import com.ms.square.android.expandabletextview.ExpandableTextView


class HeroDetailViewFragment : Fragment() {

    private lateinit var binding: FragmentHeroeDetailViewBinding
    private lateinit var heroDetailModel: HeroDetailModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_heroe_detail_view, container, false)
        initView()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun initView() {
        val heroDetailViewModel = ViewModelProviders.of(this).get(HeroDetailViewModel::class.java)

        activity?.let {
            if (it is HeroesActivity) {
                return
            }
            heroDetailModel = it.intent.extras.getSerializable("heroDetailModel") as HeroDetailModel
        }


        initImageHero(heroDetailViewModel)
        initDescriptionPower(heroDetailViewModel)
        initDescriptionAbilities(heroDetailViewModel)
        initDescriptionGroups(heroDetailViewModel)

        this@HeroDetailViewFragment.context?.let {
            heroDetailViewModel.initView(it, binding, heroDetailModel)
        }
    }

    private fun initImageHero(heroDetailViewModel: HeroDetailViewModel) {

        //observer
        val observerImageHero = Observer<Drawable> { drawable ->
            binding.heroPicture.setImageDrawable(drawable)
        }
        //Set observable
        heroDetailViewModel.getImageHero()?.let {
            it.observe(this@HeroDetailViewFragment, observerImageHero)
        }
    }

    private fun initDescriptionPower(heroDetailViewModel: HeroDetailViewModel) {

        //observer Title Power
        val observerTitleDescriptionPower = Observer<String> { string ->
            binding.power.findViewById<TextView>(R.id.titleDescription).text = string
        }
        //Set observable Title Power
        heroDetailViewModel.getTitleDescriptionPower()?.let {
            it.observe(this@HeroDetailViewFragment, observerTitleDescriptionPower)
        }

        //observer description Power
        val observerExpandableViewDescriptionPower = Observer<String> { string ->
            binding.power.findViewById<ExpandableTextView>(R.id.expand_text_view).text = string
        }
        //Set observable description Power
        heroDetailViewModel.getExpandableViewDescriptionPower()?.let {
            it.observe(this@HeroDetailViewFragment, observerExpandableViewDescriptionPower)
        }
    }

    private fun initDescriptionAbilities(heroDetailViewModel: HeroDetailViewModel) {

        //observer Title Abilities
        val observerTitleDescriptionAbilities = Observer<String> { string ->
            binding.abilities.findViewById<TextView>(R.id.titleDescription).text = string
        }
        //Set observable Title Abilities
        heroDetailViewModel.getTitleDescriptionAbilities()?.let {
            it.observe(this@HeroDetailViewFragment, observerTitleDescriptionAbilities)
        }

        //observer description Abilities
        val observerExpandableViewDescriptionAbilities = Observer<String> { string ->
            binding.abilities.findViewById<ExpandableTextView>(R.id.expand_text_view).text = string
        }
        //Set observable description Abilities
        heroDetailViewModel.getExpandableViewDescriptionAbilities()?.let {
            it.observe(this@HeroDetailViewFragment, observerExpandableViewDescriptionAbilities)
        }
    }

    private fun initDescriptionGroups(heroDetailViewModel: HeroDetailViewModel) {

        //observer Title Grupos
        val observerTitleDescriptionGroups = Observer<String> { string ->
            binding.groups.findViewById<TextView>(R.id.titleDescription).text = string
        }
        //Set observable Title Grupos
        heroDetailViewModel.getTitleDescriptionGroups()?.let {
            it.observe(this@HeroDetailViewFragment, observerTitleDescriptionGroups)
        }

        //observer description Grupos
        val observerExpandableViewDescriptiongGroups = Observer<String> { string ->
            binding.groups.findViewById<ExpandableTextView>(R.id.expand_text_view).text = string
        }
        //Set observable description Grupos
        heroDetailViewModel.getExpandableViewDescriptionGroups()?.let {
            it.observe(this@HeroDetailViewFragment, observerExpandableViewDescriptiongGroups)
        }
    }

    fun shareSuccess() {
        startActivity(getShereIntent())
    }

    fun getShereIntent(): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text, heroDetailModel.name))
        return shareIntent

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_share, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }


}
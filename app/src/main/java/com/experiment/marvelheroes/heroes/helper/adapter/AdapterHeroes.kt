package com.experiment.marvelheroes.heroes.helper.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import com.experiment.marvelheroes.heroes.ListHeroesModel
import com.experiment.marvelheroes.heroes.ListHeroesViewModel
import com.experiment.marvelheroes.R
import com.squareup.picasso.Picasso

class AdapterHeroes(
    val context: Context,
    val dataSource: ArrayList<ListHeroesModel>,
    callBackList: CallBackList,
    val enableFavoriteOption: Boolean = true
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val callBackList = (callBackList as ListHeroesViewModel)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val rowView = inflater.inflate(R.layout.list_item_heroe, parent, false)

        val txtName = rowView.findViewById<TextView>(R.id.name)
        txtName.text = dataSource[position].name

        val txtRealName = rowView.findViewById<TextView>(R.id.realName)
        txtRealName.text = dataSource[position].realName

        val txtHeight = rowView.findViewById<TextView>(R.id.height)
        txtHeight.text = dataSource[position].height

        val imgPhoto = rowView.findViewById<ImageView>(R.id.photo)

        Picasso.get().load(dataSource[position].photo)
            .placeholder(R.drawable.superhero_0)
            .into(imgPhoto)

        val tBtnIsAddToFavorite = rowView.findViewById<ToggleButton>(R.id.isAddToFavorite)
        tBtnIsAddToFavorite.isChecked = dataSource[position].isFavorite
        tBtnIsAddToFavorite.isEnabled = enableFavoriteOption

        tBtnIsAddToFavorite.setOnClickListener {
            dataSource[position].isFavorite = !dataSource[position].isFavorite
            callBackList.onClickFavorite(dataSource, position)
        }

        rowView.setOnClickListener {
            val img = rowView.findViewById<ImageView>(R.id.photo)
            callBackList.onClickItemSelected(dataSource, position, img)
        }

        return rowView
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position * 1.0.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }
}
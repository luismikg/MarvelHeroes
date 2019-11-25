package com.experiment.marvelheroes.heroes.helper.repository

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.experiment.marvelheroes.heroes.ListHeroesModel
import com.experiment.marvelheroes.heroes.ListHeroesViewModel
import org.json.JSONArray
import org.json.JSONObject
import kotlin.collections.ArrayList

class ListHeroesRepository {

    fun getDataHeroes(callBackRepository: CallBackRepository, context: Context) {

        val url = "https://api.myjson.com/bins/bvyob"

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->

                //Success
                var response: String = "%s".format(response.toString())
                response = response.replace("'", "")
                //Save into cache
                SQL(context).INSERT_JSON(response)

                this.success(context, response, callBackRepository)
            },
            Response.ErrorListener { error ->

                // TODO: Handle error
                //SEARCH INTO SQL CACHE
                var strJson: String? = SQL(context).SELECT_JSON()
                this.error(context, strJson, callBackRepository)
            }
        )

        queue.add(jsonObjectRequest)
    }

    fun error(context: Context, strJson: String?, callBackRepository: CallBackRepository) {
        if (strJson == null) {
            (callBackRepository as ListHeroesViewModel).callbackError()
        } else {
            success(context, strJson, callBackRepository)
        }
    }

    fun success(context: Context, strJson: String, callBackRepository: CallBackRepository) {
        val arrListHeroe: ArrayList<ListHeroesModel> = getData(context, strJson)
        (callBackRepository as ListHeroesViewModel).callbackSuccess(arrListHeroe)
    }

    fun getData(context: Context, strJson: String): ArrayList<ListHeroesModel> {
        val json: JSONObject = JSONObject(strJson)
        val jsonArray = (json["superheroes"] as JSONArray)
        val arrListHeroe: ArrayList<ListHeroesModel> = ArrayList()

        for (i in 0 until jsonArray.length()) {
            val json = jsonArray.getJSONObject(i)

            val isFavorite = SQL(context).SELECT_FAVORITE(json["name"] as String)
            val favorite = (isFavorite != null)

            val listHeroesModel = ListHeroesModel(
                json["abilities"] as String, json["groups"] as String,
                (json["groups"] as String).split(",").size.toString(),
                json["height"] as String, json["name"] as String,
                json["photo"] as String, json["power"] as String,
                json["realName"] as String,
                favorite
            )
            arrListHeroe.add(listHeroesModel)
        }

        return arrListHeroe
    }

}


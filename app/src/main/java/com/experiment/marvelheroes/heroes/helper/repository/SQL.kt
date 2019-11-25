package com.experiment.marvelheroes.heroes.helper.repository

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class SQL(context: Context) :
    SQLiteOpenHelper(context, NAME_DB, null, DATABASE_VER) {

    companion object {
        val DATABASE_VER = 1
        val NAME_DB = "marvel_heroe.db"
        val NAME_TABLE = "cahe"
        val NAME_TABLE_FAVORITES = "favorite"
        val CREATE_TABLE_CACHE =
            "CREATE TABLE IF NOT EXISTS ${SQL.NAME_TABLE} (json VARCHAR)"
        val CREATE_TABLE_FAVORITES =
            "CREATE TABLE IF NOT EXISTS ${SQL.NAME_TABLE_FAVORITES} (name VARCHAR)"
    }

    public fun INSERT_JSON(json: String) {
        DELETE_JSON()

        var db: SQLiteDatabase = this@SQL.writableDatabase
        val insert: String = "INSERT INTO ${SQL.NAME_TABLE} (json) VALUES('$json')"
        db.execSQL(insert)
    }

    private fun DELETE_JSON() {
        var db: SQLiteDatabase = this@SQL.writableDatabase
        val delete: String = "DELETE FROM ${SQL.NAME_TABLE}"
        db.execSQL(delete)
    }

    fun SELECT_JSON(): String? {
        var db: SQLiteDatabase = this@SQL.writableDatabase
        val select = "SELECT json FROM ${SQL.NAME_TABLE}"
        val cursor: Cursor = db.rawQuery(select, null)

        if (cursor.moveToFirst()) {
            return cursor.getString(0)
        } else {
            return null
        }
    }

    fun INSERT_IF_NOT_EXIST_FAVORITE(name: String) {
        val isHere: String? = this@SQL.SELECT_FAVORITE(name)
        if (isHere == null) {
            this@SQL.INSERT_FAVORITE(name)
        }
    }

    fun SELECT_FAVORITE(name: String): String? {
        var db: SQLiteDatabase = this@SQL.writableDatabase
        val select = "SELECT name FROM ${SQL.NAME_TABLE_FAVORITES} WHERE name='$name'"
        val cursor: Cursor = db.rawQuery(select, null)

        if (cursor.moveToFirst()) {
            return cursor.getString(0)
        } else {
            return null
        }
    }

    private fun INSERT_FAVORITE(name: String) {
        var db: SQLiteDatabase = this@SQL.writableDatabase
        val insert: String = "INSERT INTO ${SQL.NAME_TABLE_FAVORITES} (name) VALUES('$name')"
        db.execSQL(insert)
    }

    fun DELETE_FAVORITE(name: String) {
        var db: SQLiteDatabase = this@SQL.writableDatabase
        val delete: String = "DELETE FROM ${SQL.NAME_TABLE_FAVORITES} WHERE name='$name'"
        db.execSQL(delete)
    }

    override fun onCreate(database: SQLiteDatabase?) {
        database?.let {
            it.execSQL(SQL.CREATE_TABLE_CACHE)
        }

        database?.let {
            it.execSQL(SQL.CREATE_TABLE_FAVORITES)
        }
    }

    override fun onUpgrade(database: SQLiteDatabase?, p1: Int, p2: Int) {
        database?.let {
            onCreate(it)
        }
    }

}
package com.example.projetandroid

import android.app.Application
import android.content.Context

class ShardPref(application: Application) {
    val TOKENNAME = "T"
    val REFRECHTOKEN = "RF"
    val SHARDNAME = "TOKEN"
    val shardPreferences = application.getSharedPreferences(SHARDNAME, Context.MODE_PRIVATE)


    fun putToken(token: String, refreshToken: String) {
        //// shardPreferences.edit().putString(TOKENNAME, token).putString(REFRECHTOKEN, refreshToken).apply()
        with(shardPreferences.edit()) {
            putString(TOKENNAME, token)
            putString(REFRECHTOKEN, refreshToken)
            apply()
        }
    }
    fun getToken(): String {
        val token = shardPreferences.getString(TOKENNAME, "")
        if (token.isNullOrEmpty())
            throw RuntimeException("token not exists")
        return token
    }

    fun getRefreshToken(): String {
        val token = shardPreferences.getString(REFRECHTOKEN, "")
        if (token.isNullOrEmpty())
            throw RuntimeException("token not exists")
        return token
    }


}
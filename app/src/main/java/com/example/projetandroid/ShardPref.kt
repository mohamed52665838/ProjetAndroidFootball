package com.example.projetandroid

import android.app.Application
import android.content.Context
import com.example.projetandroid.data_layer.network.api.TokenAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShardPref @Inject constructor(
    private val application: Application,
    private val tokenAPI: TokenAPI
) {
    val TOKENNAME = "T"
    val REFRECHTOKEN = "RF"
    val SHARDNAME = "TOKEN"
    val shardPreferences = application.getSharedPreferences(SHARDNAME, Context.MODE_PRIVATE)


    fun putToken(token: String, refreshToken: String) {
        //// shardPreferences.edit().putString(TOKENNAME, token).putString(REFRECHTOKEN, refreshToken).apply()
        with(shardPreferences.edit()) {
            putString(TOKENNAME, TOKEN_TYPE + token)
            putString(REFRECHTOKEN, TOKEN_TYPE + refreshToken)
            apply()
        }
    }

    fun getToken(): String {
        val token = shardPreferences.getString(TOKENNAME, "")
        if (token.isNullOrEmpty())
            throw RuntimeException("Unauthorized request, please login again")
        return token
    }

    fun clearToken() {
        shardPreferences.edit().remove(TOKENNAME).remove(REFRECHTOKEN).apply()
    }

    fun getRefreshToken(): String {
        val token = shardPreferences.getString(REFRECHTOKEN, "")
        if (token.isNullOrEmpty())
            throw RuntimeException("Unauthorized request, please login again")
        return token
    }


    suspend fun resolveExpiredToken(): String {
        // may throw exception
        val refreshToken = getRefreshToken()
        val response = tokenAPI.refreshToken(refreshToken)
        if (response.isSuccessful) {
            response.body()?.data?.let {
                putToken(it.accessToken, it.refreshToken)
                return TOKEN_TYPE + it.accessToken
            } ?: run {
                throw RuntimeException("Unauthorized request, please login again")
            }
        } else {
            throw RuntimeException("Unauthorized request, please login again")
        }


    }


}
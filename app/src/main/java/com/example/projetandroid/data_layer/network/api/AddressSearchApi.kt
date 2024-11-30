package com.example.projetandroid.data_layer.network.api

import com.example.projetandroid.model.AddressSearchJson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface AddressSearchApi {

    @GET("/search")
    suspend fun searchAddress(
        @Query("city") city: String, // city the only variable right now
        @Query("limit") count: Int = 5,
        @Query("accept-language") lang: String = "en,fr",
        @Query("format") format: String = "json",
        @Query("countrycodes") code: String = "TN",
        @Header("User-Agent") agent: String = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:132.0) Gecko/20100101 Firefox/132.0"
    ): Response<List<AddressSearchJson>?>

}


// http://fafafafa.com/ -> endpoint


//
// API ( program ready to use )
// C/C++
//
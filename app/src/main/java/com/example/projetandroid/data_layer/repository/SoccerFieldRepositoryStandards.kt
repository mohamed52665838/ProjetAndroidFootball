package com.example.projetandroid.data_layer.repository

import com.example.projetandroid.Events
import com.example.projetandroid.model.ResponseType
import com.example.projetandroid.model.SoccerField
import com.google.android.gms.common.api.Response
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.POST

interface SoccerFieldRepositoryStandards {

    fun create(token: String, soccerField: SoccerField): Flow<Events<SoccerField?>>
    fun update(token: String, soccerField: SoccerField): Flow<Events<SoccerField?>>
    fun delete(token: String, soccerField: SoccerField): Flow<Events<SoccerField?>>
    fun own(token: String): Flow<Events<SoccerField?>>
    fun allSoccerFields(): Flow<Events<List<SoccerField>?>>


}
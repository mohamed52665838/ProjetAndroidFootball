package com.example.projetandroid.data_layer.repository

import com.example.projetandroid.Events
import com.example.projetandroid.model.ResponseType
import com.example.projetandroid.model.SoccerField
import com.google.android.gms.common.api.Response
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.POST

interface SoccerFieldRepositoryStandards {

    fun create(soccerField: SoccerField): Flow<Events<SoccerField>>
    fun update(soccerField: SoccerField): Flow<Events<SoccerField>>
    fun delete(soccerField: SoccerField): Flow<Events<SoccerField>>
    fun own(): Flow<Events<SoccerField>>
    fun allSoccerFields(): Flow<Events<List<SoccerField>>>


}
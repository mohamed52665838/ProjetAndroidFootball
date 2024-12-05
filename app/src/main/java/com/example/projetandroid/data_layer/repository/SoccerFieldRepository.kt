package com.example.projetandroid.data_layer.repository

import com.example.projetandroid.Events
import com.example.projetandroid.ShardPref
import com.example.projetandroid.TOKEN_TYPE
import com.example.projetandroid.data_layer.network.api.SoccerFieldAPI
import com.example.projetandroid.fromStateCodeToDeveloperMessage
import com.example.projetandroid.model.SoccerField
import com.example.projetandroid.runNotAuthenticatedRequest
import com.example.projetandroid.runRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SoccerFieldRepository @Inject constructor(
    private val SoccerFieldAPI: SoccerFieldAPI,
    private val pref: ShardPref
) : SoccerFieldRepositoryStandards {
    override fun create(soccerField: SoccerField): Flow<Events<SoccerField>> =
        runRequest(pref = pref) {
            SoccerFieldAPI.create(it, soccerField)
        }

    override fun update(soccerField: SoccerField): Flow<Events<SoccerField>> =
        runRequest(pref = pref) {
            SoccerFieldAPI.update(it, soccerField)
        }


    // not implemented for security raison :|
    override fun delete(soccerField: SoccerField): Flow<Events<SoccerField>> {
        TODO()
    }


    override fun own(): Flow<Events<SoccerField>> = runRequest(pref = pref) {
        SoccerFieldAPI.own(it)
    }

    override fun allSoccerFields(): Flow<Events<List<SoccerField>>> = runNotAuthenticatedRequest {
        SoccerFieldAPI.allSoccerFields()
    }

}
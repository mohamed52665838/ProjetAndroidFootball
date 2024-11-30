package com.example.projetandroid.data_layer.repository

import com.example.projetandroid.Events
import com.example.projetandroid.TOKEN_TYPE
import com.example.projetandroid.data_layer.network.api.SoccerFieldAPI
import com.example.projetandroid.fromStateCodeToDeveloperMessage
import com.example.projetandroid.model.SoccerField
import com.example.projetandroid.runRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SoccerFieldRepository @Inject constructor(
    private val SoccerFieldAPI: SoccerFieldAPI
) : SoccerFieldRepositoryStandards {
    override fun create(token: String, soccerField: SoccerField): Flow<Events<SoccerField>> =
        runRequest {
            SoccerFieldAPI.create(token, soccerField)
        }

    override fun update(token: String, soccerField: SoccerField): Flow<Events<SoccerField>> =
        runRequest {
            SoccerFieldAPI.update(token, soccerField)
        }


    // not implemented for security raison :|
    override fun delete(token: String, soccerField: SoccerField): Flow<Events<SoccerField>> {
        TODO("Not yet implemented")
    }


    override fun own(token: String): Flow<Events<SoccerField>> = runRequest {
        SoccerFieldAPI.own(token)
    }

    override fun allSoccerFields(): Flow<Events<List<SoccerField>>> = runRequest {
        SoccerFieldAPI.allSoccerFields()
    }

}
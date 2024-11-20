package com.example.projetandroid.data_layer.repository

import com.example.projetandroid.Events
import com.example.projetandroid.data_layer.network.api.AddressSearchApi
import com.example.projetandroid.fromStateCodeToDeveloperMessage
import com.example.projetandroid.model.AddressSearchJson
import com.example.projetandroid.model.SignUpModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AddressLookupRepository @Inject constructor(
    private val addressSearchApi: AddressSearchApi
) : AddressLookupRepositoryStandards {
    override fun getAddressByCity(city: String): Flow<Events<List<AddressSearchJson>>> = flow {
        emit(Events.LoadingEvent(data = null))
        val response = addressSearchApi.searchAddress(city)

        if (!response.isSuccessful) {
            emit(
                Events.ErrorEvent(
                    error = fromStateCodeToDeveloperMessage(response.code())
                )
            )
        } else {
            response.body()?.let { responseType ->
                emit(Events.SuccessEvent(data = responseType))
            }
        }
    }
}
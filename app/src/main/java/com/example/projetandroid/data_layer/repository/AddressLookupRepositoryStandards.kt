package com.example.projetandroid.data_layer.repository

import com.example.projetandroid.Events
import com.example.projetandroid.model.AddressSearchJson
import kotlinx.coroutines.flow.Flow

interface AddressLookupRepositoryStandards {
    fun getAddressByCity(city: String): Flow<Events<List<AddressSearchJson>>> // max return 5
}
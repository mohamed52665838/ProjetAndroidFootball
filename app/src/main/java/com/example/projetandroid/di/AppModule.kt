package com.example.projetandroid.di

import android.app.Application
import com.example.projetandroid.ShardPref
import com.example.projetandroid.data_layer.network.RetrofitInstance
import com.example.projetandroid.data_layer.network.api.AddressSearchApi
import com.example.projetandroid.data_layer.network.api.SoccerFieldAPI
import com.example.projetandroid.data_layer.network.api.UserAPI
import com.example.projetandroid.data_layer.repository.AddressLookupRepository
import com.example.projetandroid.data_layer.repository.AddressLookupRepositoryStandards
import com.example.projetandroid.data_layer.repository.UserRepository
import com.example.projetandroid.data_layer.repository.UserRepositoryStandards
import com.example.projetandroid.ui_layer.viewModels.manager_viewModels.AddSoccerFieldViewModelBase
import com.example.projetandroid.ui_layer.viewModels.manager_viewModels.AddSoccerFieldViewModelImp
import com.example.projetandroid.ui_layer.viewModels.manager_viewModels.AddSoccerFieldViewModelProtocol
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun getUserAPI(): UserAPI {
        return RetrofitInstance.getInstance().create(UserAPI::class.java)
    }

    @Provides
    fun getUserRepository(userAPI: UserAPI): UserRepositoryStandards {
        return UserRepository(userAPI)
    }


    @Provides
    fun getAddressLookupRepository(addressSearchApi: AddressSearchApi): AddressLookupRepositoryStandards {
        return AddressLookupRepository(addressSearchApi)
    }

    @Provides
    fun getAddressLookupAPI(): AddressSearchApi {
        return RetrofitInstance.getAddressLockupRetrofitInstance()
            .create(AddressSearchApi::class.java)
    }


    @Provides
    fun getShardPref(application: Application): ShardPref {
        return ShardPref(application)
    }


    @Provides
    fun getSoccerFieldAPI(): SoccerFieldAPI {
        return RetrofitInstance.getInstance().create(SoccerFieldAPI::class.java)
    }

}
package com.example.projetandroid.di

import android.app.Application
import com.example.projetandroid.ShardPref
import com.example.projetandroid.TOKEN_TYPE
import com.example.projetandroid.data_layer.network.RetrofitInstance
import com.example.projetandroid.data_layer.network.api.AddressSearchApi
import com.example.projetandroid.data_layer.network.api.MatchAPI
import com.example.projetandroid.data_layer.network.api.SoccerFieldAPI
import com.example.projetandroid.data_layer.network.api.TokenAPI
import com.example.projetandroid.data_layer.network.api.UserAPI
import com.example.projetandroid.data_layer.repository.AddressLookupRepository
import com.example.projetandroid.data_layer.repository.AddressLookupRepositoryStandards
import com.example.projetandroid.data_layer.repository.MatchRepository
import com.example.projetandroid.data_layer.repository.MatchRepositoryStandards
import com.example.projetandroid.data_layer.repository.UserRepository
import com.example.projetandroid.data_layer.repository.UserRepositoryStandards
import com.example.projetandroid.ui_layer.event.EventsBus
import com.example.projetandroid.ui_layer.viewModels.manager_viewModels.AddSoccerFieldViewModelBase
import com.example.projetandroid.ui_layer.viewModels.manager_viewModels.AddSoccerFieldViewModelImp
import com.example.projetandroid.ui_layer.viewModels.manager_viewModels.AddSoccerFieldViewModelProtocol
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun getUserAPI(): UserAPI {
        return RetrofitInstance.getInstance().create(UserAPI::class.java)
    }

    @Provides
    fun getMatchAPI(): MatchAPI {
        return RetrofitInstance.getInstance().newBuilder()
            .addConverterFactory(GsonConverterFactory.create()).build().create(MatchAPI::class.java)
    }

    @Provides
    fun getUserRepository(userAPI: UserAPI, pref: ShardPref): UserRepositoryStandards {
        return UserRepository(userAPI, pref)
    }

    @Provides
    fun getMatchRepository(matchAPI: MatchAPI, pref: ShardPref): MatchRepositoryStandards {
        return MatchRepository(matchAPI, pref)
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
    fun getTokenApi(): TokenAPI {
        return RetrofitInstance.getInstance().create(TokenAPI::class.java)
    }

    @Provides
    fun getShardPref(application: Application, tokenAPI: TokenAPI): ShardPref {
        return ShardPref(application, tokenAPI)
    }


    @Provides
    fun getSoccerFieldAPI(): SoccerFieldAPI {
        return RetrofitInstance.getInstance().create(SoccerFieldAPI::class.java)
    }


    @Provides
    fun getEventBus() = EventsBus

}
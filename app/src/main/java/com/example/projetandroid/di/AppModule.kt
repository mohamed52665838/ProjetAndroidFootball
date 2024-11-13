package com.example.projetandroid.di

import android.app.Application
import com.example.projetandroid.ShardPref
import com.example.projetandroid.data_layer.network.RetrofitInstance
import com.example.projetandroid.data_layer.network.api.UserAPI
import com.example.projetandroid.data_layer.repository.UserRepository
import com.example.projetandroid.data_layer.repository.UserRepositoryStandards
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
    fun getShardPref(application: Application): ShardPref {
        return ShardPref(application)
    }

}
package com.example.projetandroid.di

import com.example.projetandroid.ui_layer.viewModels.manager_viewModels.AddSoccerFieldViewModelBase
import com.example.projetandroid.ui_layer.viewModels.manager_viewModels.AddSoccerFieldViewModelImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class AppModelInjectors {
    @Binds
    abstract fun addSoccerFieldViewModel(addSoccerFieldViewModel: AddSoccerFieldViewModelImp): AddSoccerFieldViewModelBase
}
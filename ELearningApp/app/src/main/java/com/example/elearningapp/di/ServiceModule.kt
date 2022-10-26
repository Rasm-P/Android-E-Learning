package com.example.elearningapp.di

import com.example.elearningapp.repositories.LoginRepository
import com.example.elearningapp.repositories.LoginRepositoryInterface
import dagger.Module
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppServiceModule {

    @Binds
    abstract fun bindLoginRepository(
        loginRepository: LoginRepository
    ): LoginRepositoryInterface

}
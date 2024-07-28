package com.life.plus.tv.di

import com.life.plus.tv.data.repo.MainRepoImpl
import com.life.plus.tv.domain.repo.MainRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BindModule {

    @Binds
    @Singleton
    fun bindMainRepo(mainRepoImpl: MainRepoImpl): MainRepo


}
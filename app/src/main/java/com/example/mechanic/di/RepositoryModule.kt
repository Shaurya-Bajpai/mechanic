package com.example.mechanic.di

import com.example.mechanic.data.remote.MechanicApi
import com.example.mechanic.data.repository.MechanicRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMechanicRepository(api: MechanicApi): MechanicRepository {
        return MechanicRepository(api)
    }
}

package com.kazantsev.data.di

import com.kazantsev.data.preference.DataStoreManagerImpl
import com.kazantsev.domain.repository.DataStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DataStore {
    @Singleton
    @Binds
    fun bindsDataStore( DataStore: DataStoreManagerImpl): DataStore
}
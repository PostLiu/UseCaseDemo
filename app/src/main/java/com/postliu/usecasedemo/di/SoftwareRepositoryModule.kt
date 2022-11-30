package com.postliu.usecasedemo.di

import com.postliu.usecasedemo.data.source.DefaultSoftwareRepository
import com.postliu.usecasedemo.data.source.SoftwareDataSource
import com.postliu.usecasedemo.data.source.SoftwareRepository
import com.postliu.usecasedemo.data.source.local.SoftwareDao
import com.postliu.usecasedemo.data.source.local.SoftwareLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SoftwareRepositoryModule {

    @Singleton
    @Provides
    fun providesSoftwareDataSource(dao: SoftwareDao): SoftwareDataSource {
        return SoftwareLocalDataSource(dao)
    }

    @Singleton
    @Provides
    fun providesSoftwareRepository(dataSource: SoftwareDataSource): SoftwareRepository {
        return DefaultSoftwareRepository(dataSource)
    }
}
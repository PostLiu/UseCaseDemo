package com.postliu.usecasedemo.di

import android.content.Context
import androidx.room.Room
import com.postliu.usecasedemo.data.source.local.SoftwareDao
import com.postliu.usecasedemo.data.source.local.SoftwareDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SoftwareDatabaseModule {

    @Singleton
    @Provides
    fun providesSoftwareDatabase(@ApplicationContext context: Context): SoftwareDatabase {
        return Room.databaseBuilder(context, SoftwareDatabase::class.java, "software.db").build()
    }

    @Singleton
    @Provides
    fun providesSoftwareDao(database: SoftwareDatabase): SoftwareDao {
        return database.softwareDao()
    }
}
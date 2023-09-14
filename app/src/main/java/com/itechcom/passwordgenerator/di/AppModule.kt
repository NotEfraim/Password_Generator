package com.itechcom.passwordgenerator.di

import android.content.Context
import com.itechcom.passwordgenerator.storage.room.RoomDBManager
import com.itechcom.passwordgenerator.storage.sharedPref.SharedPrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRoomDBManager(@ApplicationContext context: Context) : RoomDBManager {
        return RoomDBManager(context)
    }

    @Provides
    @Singleton
    fun provideSharedPrefManager(@ApplicationContext context: Context) : SharedPrefManager {
        return SharedPrefManager(context)
    }
}
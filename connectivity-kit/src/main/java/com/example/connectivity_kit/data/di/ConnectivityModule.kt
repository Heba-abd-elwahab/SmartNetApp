package com.example.connectivity_kit.data.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.connectivity_kit.data.ConnectivityRepositoryImpl
import com.example.connectivity_kit.domain.ConnectivityRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ConnectivityModule {
    @Binds
    @Singleton
    abstract fun bindConnectivityRepository(
        impl: ConnectivityRepositoryImpl
    ): ConnectivityRepository

    companion object {
        @Provides
        @Singleton
        fun provideConnectivityManager(
            @ApplicationContext context: Context
        ): ConnectivityManager {
            return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }
    }
}

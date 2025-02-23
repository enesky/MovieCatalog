package dev.enesky.core.common.connectivity.di

import android.content.Context
import dev.enesky.core.common.connectivity.ConnectivityListener
import dev.enesky.core.common.connectivity.ConnectivityListenerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ConnectivityModule {

    @Provides
    @Singleton
    fun provideConnectivityListener(
        @ApplicationContext context: Context,
    ): ConnectivityListener = ConnectivityListenerImpl(context)
}

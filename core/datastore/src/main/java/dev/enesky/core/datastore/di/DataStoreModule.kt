package dev.enesky.core.datastore.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.enesky.core.datastore.MovieDataStoreHelper
import dev.enesky.core.datastore.MovieDataStoreHelperImpl
import dev.enesky.core.domain.mapper.DataStoreDtoMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {

    @Provides
    @Singleton
    fun provideMovieDataStoreHelper(
        @ApplicationContext context: Context,
        dataStoreDtoMapper: DataStoreDtoMapper
    ): MovieDataStoreHelper = MovieDataStoreHelperImpl(context, dataStoreDtoMapper)
}

package dev.enesky.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.enesky.core.network.api.MovieDbApi
import dev.enesky.core.network.repository.MovieDbDataSource
import dev.enesky.core.network.repository.MovieDbDataSourceImpl
import dev.enesky.core.network.repository.MovieRepository
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieDbApi(retrofit: Retrofit): MovieDbApi =
        retrofit.create(MovieDbApi::class.java)

    @Provides
    @Singleton
    fun provideMovieDbDataSource(movieDbApi: MovieDbApi): MovieDbDataSource =
        MovieDbDataSourceImpl(movieDbApi)

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieDbDataSource: MovieDbDataSource
    ): MovieRepository = MovieRepository(movieDbDataSource)
}

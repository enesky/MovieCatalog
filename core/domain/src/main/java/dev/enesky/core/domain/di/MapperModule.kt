package dev.enesky.core.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.enesky.core.domain.mapper.MovieMapper
import dev.enesky.core.domain.mapper.MovieDetailMapper
import dev.enesky.core.domain.mapper.MovieToDetailMapper

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */
@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun provideMovieMapper(): MovieMapper = MovieMapper()

    @Provides
    fun provideMovieDetailMapper(): MovieDetailMapper = MovieDetailMapper()

    @Provides
    fun provideMovieToDetailMapper(): MovieToDetailMapper = MovieToDetailMapper()
}

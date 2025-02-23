package dev.enesky.core.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.enesky.core.domain.mapper.MovieDetailMapper
import dev.enesky.core.domain.mapper.MovieMapper
import dev.enesky.core.domain.usecase.GetCategorizedMoviesUseCase
import dev.enesky.core.domain.usecase.GetMovieDetailsUseCase
import dev.enesky.core.network.repository.MovieRepository

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetCategorizedMoviesUseCase(
        movieRepository: MovieRepository,
        movieMapper: MovieMapper
    ): GetCategorizedMoviesUseCase {
        return GetCategorizedMoviesUseCase(movieRepository, movieMapper)
    }

    @Provides
    fun provideGetMovieDetailsUseCase(
        movieRepository: MovieRepository,
        movieDetailMapper: MovieDetailMapper
        ): GetMovieDetailsUseCase {
        return GetMovieDetailsUseCase(movieRepository, movieDetailMapper)
    }
}

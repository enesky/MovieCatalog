package dev.enesky.core.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.enesky.core.domain.usecase.GetMovieDetailsUseCase
import dev.enesky.core.network.repository.MovieRepository

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetMovieDetailsUseCase(movieRepository: MovieRepository): GetMovieDetailsUseCase {
        return GetMovieDetailsUseCase(movieRepository)
    }
}

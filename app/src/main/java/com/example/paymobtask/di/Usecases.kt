package com.example.paymobtask.di

import com.example.paymobtask.domain.usecases.LocalMovieUseCase
import com.example.paymobtask.domain.usecases.LocalMoviesUseCase
import com.example.paymobtask.domain.usecases.MoviesUseCase
import com.example.paymobtask.domain.usecases.UpdateMovieFavoriteUseCase
import org.koin.dsl.module

val usecasesModule = module {
    single { MoviesUseCase(get()) }
    single { LocalMoviesUseCase(get()) }
    single { LocalMovieUseCase(get()) }
    single { UpdateMovieFavoriteUseCase(get()) }
}
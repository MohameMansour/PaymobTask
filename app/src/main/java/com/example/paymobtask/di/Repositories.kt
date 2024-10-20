package com.example.paymobtask.di

import com.example.paymobtask.data.repo.MoviesRepository
import com.example.paymobtask.domain.repo.IMoviesRepository
import org.koin.dsl.module

val repoModule = module {
    single<IMoviesRepository> { MoviesRepository(get(),get()) }
}
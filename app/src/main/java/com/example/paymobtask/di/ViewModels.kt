package com.example.paymobtask.di

import com.example.paymobtask.ui.movielist.MoviesViewModel
import com.example.paymobtask.ui.moviedetails.MovieDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmsModule = module {
    viewModel { MoviesViewModel(get(),get(),get()) }
    viewModel { MovieDetailsViewModel(get(),get()) }
}
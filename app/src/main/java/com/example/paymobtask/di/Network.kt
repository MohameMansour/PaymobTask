package com.example.paymobtask.di

import com.example.paymobtask.data.remotedatasource.RetrofitBuilder
import org.koin.dsl.module

val networkModule = module {

    single { RetrofitBuilder.getInstance() }
    single { RetrofitBuilder.provideMovieListApis() }

}
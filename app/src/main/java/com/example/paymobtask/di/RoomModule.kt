package com.example.paymobtask.di

import androidx.room.Room
import com.example.paymobtask.data.localDatabase.MoviesDao
import com.example.paymobtask.data.localDatabase.LocalDataBase
import org.koin.dsl.module

val RoomModule = module {

    single <LocalDataBase>{
        Room.databaseBuilder(
            get(),
            LocalDataBase::class.java, "movie_database"
        ).build()
    }

    single <MoviesDao> { get<LocalDataBase>().moviesListDao() }


}
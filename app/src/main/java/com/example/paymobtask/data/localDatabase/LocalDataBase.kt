package com.example.paymobtask.data.localDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paymobtask.data.model.MovieLocal

@Database(entities = [MovieLocal::class], version = 1, exportSchema = false)
abstract class LocalDataBase : RoomDatabase() {

    abstract fun moviesListDao(): MoviesDao
}

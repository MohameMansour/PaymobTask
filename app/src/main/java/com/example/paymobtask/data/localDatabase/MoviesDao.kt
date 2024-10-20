package com.example.paymobtask.data.localDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paymobtask.data.model.MovieLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieLocal>)

    @Query("select * From movies_table")
    fun getAllMovies(): Flow<List<MovieLocal>>

    @Query("UPDATE movies_table SET isFavorite = :isFavorite WHERE movieId = :movieId")
    fun updateMovie(movieId: Int, isFavorite: Boolean): Int

    @Query("select isFavorite From movies_table WHERE movieId = :movieId")
    fun isFavorite(movieId: Int): Boolean

    @Query("select * From movies_table WHERE movieId = :movieId")
    fun getLocalMovie(movieId: Int): Flow<MovieLocal>

}
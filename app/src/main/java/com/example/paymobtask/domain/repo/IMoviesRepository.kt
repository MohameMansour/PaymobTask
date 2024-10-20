package com.example.paymobtask.domain.repo

import com.example.paymobtask.ui.movielist.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {
    suspend fun fetchAndCacheMovies()
    suspend fun getLocalMovies(): Flow<List<Movie>>
    suspend fun getLocalMovie(movieId: Int): Flow<Movie>
    suspend fun updateMovie(movieId: Int,isFavorite :Boolean): Boolean
}
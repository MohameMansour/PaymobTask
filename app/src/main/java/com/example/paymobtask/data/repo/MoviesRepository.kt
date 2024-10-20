package com.example.paymobtask.data.repo

import com.example.paymobtask.data.apis.MovieAPIs
import com.example.paymobtask.data.localDatabase.MoviesDao
import com.example.paymobtask.domain.mapper.mapToLocal
import com.example.paymobtask.domain.mapper.mapToUi
import com.example.paymobtask.domain.repo.IMoviesRepository
import com.example.paymobtask.ui.movielist.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class MoviesRepository(
    private val api: MovieAPIs,
    private val moviesDao: MoviesDao
) : IMoviesRepository {

    override suspend fun fetchAndCacheMovies() {
        val moviesResponse = api.moviesList("en-US", 1).results ?: emptyList()
        val moviesLocal = moviesResponse
            .map {
                val isFavorite = it.id?.let { id -> moviesDao.isFavorite(movieId = id) } ?: false
                val movie = it.mapToLocal(isFavorite)
                movie.copy(isFavorite = isFavorite)
            }
        moviesDao.insertMovies(moviesLocal)
    }

    override suspend fun getLocalMovies(): Flow<List<Movie>> {
        return moviesDao
            .getAllMovies()
            .map { movies ->
                movies.map { it.mapToUi() }
            }.flowOn(Dispatchers.IO)
    }

    override suspend fun getLocalMovie(movieId: Int): Flow<Movie> {
        return moviesDao
            .getLocalMovie(movieId)
            .map { movie ->
                movie.mapToUi()
            }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateMovie(movieId: Int, isFavorite: Boolean): Boolean {
        moviesDao.updateMovie(movieId, isFavorite)
        return isFavorite
    }

}
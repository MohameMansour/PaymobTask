package com.example.paymobtask.ui.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paymobtask.domain.usecases.LocalMoviesUseCase
import com.example.paymobtask.domain.usecases.MoviesUseCase
import com.example.paymobtask.domain.usecases.UpdateMovieFavoriteUseCase
import com.example.paymobtask.ui.movielist.model.Movie
import com.example.paymobtask.utils.usecase.out.State
import com.example.paymobtask.utils.usecase.out.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val moviesUseCase: MoviesUseCase,
    private val updateMovieFavoriteUseCase: UpdateMovieFavoriteUseCase,
    private val localMoviesUseCase: LocalMoviesUseCase,
) : ViewModel() {

    private val _movieList = MutableStateFlow<State<List<Movie>>>(State.Loading)
    val movieList: MutableStateFlow<State<List<Movie>>> = _movieList

    init {
        fetchAndCacheMovies()
        observeLocalMovies()
    }

    private fun fetchAndCacheMovies() {
        viewModelScope.launch {
            moviesUseCase.invoke(Unit)
                .collect {
                }
        }
    }

    private fun observeLocalMovies() {
        viewModelScope.launch {
            localMoviesUseCase.invoke(Unit)
                .collect {
                    _movieList.emit(it)
                }
        }
    }

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            updateMovieFavoriteUseCase.invoke(Pair(movie.id, movie.isFavorite.not()))
                .collect {
                    it.onSuccess {

                    }
                }
        }
    }

}
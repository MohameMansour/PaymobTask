package com.example.paymobtask.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paymobtask.domain.usecases.LocalMovieUseCase
import com.example.paymobtask.domain.usecases.UpdateMovieFavoriteUseCase
import com.example.paymobtask.ui.movielist.model.Movie
import com.example.paymobtask.utils.usecase.out.State
import com.example.paymobtask.utils.usecase.out.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val updateMovieFavoriteUseCase: UpdateMovieFavoriteUseCase,
    private val localMovieUseCase: LocalMovieUseCase,
) : ViewModel() {

    private val _movie = MutableStateFlow<State<Movie>>(State.Loading)
    val movie: MutableStateFlow<State<Movie>> = _movie

    fun observeLocalMovie(movieId: Int) {
        viewModelScope.launch {
            localMovieUseCase.invoke(movieId)
                .collect {
                    _movie.emit(it)
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
package com.example.paymobtask.domain.usecases

import com.example.paymobtask.domain.repo.IMoviesRepository
import com.example.paymobtask.ui.movielist.model.Movie
import com.example.paymobtask.utils.usecase.SingleUseCase
import com.example.paymobtask.utils.usecase.out.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalMovieUseCase (private val repo: IMoviesRepository) :
    SingleUseCase<Int, Movie>() {
    override suspend fun execute(args: Int): Flow<State<Movie>> {
        return repo.getLocalMovie(args).map {
            State.Success(it)
        }
    }
}
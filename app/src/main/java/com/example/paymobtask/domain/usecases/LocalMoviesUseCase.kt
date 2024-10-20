package com.example.paymobtask.domain.usecases

import com.example.paymobtask.domain.repo.IMoviesRepository
import com.example.paymobtask.ui.movielist.model.Movie
import com.example.paymobtask.utils.usecase.SingleUseCase
import com.example.paymobtask.utils.usecase.out.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalMoviesUseCase (private val repo: IMoviesRepository) :
    SingleUseCase<Unit, List<Movie>>() {
    override suspend fun execute(args: Unit): Flow<State<List<Movie>>> {
        return repo.getLocalMovies().map {
            State.Success(it)
        }
    }
}
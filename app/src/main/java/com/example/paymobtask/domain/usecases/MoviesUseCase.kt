package com.example.paymobtask.domain.usecases

import com.example.paymobtask.domain.repo.IMoviesRepository
import com.example.paymobtask.utils.usecase.SingleUseCase
import com.example.paymobtask.utils.usecase.out.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MoviesUseCase(private val repo: IMoviesRepository) :
    SingleUseCase<Unit, Unit?>() {
    override suspend fun execute(args: Unit): Flow<State<Unit?>> = flow {
        repo.fetchAndCacheMovies()
        emit(State.Success(Unit))
    }.flowOn(Dispatchers.IO)
}
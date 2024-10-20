package com.example.paymobtask.domain.usecases

import com.example.paymobtask.domain.repo.IMoviesRepository
import com.example.paymobtask.utils.usecase.SingleUseCase
import com.example.paymobtask.utils.usecase.out.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UpdateMovieFavoriteUseCase(private val repo: IMoviesRepository) :
    SingleUseCase<Pair<Int, Boolean>, Boolean>() {
    override suspend fun execute(args: Pair<Int, Boolean>): Flow<State<Boolean>> {
        return flow { emit(repo.updateMovie(args.first, args.second)) }.map {
            State.Success(it)
        }
    }
}
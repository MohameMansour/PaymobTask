package com.example.paymobtask.utils.usecase

import com.example.paymobtask.utils.usecase.exception.toExceptionType
import com.example.paymobtask.utils.usecase.out.State
import kotlinx.coroutines.flow.*

abstract class SingleUseCase<in IncomingParamterType, out OutComingResultType>() {

    suspend operator fun invoke(args: IncomingParamterType): Flow<State<OutComingResultType>> =
        execute(args)

            .onStart {
                emit(State.Loading)
            }

            .onEmpty { emit(State.Empty) }

            .catch { e ->
                emit(
                    State.Error(
                        e.toExceptionType()
                    )
                )
            }

    protected abstract suspend fun execute(args: IncomingParamterType): Flow<State<OutComingResultType>>

}


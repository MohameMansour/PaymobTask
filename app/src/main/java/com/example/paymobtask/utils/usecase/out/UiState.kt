package com.example.paymobtask.utils.usecase.out

import com.example.paymobtask.data.remotedatasource.model.Response
import com.example.paymobtask.utils.errorview.ExceptionType
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

fun <Response> State<Response>.onEmpty(`do`: () -> Unit) = apply {
    if (this is State.Empty) {
        `do`.invoke()
    }
}

fun <Response> State<Response>.onLoading(`do`: () -> Unit) = apply {
    if (this is State.Loading) {
        `do`.invoke()
    }
}

fun <Response> State<Response>.onFail(`do`: (ExceptionType) -> Unit) = apply {
    if (this is State.Error) {
        `do`.invoke(this.exceptionType)
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <Response> State<Response>.doOnSuccess(execution: (Response) -> Unit) {
    contract {
        callsInPlace(execution, InvocationKind.EXACTLY_ONCE)
    }
    if (this is State.Success) {
        execution.invoke(this.data)
    } else {
        return
    }
}


fun <Response> State<Response>.onSuccess(`do`: (Response) -> Unit) = apply {
    if (this is State.Success) {
        `do`.invoke(this.data)
    }
}

/*
fun <Response> State<Response>.hasData() = if (this is State.Success) {
    this.data != null
} else false
*/

fun <DataType> State<Response<DataType>>.hasData() = if (this is State.Success) {
    this.data.response != null
} else false

fun <DataType> State<Response<DataType>>.getData() =  if (this is State.Success)
    this.data.response
else null

suspend fun <DataType> State<Response<DataType>>.getData(get: suspend (DataType) -> Unit) = apply {
    if (this.hasData()) {
        get.invoke(this.getData()!!)
    }
}


sealed class State<out T> {

    object Loading : State<Nothing>()

    data class Success<out T>(val data: T) : State<T>()

    object Empty : State<Nothing>()

    data class Error(
        val exceptionType: ExceptionType,
    ) : State<Nothing>()

}
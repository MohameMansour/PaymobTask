package com.example.paymobtask.utils.usecase.exception

import com.example.paymobtask.utils.errorview.ExceptionType
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.toExceptionType(): ExceptionType {
    return when (this) {
        is SocketTimeoutException -> return ExceptionType.SocketTimeoutException
        is javax.net.ssl.SSLHandshakeException -> return ExceptionType.SSLHandshakeException
        is UnknownHostException -> return ExceptionType.UnknownHostException
        is java.net.ProtocolException -> return ExceptionType.ProtocolException
        is javax.net.ssl.SSLException -> return ExceptionType.SSLException
        is java.net.SocketException -> return ExceptionType.SocketException
        is java.io.EOFException -> return ExceptionType.EOFException
        is java.util.concurrent.CancellationException -> return ExceptionType.UserCancellationException
        else -> return ExceptionType.GenericException(this.localizedMessage ?: "Generic Exception")
    }
}
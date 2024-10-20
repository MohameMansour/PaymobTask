package com.example.paymobtask.utils.errorview

sealed class ExceptionType () {
    object HttpException404  : ExceptionType()
    object HttpException422 : ExceptionType()
    object HttpException403: ExceptionType()
    object HttpException504 : ExceptionType()
    object HttpException401 : ExceptionType()
    object HttpException400 : ExceptionType()
    object HttpExceptionGeneric : ExceptionType()
    object SocketTimeoutException : ExceptionType()
    object SSLHandshakeException : ExceptionType()
    object UnknownHostException : ExceptionType()
    object ProtocolException : ExceptionType()
    object SSLException : ExceptionType()
    object SocketException : ExceptionType()
    object EOFException : ExceptionType()
    object UserCancellationException : ExceptionType()
    data class GenericException (val message : String) : ExceptionType()
}
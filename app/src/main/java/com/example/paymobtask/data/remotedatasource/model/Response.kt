package com.example.paymobtask.data.remotedatasource.model

import com.google.gson.annotations.SerializedName

open class Response<DataType> {
    var message: List<String>? = null
    var status: Int = 0
    @SerializedName("response", alternate = ["data"])
    var response: DataType? = null
    fun getErrors() = message?.reduceRight { s, acc -> "$s \n$acc" } ?: ""

    fun doOnSuccess(`do`: () -> Unit): Response<DataType> = apply {
        if (status == 200)
            `do`.invoke()
    }

    fun doOnFail(`do`: (String) -> Unit): Response<DataType> = apply {
        if (status != 200)
            `do`.invoke(getErrors())
    }
}

open class ResponseList<DataType> {
    var message: String = ""
    var status: Int = 0

    @SerializedName("data", alternate = [""])
    var data: List<DataType>? = null
}

data class InternalResponseState(
    val code: Int = 0,
    val errorMessages: List<String>? = null,
    val success: Boolean = false
)
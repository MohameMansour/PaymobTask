package com.example.paymobtask.data.apis

import com.example.paymobtask.data.model.MoviesResponse
import com.example.paymobtask.data.remotedatasource.ApiConstant.Companion.moviesList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPIs {

    @GET(moviesList)
    suspend fun moviesList(
        @Query("language") lang: String,
        @Query("page") page: Int
    ): MoviesResponse

}
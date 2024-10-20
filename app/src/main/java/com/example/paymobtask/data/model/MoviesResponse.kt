package com.example.paymobtask.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class MoviesResponse(
    @field:SerializedName("page")
    val page: Int ?= null,
    @field:SerializedName("results")
    val results: List<Result> ?= null,
    @field:SerializedName("total_pages")
    val totalPages: Int ?= null,
    @field:SerializedName("total_results")
    val totalResults: Int ?= null
) {
    data class Result(

        @field:SerializedName("adult")
        val adult: Boolean ?= null,

        @field:SerializedName("backdrop_path")
        val backdropPath: String ?= null,

        val genre_ids: List<Int> ?= null,

        @field:SerializedName("id")
        val id: Int ?= null,

        @field:SerializedName("original_language")
        val originalLanguage: String ?= null,

        @field:SerializedName("original_title")
        val originalTitle: String ?= null,

        @field:SerializedName("overview")
        val overview: String ?= null,

        val popularity: Double ?= null,

        @field:SerializedName("poster_path")
        val posterPath: String ?= null,

        @field:SerializedName("release_date")
        val releaseDate: String ?= null,

        @field:SerializedName("title")
        val title: String ?= null,

        @field:SerializedName("video")
        val video: Boolean ?= null,

        @field:SerializedName("vote_average")
        val voteAverage: Double ?= null,

        @field:SerializedName("vote_count")
        val voteCount: Int ?= null
    )
}
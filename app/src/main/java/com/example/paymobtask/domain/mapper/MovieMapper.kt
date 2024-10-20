package com.example.paymobtask.domain.mapper

import com.example.paymobtask.data.model.MovieLocal
import com.example.paymobtask.data.model.MoviesResponse
import com.example.paymobtask.ui.movielist.model.Movie

fun MoviesResponse.Result.mapToLocal(isFavorite: Boolean): MovieLocal {
    return MovieLocal(
        movieId = id ?: 0,
        title = title.orEmpty(),
        isFavorite = isFavorite ?: false,
        originalLanguage = originalLanguage.orEmpty(),
        voteCount = voteCount ?: 0,
        originalTitle = originalTitle.orEmpty(),
        posterPath = posterPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        voteAverage = voteAverage ?: 0.0,
        overView = overview.orEmpty()
    )
}

fun MovieLocal.mapToUi(): Movie {
    return Movie(
        id = movieId,
        title = title,
        isFavorite = isFavorite,
        originalLanguage = originalLanguage,
        voteCount = voteCount,
        originalTitle = originalTitle,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        overView = overView
    )
}
package com.example.paymobtask.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_table")
data class MovieLocal(
    @PrimaryKey(autoGenerate = true)
    val movieId: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val overView :String,
    val voteCount: Int,
    var isFavorite: Boolean
)
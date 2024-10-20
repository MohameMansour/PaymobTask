package com.example.paymobtask.ui.movielist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paymobtask.R
import com.example.paymobtask.data.remotedatasource.ApiConstant
import com.example.paymobtask.databinding.ItemMovieBinding
import com.example.paymobtask.ui.movielist.model.Movie

class MoviesAdapter(
    private var moviesList: List<Movie> = emptyList(),
    private var favoriteListener: OnFavoriteClickListener? = null,
    private var movieListener: OnMovieClickListener? = null
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    class MovieViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val context = holder.binding.root.context

        holder.binding.movie = moviesList[position]

        val moviePosterUrl = ApiConstant.IMAGE_BASE + moviesList[position].posterPath

        Glide.with(holder.binding.root.context)
            .load(moviePosterUrl)
            .into(holder.binding.movieImageIv)

        holder.binding.favorite.setImageResource(
            if (moviesList[position].isFavorite) R.drawable.ic_favorite else R.drawable.ic_not_favorite
        )

        holder.binding.favorite.setOnClickListener {
            favoriteListener?.onFavoriteClicked(moviesList[position])
        }

        holder.binding.movieItem.setOnClickListener {
            movieListener?.onMovieClicked(moviesList[position])
        }

        val percentage = (moviesList[position].voteAverage * 10).toInt()
        holder.binding.voteAverageProgressBar.progress = percentage
        holder.binding.voteNumberTv.text =
            StringBuilder(percentage.toString()).append("%").toString()

    }

    override fun getItemCount(): Int = moviesList.size

    interface OnFavoriteClickListener {
        fun onFavoriteClicked(movie: Movie)
    }

    interface OnMovieClickListener {
        fun onMovieClicked(movie: Movie)
    }

}
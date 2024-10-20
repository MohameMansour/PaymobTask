package com.example.paymobtask.ui.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.paymobtask.R
import com.example.paymobtask.data.remotedatasource.ApiConstant
import com.example.paymobtask.databinding.FragmentMovieDetailsBinding
import com.example.paymobtask.ui.movielist.model.Movie
import com.example.paymobtask.utils.coroutine.collect
import com.example.paymobtask.utils.extentions.getParcelableCompat
import com.example.paymobtask.utils.extentions.loadImage
import com.example.paymobtask.utils.usecase.out.onFail
import com.example.paymobtask.utils.usecase.out.onLoading
import com.example.paymobtask.utils.usecase.out.onSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : Fragment() {

    private var movieItem: Movie? = null
    private lateinit var binding: FragmentMovieDetailsBinding
    private val vm: MovieDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailsBinding.bind(view)

        movieItem =
            requireArguments().getParcelableCompat("Movie_Item", Movie::class.java)
        initViews()
        observeStatus()
        vm.observeLocalMovie(movieItem?.id ?: 0)
    }

    private fun observeStatus() {
        collect(vm.movie) { state ->
            state
                .onLoading {}
                .onFail {}
                .onSuccess {
                    movieItem = it
                    initViews()
                }
        }
    }

    private fun initViews() {

        binding.favorite.setOnClickListener{
            movieItem?.let { it1 -> vm.toggleFavorite(it1) }
        }

        binding.title.text = movieItem?.title.toString()

        binding.favorite.setImageResource(
            if (movieItem?.isFavorite == true) R.drawable.ic_favorite else R.drawable.ic_not_favorite
        )

        binding.language.text = StringBuilder(getString(R.string.language)).append(" ").append(movieItem?.originalLanguage).toString()

        val percentage = (movieItem?.voteAverage.toString().toDouble() * 10).toInt()
        binding.voteAverageProgressBar.progress = percentage
        binding.voteNumberTv.text = StringBuilder(percentage.toString()).append("%").toString()

        binding.txtCountAverage.text = StringBuilder(getString(R.string.vote)).append(" ").append(movieItem?.voteCount.toString()).toString()

        binding.txtDescription.text = movieItem?.overView.toString()

        binding.imgMovie.loadImage(
            ApiConstant.IMAGE_BASE + movieItem?.posterPath
        )

    }

}
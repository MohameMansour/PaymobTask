package com.example.paymobtask.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paymobtask.R
import com.example.paymobtask.databinding.FragmentMoviesListBinding
import com.example.paymobtask.ui.movielist.adapter.MoviesAdapter
import com.example.paymobtask.ui.movielist.model.Movie
import com.example.paymobtask.utils.coroutine.collect
import com.example.paymobtask.utils.extentions.customNavigate
import com.example.paymobtask.utils.usecase.out.onFail
import com.example.paymobtask.utils.usecase.out.onLoading
import com.example.paymobtask.utils.usecase.out.onSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListFragment : Fragment(), MoviesAdapter.OnMovieClickListener,
    MoviesAdapter.OnFavoriteClickListener {

    private val vm: MoviesViewModel by viewModel()
    private var moviesAdapter = MoviesAdapter()
    private lateinit var binding: FragmentMoviesListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoviesListBinding.bind(view)

        observeStatus()
    }

    private fun observeStatus() {
        collect(vm.movieList) { state ->
            state
                .onLoading {}
                .onFail {}
                .onSuccess {
                    initRecycle(it)
                }
        }
    }

    private fun initRecycle(movies: List<Movie>) {
        moviesAdapter = MoviesAdapter(movies, this, this)
        binding.moviesRv.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL,
            false
        )
        binding.moviesRv.adapter = moviesAdapter
    }

    override fun onMovieClicked(movie: Movie) {
        val bundle = Bundle()
        bundle.putParcelable("Movie_Item", movie)
        findNavController().customNavigate(R.id.movieDetailsFragment, data = bundle)
    }

    override fun onFavoriteClicked(movie: Movie) {
        vm.toggleFavorite(movie)
    }


}
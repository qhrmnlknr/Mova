package com.example.movamovieapp.ui.home.tabs

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentTrailersBinding
import com.example.movamovieapp.datamodels.movietrailers.TrailerResult
import com.example.movamovieapp.ui.details.MovieDetailsFragmentDirections
import com.example.movamovieapp.ui.details.MovieDetailsViewModel
import com.example.movamovieapp.ui.home.MovieTrailerUiState
import com.example.movamovieapp.ui.home.adapters.TrailerAdapter
import com.example.movamovieapp.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrailersFragment : BaseFragment<FragmentTrailersBinding>(FragmentTrailersBinding::inflate) {

    private val viewmodel by viewModels<MovieDetailsViewModel>()
    private val adapter = TrailerAdapter()




    private val movieId: String? by lazy {
        arguments?.getString(ARG_MOVIE_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        binding.recyclerview.adapter = adapter

        adapter.onClick = {
            findNavController().navigate(MovieDetailsFragmentDirections.actionMovieDetailsFragmentToYoutubeVideoPlayerFragment(it))
        }

        movieId?.let { viewmodel.getMovieTrailers(it) }
    }

    private fun observeData() {
        viewmodel.movieTrailerUIState.observe(viewLifecycleOwner) {
            when (it) {
                is MovieTrailerUiState.SuccessNowPlaying -> {
                    adapter.updateList(it.data.results as java.util.ArrayList<TrailerResult>)
                }

                is MovieTrailerUiState.Error -> {
                    Toast.makeText(requireContext(), "Unable to load data :/", Toast.LENGTH_SHORT).show()
                }

                is MovieTrailerUiState.Loading -> {
                    // You can show a loading spinner if needed
                }
            }
        }
    }

    companion object {
        private const val ARG_MOVIE_ID = "movie_id"

        fun newInstance(movieId: String): TrailersFragment {
            val fragment = TrailersFragment()
            val args = Bundle()
            args.putString(ARG_MOVIE_ID, movieId)
            fragment.arguments = args
            return fragment
        }
    }

}

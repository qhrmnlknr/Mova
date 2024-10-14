package com.example.movamovieapp.ui.home.tabs

import android.os.Bundle
import android.util.Log
import android.util.Log.e
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movamovieapp.databinding.FragmentMovieLikeThisBinding
import com.example.movamovieapp.datamodels.movietrailers.TrailerResult
import com.example.movamovieapp.datamodels.newmovies.Result
import com.example.movamovieapp.ui.details.MovieDetailsFragmentDirections

import com.example.movamovieapp.ui.details.MovieDetailsViewModel
import com.example.movamovieapp.ui.home.HomeUiState
import com.example.movamovieapp.ui.home.MovieTrailerUiState
import com.example.movamovieapp.ui.home.adapters.SimilarMoviesAdapter
import com.example.movamovieapp.ui.home.adapters.TrailerAdapter
import com.example.movamovieapp.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieLikeThisFragment(private val id : String) : BaseFragment<FragmentMovieLikeThisBinding>(FragmentMovieLikeThisBinding::inflate) {
    private val viewmodel by viewModels<MovieDetailsViewModel>()
    private val adapter = SimilarMoviesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        binding.rv.adapter = adapter
        adapter.onClick = {
            findNavController().navigate(MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf2(it))
        }
        viewmodel.getSimilarMovies(id)
    }


    private fun observeData(){
        viewmodel.movieSimilarUIState.observe(viewLifecycleOwner){
            when (it) {
                is HomeUiState.SuccessNowPlaying -> {
                    adapter.updateList(it.data.results as java.util.ArrayList<Result> )
                }

                is HomeUiState.Error -> {
                    Toast.makeText(requireContext(), "Unable to load data :/", Toast.LENGTH_SHORT).show()
                }

                is HomeUiState.Loading -> {
                }

            }
        }
    }
}
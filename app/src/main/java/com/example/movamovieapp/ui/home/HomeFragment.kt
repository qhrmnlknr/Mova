package com.example.movamovieapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentHomeBinding
import com.example.movamovieapp.datamodels.newmovies.Result
import com.example.movamovieapp.ui.home.adapters.DiscoverAdapter
import com.example.movamovieapp.ui.home.adapters.NewMoviesAdapter
import com.example.movamovieapp.ui.onboarding.SignInViewModel
import com.example.movamovieapp.utils.BaseFragment
import com.example.movamovieapp.utils.Constants.IMAGE_BASE_URL
import com.example.movamovieapp.utils.enums.CardTypeEnum
import com.example.movamovieapp.utils.loadURL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel by viewModels<HomeViewModel>()
    private val movieadapter = NewMoviesAdapter()
    private val tvadapter = DiscoverAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.Top10rv.adapter = movieadapter
        binding.NewTVrv.adapter = tvadapter
        observData()

        binding.textViewMovies.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTop10AndNewReleaseFragment(CardTypeEnum.MOVIE_LIST))
        }
        binding.textViewTvSeries.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTop10AndNewReleaseFragment(CardTypeEnum.TV_SERIES_LIST))
        }

        movieadapter.onClick = {findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(it))}
        tvadapter.onClick = {findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(it))}


    }


    private fun observData(){
        homeViewModel.movieUiStateData.observe(viewLifecycleOwner){
            viewLifecycleOwner.lifecycleScope.launch {
                movieadapter.submitData(it)
            }
        }

        homeViewModel.tvUIState.observe(viewLifecycleOwner){
            when (it) {
                is HomeUiState.SuccessNowPlaying -> {
                    it.data?.results?.let {
                        tvadapter.updateList(it as java.util.ArrayList<Result>)
                    }
                }

                is HomeUiState.Error -> {
                    Toast.makeText(requireContext(), "Unable to load data :/", Toast.LENGTH_SHORT).show()
                }

                is HomeUiState.Loading -> {
                }
            }
        }

        homeViewModel.movieDetailUIState.observe(viewLifecycleOwner){
            when (it) {
                is MovieDetailUiState.SuccessNowPlaying -> {


                    it.data?.let {
                        binding.viewPager2.loadURL(it.backdropPath!!)
                    }

                }

                is MovieDetailUiState.Error -> {
                    Toast.makeText(requireContext(), "Unable to load data :/", Toast.LENGTH_SHORT).show()
                }

                is MovieDetailUiState.Loading -> {

                }

            }
        }









    }


}
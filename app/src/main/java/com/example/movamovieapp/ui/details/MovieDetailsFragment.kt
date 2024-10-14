package com.example.movamovieapp.ui.details

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.util.Log.*
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentMovieDetailsBinding
import com.example.movamovieapp.datamodels.MovieDetails
import com.example.movamovieapp.datamodels.cast.Cast
import com.example.movamovieapp.datamodels.local.FavoriteMovies
import com.example.movamovieapp.ui.home.MovieCastUiState
import com.example.movamovieapp.ui.home.MovieDetailUiState
import com.example.movamovieapp.ui.home.adapters.ActorsAdapter
import com.example.movamovieapp.ui.home.adapters.ViewPagerAdapter
import com.example.movamovieapp.utils.BaseFragment
import com.example.movamovieapp.utils.loadURL
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>(FragmentMovieDetailsBinding::inflate) {

    private val viewmodel by viewModels<MovieDetailsViewModel>()
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val adapter = ActorsAdapter()
    private var moviedetail : MovieDetails? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieID = args.movieID
        observeData()

        binding.recyclerView.adapter = adapter

        setTablayout()

        binding.button6.setOnClickListener {
            findNavController().navigate(MovieDetailsFragmentDirections.actionMovieDetailsFragmentToYoutubeVideoPlayerFragment("dQw4w9WgXcQ"))
        }

        binding.imageView17.setOnClickListener {
           addToFavs()
            binding.imageView17.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red), PorterDuff.Mode.SRC_IN)
        }

        binding.imageView8.setOnClickListener {
            findNavController().navigateUp()
        }

        viewmodel.getMovieDetail(movieID)
        viewmodel.getMovieCast(movieID)





    }

    fun setTablayout(){


        with(binding) {
            val movieID = args.movieID
            viewpager.adapter = ViewPagerAdapter(childFragmentManager, lifecycle, movieID)

            val tabsArray = arrayOf(
                "Trailers",
                "More Like This",
                "Comments",
            )

            TabLayoutMediator(tabLayout2, viewpager) { tab, position ->
                tab.text = tabsArray[position]
            }.attach()

        }
    }

    fun addToFavs(){
        val movieFavDetail = FavoriteMovies(
            title = moviedetail!!.title,
            image = moviedetail!!.backdropPath,
            vote = moviedetail!!.voteAverage
        )

        viewmodel.addToFavorites(movieFavDetail)
        Toast.makeText(requireContext(), getString(R.string.done), Toast.LENGTH_SHORT).show()
    }



    private fun observeData(){
        viewmodel.movieDetailUIState.observe(viewLifecycleOwner){
            when (it) {
                is MovieDetailUiState.SuccessNowPlaying -> {


                    it.data?.let {
                        binding.movieImage.loadURL(it.backdropPath!!)
                        binding.thedata = it
                        moviedetail = it
                        binding.textViewGenres.text = getString(R.string.genre) + " " + viewmodel.getGenres(it.genres!!)

                    }

                }

                is MovieDetailUiState.Error -> {
                    Toast.makeText(requireContext(), "Unable to load data :/", Toast.LENGTH_SHORT).show()

                }

                is MovieDetailUiState.Loading -> {

                }

            }
        }



        viewmodel.movieCastUIState.observe(viewLifecycleOwner){
            when (it) {
                is MovieCastUiState.SuccessNowPlaying -> {


                    it.data?.let {
                        val actors = viewmodel.getActors(it)
                        adapter.updateList(actors as java.util.ArrayList<Cast>)
                    }

                }

                is MovieCastUiState.Error -> {
                    Toast.makeText(requireContext(), "Unable to load data :/", Toast.LENGTH_SHORT).show()
                }

                is MovieCastUiState.Loading -> {
                }

            }
        }
        }

    }






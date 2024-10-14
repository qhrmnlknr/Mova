package com.example.movamovieapp.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentTop10AndNewReleaseBinding
import com.example.movamovieapp.datamodels.newmovies.Result
import com.example.movamovieapp.ui.home.adapters.NewMoviesAdapter
import com.example.movamovieapp.ui.home.adapters.Top10andNewReleaseAdapter
import com.example.movamovieapp.utils.BaseFragment
import com.example.movamovieapp.utils.Constants.IMAGE_BASE_URL
import com.example.movamovieapp.utils.enums.CardTypeEnum
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Top10AndNewReleaseFragment : BaseFragment<FragmentTop10AndNewReleaseBinding>(FragmentTop10AndNewReleaseBinding::inflate) {

    private val viewModel by viewModels<Top10AndNewReleaseViewModel>()
    private val args: Top10AndNewReleaseFragmentArgs by navArgs()
    private val adapter = Top10andNewReleaseAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerview.adapter = adapter

        val type = args.ListType
        viewModel.selectRequestType(type)

        when(type){
            CardTypeEnum.MOVIE_LIST -> binding.textView9.text = getString(R.string.top_10_movies_this_week)
            CardTypeEnum.TV_SERIES_LIST -> binding.textView9.text = getString(R.string.new_releases)

        }

        binding.imageView8.setOnClickListener {
            findNavController().navigateUp()
        }

        adapter.onClick = {
            findNavController().navigate(Top10AndNewReleaseFragmentDirections.actionTop10AndNewReleaseFragmentToMovieDetailsFragment(it))
        }

        observData()


    }

    private fun observData(){
        viewModel.listUiStateData.observe(viewLifecycleOwner){
            when (it) {
                is HomeUiState.SuccessNowPlaying -> {

                    it.data?.results?.let {
                        adapter.updateList(it as java.util.ArrayList<Result>)
                    }

                }

                is HomeUiState.Error -> {
                }

                is HomeUiState.Loading -> {

                }

            }
        }


    }
}
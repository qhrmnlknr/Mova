package com.example.movamovieapp.ui.explore

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentExploreBinding
import com.example.movamovieapp.datamodels.Genre
import com.example.movamovieapp.datamodels.searchmovies.Result
import com.example.movamovieapp.ui.home.GenresUiState
import com.example.movamovieapp.ui.home.HomeUiState
import com.example.movamovieapp.ui.home.SearchMovieUiState
import com.example.movamovieapp.ui.home.adapters.ChosenGenresAdapter
import com.example.movamovieapp.ui.home.adapters.ExploreAdapter
import com.example.movamovieapp.ui.home.adapters.GenreAdapter
import com.example.movamovieapp.ui.home.adapters.SearchAdapter
import com.example.movamovieapp.ui.home.adapters.SearchedMoviesAdapter
import com.example.movamovieapp.ui.home.adapters.Top10andNewReleaseAdapter
import com.example.movamovieapp.utils.BaseFragment
import com.example.movamovieapp.utils.makeGone
import com.example.movamovieapp.utils.makeVisible
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.cache.DiskLruCache

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>(FragmentExploreBinding::inflate) {

    private val viewmodel by viewModels<ExploreViewModel>()
    private val adapter = GenreAdapter()
    private var selectedGenres = arrayListOf<Genre>()
    private val selectedGenreAdapter = ChosenGenresAdapter()
    private var genres = listOf<Genre>()
    private val searchviewAdapter = SearchAdapter()
    private val listedMoviesAdapter = SearchedMoviesAdapter()
    private val newmoviesadapter = ExploreAdapter()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setAdapters()

        listedMoviesAdapter.onClick = {
            findNavController().navigate(ExploreFragmentDirections.actionExploreFragmentToMovieDetailsFragment(it))
        }

        binding.imageViewspecs.setOnClickListener {
            bottomsheetdialog()
        }

        adapter.isChecked = { check, data ->
            if (check && selectedGenres.none { it.name == data.name }) {
                selectedGenres.add(data)
            }
        }

        binding.searchtextinput.addTextChangedListener( object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.rvsearchview.makeVisible()
                binding.rvselectedgenre.makeGone()
                binding.rvmovielist.makeGone()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.rvsearchview.makeVisible()
                GlobalScope.launch {
                    delay(600)
                    val text = binding.searchtextinput.text.toString().trim()
                    viewmodel.searchMovie(text)
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.searchtextinput.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                binding.rvsearchview.makeGone()
                binding.rvselectedgenre.makeVisible()
                binding.rvmovielist.makeVisible()
                true
            } else {
                false
            }
        }



    }



    fun setAdapters(){
        binding.rvselectedgenre.adapter = selectedGenreAdapter
        binding.rvsearchview.adapter = searchviewAdapter
        binding.rvmovielist.adapter = listedMoviesAdapter
    }


    private fun updateActiveGenres(): List<Genre>{
        return genres.map { genre: Genre ->
            genre.copy(isActive = selectedGenres.any { it.name == genre.name })
        }
    }

    private fun addSelectedGenresToRv(){
        selectedGenreAdapter.updateList(selectedGenres)
    }

    private fun bottomsheetdialog(){
        val dialog = BottomSheetDialog(requireContext())
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.explore_bottomsheet_dialog, null)
        dialog.setContentView(view)

        view.findViewById<Button>(R.id.buttonapply).setOnClickListener {
            dialog.dismiss()
            addSelectedGenresToRv()
        }

        view.findViewById<Button>(R.id.buttonreset).setOnClickListener {
            selectedGenres.clear()
            selectedGenreAdapter.updateList(selectedGenres)
            genres.map { genre ->
                genre.copy(isActive = false)
            }
            dialog.dismiss()
        }


        val recyclerview = view.findViewById<RecyclerView>(R.id.rvgenre)
        recyclerview.adapter = adapter

        adapter.updateList(updateActiveGenres() as java.util.ArrayList<Genre>)

        dialog.show()
    }

    private fun observeData(){

        viewmodel.movieUiStateData.observe(viewLifecycleOwner){
            when (it) {
                is HomeUiState.SuccessNowPlaying -> {


                    it.data?.results?.let {
                        newmoviesadapter.updateList(it as java.util.ArrayList<com.example.movamovieapp.datamodels.newmovies.Result>)
                    }

                }

                is HomeUiState.Error -> {
                    Toast.makeText(requireContext(), "Unable to load data :/", Toast.LENGTH_SHORT).show()
                }

                is HomeUiState.Loading -> {

                }

            }
        }


        viewmodel.genreUIState.observe(viewLifecycleOwner){
            when (it) {
                is GenresUiState.SuccessNowPlaying -> {


                    it.data?.let {
                        genres = it.genres!!
                        adapter.updateList(genres as java.util.ArrayList<Genre>)
                    }

                }

                is GenresUiState.Error -> {
                    Toast.makeText(requireContext(), "Unable to load data :/", Toast.LENGTH_SHORT).show()
                }

                is GenresUiState.Loading -> {

                }

            }
        }


        viewmodel.senreUIState.observe(viewLifecycleOwner){
            when (it) {
                is SearchMovieUiState.SuccessNowPlaying -> {


                    it.data?.results?.let {
                        searchviewAdapter.updateList(it as java.util.ArrayList<Result>)
                        showNotFound(it)
                    }

                }

                is SearchMovieUiState.Error -> {
                    Toast.makeText(requireContext(), "Unable to load data :/", Toast.LENGTH_SHORT).show()
                }

                is SearchMovieUiState.Loading -> {

                }

            }
        }
    }

    fun showNotFound(list : List<Result>){
        when(list.size){
            0 -> {
                binding.imageViewNotFound.makeVisible()
                binding.textView22.makeVisible()
                binding.textView21.makeVisible()
                binding.rvmovielist.makeGone()


            }
            else ->{
                binding.imageViewNotFound.makeGone()
                binding.textView22.makeGone()
                binding.textView21.makeGone()
                binding.rvmovielist.makeVisible()

            }
        }
        listedMoviesAdapter.updateList(list as java.util.ArrayList<Result>)
    }

}
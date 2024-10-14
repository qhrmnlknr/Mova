package com.example.movamovieapp.ui.mylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movamovieapp.databinding.FragmentMyListBinding
import com.example.movamovieapp.ui.home.adapters.FavoritesAdapter
import com.example.movamovieapp.utils.BaseFragment
import com.example.movamovieapp.utils.makeGone
import com.example.movamovieapp.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyListFragment : BaseFragment<FragmentMyListBinding>(FragmentMyListBinding::inflate) {

    private val viewmodel by viewModels<MyListViewModel>()
    private val adapter = FavoritesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        binding.rv.adapter = adapter

        adapter.onClick = {
            findNavController().navigate(MyListFragmentDirections.actionMyListFragmentToMovieDetailsFragment(it))
        }

        adapter.onClickRemove = {
            viewmodel.removeMovieItem(it)

        }





    }

    override fun onResume() {
        super.onResume()
        viewmodel.getFavorites()
    }

    fun observeData(){
        viewmodel.favorites.observe(viewLifecycleOwner){
            adapter.updateList(it)

            if (it.isNullOrEmpty()){
                binding.imageViewnotfound.makeVisible()
                binding.textViewnofavorite.makeVisible()
                binding.textViewnofavorite.makeVisible()
            } else{
                binding.imageViewnotfound.makeGone()
                binding.textViewnofavorite.makeGone()
                binding.textViewnofavorite.makeGone()
            }
        }
    }




}
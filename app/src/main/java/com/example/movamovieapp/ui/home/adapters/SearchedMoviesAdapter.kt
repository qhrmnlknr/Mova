package com.example.movamovieapp.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movamovieapp.databinding.MovieItemBigBinding
import com.example.movamovieapp.utils.Constants.IMAGE_BASE_URL
import com.example.movamovieapp.utils.loadURL


class SearchedMoviesAdapter  : RecyclerView.Adapter<SearchedMoviesAdapter.SearchedMoviesViewModel>() {

    var onClick : (id : String) -> Unit = {}

    private val list = arrayListOf<com.example.movamovieapp.datamodels.searchmovies.Result>()

    inner class SearchedMoviesViewModel(val itemBigBinding: MovieItemBigBinding) : RecyclerView.ViewHolder(itemBigBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedMoviesViewModel {
        val view = MovieItemBigBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchedMoviesViewModel(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SearchedMoviesViewModel, position: Int) {
        val data = list[position]

        Glide.with( holder.itemBigBinding.imageView9.context)
            .load(IMAGE_BASE_URL + data.posterPath)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into( holder.itemBigBinding.imageView9)
        holder.itemBigBinding.textView18.text = data.voteAverage.toString().take(3)

        holder.itemBigBinding.materialcard.setOnClickListener {
            onClick(data.id.toString())
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(new : ArrayList < com.example.movamovieapp.datamodels.searchmovies.Result >) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

}
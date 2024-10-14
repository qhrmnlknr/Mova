package com.example.movamovieapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.databinding.MovieItemBinding
import com.example.movamovieapp.datamodels.newmovies.Result

class NewMoviesAdapter : PagingDataAdapter<Result, NewMoviesAdapter.NewMoviesViewHolder>(DIFF_CALLBACK) {

    var onClick: (id: String) -> Unit = {}

    inner class NewMoviesViewHolder(val movieItemBinding: MovieItemBinding) : RecyclerView.ViewHolder(movieItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewMoviesViewHolder {
        val view = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewMoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewMoviesViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.movieItemBinding.thedata = data
            holder.movieItemBinding.materialcard.setOnClickListener {
                onClick(data.id.toString())
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {

                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {

                return oldItem == newItem
            }
        }
    }
}

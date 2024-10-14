package com.example.movamovieapp.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.movamovieapp.databinding.MovieItemBigBinding
import com.example.movamovieapp.databinding.MovieItemBinding
import com.example.movamovieapp.datamodels.newmovies.Result

class Top10andNewReleaseAdapter : RecyclerView.Adapter<Top10andNewReleaseAdapter.Top10andNewReleaseViewHolder>(){

    private val list = arrayListOf<Result>()
    var onClick : (id : String) -> Unit = {}

    inner class Top10andNewReleaseViewHolder(val movieItemBigBinding: MovieItemBigBinding) : ViewHolder(movieItemBigBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Top10andNewReleaseViewHolder {
        val view = MovieItemBigBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Top10andNewReleaseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Top10andNewReleaseViewHolder, position: Int) {
        val data = list[position]
        holder.movieItemBigBinding.thedata = data
        holder.movieItemBigBinding.materialcard.setOnClickListener {
            onClick(data.id.toString())
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(new : ArrayList < Result >) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }
}
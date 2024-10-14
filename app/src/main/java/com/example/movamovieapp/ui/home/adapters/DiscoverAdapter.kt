package com.example.movamovieapp.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.databinding.MovieItemBinding
import com.example.movamovieapp.datamodels.newmovies.Result

class DiscoverAdapter  : RecyclerView.Adapter<DiscoverAdapter.DiscoverViewHolder>(){

    var onClick : (id : String) -> Unit = {}
    private val list = arrayListOf<Result>()

    inner class DiscoverViewHolder(val itemBinding: MovieItemBinding) : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverViewHolder {
        val view = MovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DiscoverViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DiscoverViewHolder, position: Int) {
        val data = list[position]
        holder.itemBinding.thedata = data
        holder.itemBinding.materialcard.setOnClickListener {
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
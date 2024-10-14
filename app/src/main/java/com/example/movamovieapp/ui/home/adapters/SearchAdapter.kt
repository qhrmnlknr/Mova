package com.example.movamovieapp.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.databinding.SearchviewBinding
import com.example.movamovieapp.datamodels.searchmovies.Result

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchAdapterViewHolder>(){

    private val list = arrayListOf<Result>()

    inner class SearchAdapterViewHolder(val searchviewBinding: SearchviewBinding): RecyclerView.ViewHolder(searchviewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapterViewHolder {
        val view = SearchviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SearchAdapterViewHolder, position: Int) {
        val data = list[position]
        holder.searchviewBinding.thedata = data
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(new : ArrayList <Result>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }
}
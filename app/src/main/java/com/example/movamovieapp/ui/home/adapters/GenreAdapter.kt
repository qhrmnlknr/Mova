package com.example.movamovieapp.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.databinding.GenrelistBinding
import com.example.movamovieapp.datamodels.Genre


class GenreAdapter : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    private val list = arrayListOf<Genre>()
    var isChecked : (Boolean, Genre) -> Unit = { _a , __a ->}

    inner class GenreViewHolder(val genrelistBinding: GenrelistBinding) : RecyclerView.ViewHolder(genrelistBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
      val view = GenrelistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
      return GenreViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val data = list[position]
        holder.genrelistBinding.thedata = data
        holder.genrelistBinding.button5.setOnCheckedChangeListener { _, isOn->
            isChecked(isOn, data)
        }
        if(data.isActive){
            holder.genrelistBinding.button5.isChecked = true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(new : ArrayList <Genre>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }
}
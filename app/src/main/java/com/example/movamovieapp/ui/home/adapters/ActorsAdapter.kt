package com.example.movamovieapp.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.databinding.ActorsBinding
import com.example.movamovieapp.datamodels.cast.Cast


class ActorsAdapter : RecyclerView.Adapter<ActorsAdapter.ActorsViewHolder>(){

    private val list = arrayListOf<Cast>()

    inner class ActorsViewHolder(val actorsBinding : ActorsBinding) : RecyclerView.ViewHolder(actorsBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        val view = ActorsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ActorsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        val data = list[position]
        holder.actorsBinding.thedata = data
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(new : ArrayList < Cast >) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

}
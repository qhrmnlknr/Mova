package com.example.movamovieapp.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.NotificationsDetailsBinding
import com.example.movamovieapp.datamodels.movietrailers.TrailerResult


class TrailerAdapter  : RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>() {

    var onClick : (videoID : String) -> Unit = {}

    private val list = arrayListOf<TrailerResult>()

    inner class TrailerViewHolder(val notificationsDetailsBinding: NotificationsDetailsBinding): RecyclerView.ViewHolder(notificationsDetailsBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val view = NotificationsDetailsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TrailerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        val data = list[position]
        holder.notificationsDetailsBinding.thedata = data
        holder.notificationsDetailsBinding.materialCardView.setOnClickListener {
            onClick(data.key?:"dQw4w9WgXcQ")
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(new : ArrayList <TrailerResult>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

}
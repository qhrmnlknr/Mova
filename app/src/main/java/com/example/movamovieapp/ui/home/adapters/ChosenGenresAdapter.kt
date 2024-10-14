package com.example.movamovieapp.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movamovieapp.databinding.ChosenSpecificationButtonBinding
import com.example.movamovieapp.datamodels.Genre

class ChosenGenresAdapter : RecyclerView.Adapter<ChosenGenresAdapter.ChosenGenresViewHolder>() {

    private val list = arrayListOf<Genre>()
    inner class ChosenGenresViewHolder(val chosenSpecificationButtonBinding: ChosenSpecificationButtonBinding): RecyclerView.ViewHolder(chosenSpecificationButtonBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChosenGenresViewHolder {
        val view = ChosenSpecificationButtonBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ChosenGenresViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: ChosenGenresViewHolder, position: Int) {
        val data = list[position]
        holder.chosenSpecificationButtonBinding.textView19.text = data.name
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(new : ArrayList <Genre>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

}
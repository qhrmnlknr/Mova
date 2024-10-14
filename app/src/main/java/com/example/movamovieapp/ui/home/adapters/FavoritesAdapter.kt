package com.example.movamovieapp.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movamovieapp.databinding.MovieItemBigBinding
import com.example.movamovieapp.datamodels.Genre
import com.example.movamovieapp.datamodels.local.FavoriteMovies
import com.example.movamovieapp.utils.Constants.IMAGE_BASE_URL
import com.example.movamovieapp.utils.loadURL
import com.example.movamovieapp.utils.makeGone

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>(){

    private val list = arrayListOf<FavoriteMovies>()
    var onClick : (id : String) -> Unit = {}
    var onClickRemove : (movie : FavoriteMovies ) -> Unit = {}

    inner class  FavoritesViewHolder(val itemBigBinding: MovieItemBigBinding) : RecyclerView.ViewHolder(itemBigBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view = MovieItemBigBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoritesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val data = list[position]
        holder.itemBigBinding.imageView9.loadURL(data.image!!)
        holder.itemBigBinding.materialcard.setOnClickListener {
            onClick(data.id.toString())
        }

        holder.itemBigBinding.closebutt.setOnClickListener {
            onClickRemove(data)

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(new : List <FavoriteMovies>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }
}
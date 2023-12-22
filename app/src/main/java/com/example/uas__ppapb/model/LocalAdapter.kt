package com.example.uas__ppapb.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uas__ppapb.R

class LocalAdapter(private val movieList: List<DataMovie>) :
    RecyclerView.Adapter<LocalAdapter.LocalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return LocalViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocalViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class LocalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val img: ImageView = itemView.findViewById(R.id.img_movie_item)

        fun bind(movie: DataMovie) {
            title.text = movie.title

            Glide.with(itemView)
                .load(movie.imageUrl)
                .error(R.drawable.ic_launcher_background)
                .into(img)
        }
    }
}
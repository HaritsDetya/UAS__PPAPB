package com.example.uas__ppapb.user

import com.example.uas__ppapb.model.MovieData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uas__ppapb.R


class UserLoginAdapter(private var movieList: List<MovieData>, private val onItemClick: (MovieData) -> Unit) :
    RecyclerView.Adapter<UserLoginAdapter.UserLoginViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserLoginViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return UserLoginViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserLoginViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            onItemClick(movie)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setMovies(movies: List<MovieData>) {
        movieList = movies
        notifyDataSetChanged()
    }

    inner class UserLoginViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvJudul: TextView = itemView.findViewById(R.id.username)
        private val ivImage: ImageView = itemView.findViewById(R.id.img_movie_item)

        fun bind(movie: MovieData) {
            tvJudul.text = movie.title

            Glide.with(itemView)
                .load(movie.imageUrl)
                .error(R.drawable.ic_launcher_background)
                .into(ivImage)
        }
    }
}
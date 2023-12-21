package com.example.uas__ppapb.admin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uas__ppapb.R
import com.example.uas__ppapb.model.DataMovie

class AdminAdapter(private val context: Context, private val dataList: List<DataMovie>) :
    RecyclerView.Adapter<AdminAdapter.AdminViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return AdminViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        holder.bind(dataList[position])
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailMovie::class.java)
            intent.putExtra("Image", dataList[position].imageUrl)
            intent.putExtra("Description", dataList[position].description)
            intent.putExtra("Title", dataList[position].title)
            intent.putExtra("Id", dataList[position].id)
            intent.putExtra("Director", dataList[position].director)
            intent.putExtra("Genre", dataList[position].genre)
            intent.putExtra("Date", dataList[position].date)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class AdminViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var recDate: TextView = itemView.findViewById(R.id.dateMov)
        private val recImage: ImageView = itemView.findViewById(R.id.img_movie_item)
        private val recCard: CardView = itemView.findViewById(R.id.cv_movie_item)
        private val recTitle: TextView = itemView.findViewById(R.id.titleMov)

        fun bind(model: DataMovie) {
            recTitle.text = model.title
            recDate.text = model.date

            Glide.with(itemView).load(model.imageUrl).into(recImage)
        }
    }

}

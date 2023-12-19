package com.example.uas__ppapb.model

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
import com.example.uas__ppapb.admin.DetailMovie
import com.example.uas__ppapb.R

class MyAdapter(private val context: Context, private var dataList: List<DataMovie>) :
    RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].imageURL).into(holder.recImage)
        holder.recTitle.text = dataList[position].title

        holder.recCard.setOnClickListener {
            val intent = Intent(context, DetailMovie::class.java)
            intent.putExtra("Image", dataList[position].imageURL)
            intent.putExtra("Description", dataList[position].description)
            intent.putExtra("Title", dataList[position].title)
            intent.putExtra("Id", dataList[position].id)
            intent.putExtra("Director", dataList[position].director)
            intent.putExtra("Date", dataList[position].date)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setSearchDataList(searchList: ArrayList<DataMovie>) {
        dataList = searchList
        notifyDataSetChanged()
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val recImage: ImageView = itemView.findViewById(R.id.img_movie_item)
    val recCard: CardView = itemView.findViewById(R.id.cv_movie_item)
    val recTitle: TextView = itemView.findViewById(R.id.titleMov)
}
